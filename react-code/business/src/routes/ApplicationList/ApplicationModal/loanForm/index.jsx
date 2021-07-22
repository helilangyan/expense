import React, {Component} from 'react';
import {Row, Col, Button, Steps, Form, Input, Space, Select, Checkbox, DatePicker, message} from 'antd';
import {UserOutlined} from '@ant-design/icons';
import {toJS} from "mobx"
import {inject, observer} from 'mobx-react'
import moment from 'moment'
import PubSub from 'pubsub-js'


const {Step} = Steps;
const {Option} = Select;

@inject('appStore') @observer
export default class LoanForm extends Component {
    state = {
        data: toJS(this.props.appStore.curEnt),
        status:'',
        cardlist: [],
        formData: {},
        history:[]
    }
    formRef = React.createRef();

    componentDidMount() {
        this.setDefaultData()
        this.getCards()
        this.getHistory()
    }

    //银行卡
    getCards = () => {
        let obj = {
            limit: 100,
            page: 1,
            enterpriseId: this.state.data.enterpriseId
        }
        this.post("/enterprise/bank/list", obj).then(res => {
            if (res.code === 1) {
                this.setState({
                    cardlist: res.data.data,
                })
            }
        });
    }
    //历史记录
    getHistory = () => {
        if (this.props.defaultData){
            this.get("/bpm/use/history/records/"+this.props.defaultData.id).then(res => {
                if (res.code === 1) {
                    this.setState({
                        history: res.data,
                    })
                }
            });
        }

    }
    checkPromise = (e) => {
        this.setState({promise: e.target.checked})
    }
    setDefaultData = () => {
        if (this.props.defaultData) {
            const {defaultData} = this.props
            defaultData.dueDate = moment(defaultData.dueDate)
            delete defaultData.createTime
            this.setState({formData: defaultData,status:defaultData.status})
            this.formRef.current.setFieldsValue(defaultData)
        }
    }
    onSubmit = (e) => {
        let that = this;
        this.formRef.current.validateFields().then(() => {
            if (e == '1') {
                const {data, formData, promise} = this.state
                let subData = {
                    dueDate: formData.dueDate.format('YYYY-MM-DD'),
                    isSubmit: e,
                    enterpriseId: data.enterpriseId
                }
                subData = {...formData, ...subData}
                if (promise) {
                    that.props.onSubmit(subData, 3);
                } else {
                    message.error("请勾选承诺");
                }
            } else {
                const {data, formData, promise} = this.state
                let subData = {
                    dueDate: formData.dueDate.format('YYYY-MM-DD'),
                    isSubmit: e,
                    enterpriseId: data.enterpriseId
                }
                subData = {...formData, ...subData}
                console.log('subData', subData);
                if (promise) {
                    this.post(`/detail/apply-borrow/insert`, subData, 'application/json').then(res => {
                        if (res.code == 1) {
                            message.success((data.id ? '修改' : '添加') + res.msg)
                            this.onCancel();
                            PubSub.publish('refresh', {tabBtn: '3'})
                        } else {
                            message.error(res.message);
                        }
                    })
                } else {
                    message.error("请勾选承诺");
                }
            }

        }).catch((errorInfo) => {
            console.log(errorInfo);
            message.error("请输入完整信息");
        })
    }
    onChange = (o, v) => {
        let data = {...this.state.formData, ...v}
        this.setState({formData: data})
    }
    onCancel = () => {
        this.props.onCancel();
    }
    // handleMoment = (obj, prop) => {
    //   let format = obj[prop] ? obj[prop].format('YYYY-MM-DD') : ''
    //   return format
    // }

    render() {
        const {cardlist,formData,history} = this.state
        const formItemLayout = {
            labelCol: {
                xs: {span: 24},
                sm: {span: 4},
            },
            wrapperCol: {
                xs: {span: 24},
                sm: {span: 19},
            },
        }
        return (
            <div>
                <Row>
                    <Form style={styles.form} {...formItemLayout} onValuesChange={this.onChange} ref={this.formRef}>
                        <Form.Item label='申请名称' name='applyName' rules={[{required: true, message: '请输入申请名称!'}]}>
                            <Input placeholder='请输入申请名称' readOnly={this.state.status==null||!this.state.status?false:true}/>
                        </Form.Item>
                        <Form.Item label='借款金额' name='money' rules={[{required: true, message: '请输入借款金额!'}]}>
                            <Input placeholder='请输入借款金额' readOnly={this.state.status==null||!this.state.status?false:true}/>
                        </Form.Item>
                        <Form.Item label='预计还款日期' name='dueDate' rules={[{required: true, message: '请选择还款日期!'}]}>
                            <DatePicker disabled={this.state.status==null||!this.state.status?false:true}/>
                        </Form.Item>
                        <Form.Item name="enterpriseBankId" label="公司卡" name='bankId'
                                   rules={[{required: true, message: '请选择公司卡!'}]}>
                            <Select disabled={this.state.status == null || !this.state.status?false:true}>
                                {
                                    !cardlist ? null : cardlist.map((item) => {
                                        return <Option key={item.id}
                                        value={item.id}>{item.bankName} - {item.bankAccount.slice(0, 4)} ******** {item.bankAccount.slice(-4)}</Option>
                                    })
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item label='说明' name='remark'>
                            <Input.TextArea placeholder='请输入说明' readOnly={this.state.status==null||!this.state.status?false:true}/>
                        </Form.Item>
                        {
                            history.length!==0?
                            <Form.Item label='历史记录'>
                                {
                                history.map((item,index)=>{
                                    return <p  className='recordList' key={index}>
                                        <span className="time">{item.arriveTime}</span>
                                        <span className="username">{item.nextUsername}</span>
                                        <span className="approve">{item.approve}</span>
                                        <span>{item.stepName}</span>
                                        <span>意见：{item.comment?item.comment:''}</span>
                                    </p>
                                    })
                                }
                            </Form.Item>:
                        ''}
                    </Form>
                </Row>
                <div className="promise">
                    <Checkbox onChange={this.checkPromise.bind(this)}>我承诺填报数据真实无误，如有错误，愿意承担相应的法律责任！</Checkbox>
                </div>
                <Row>
                    <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                        {
                            formData.isSubmit==1?<Button onClick={this.onCancel}>关闭</Button>
                              :   <Space>
                                <Button type="primary" onClick={() => this.onSubmit(1)}>提交审批</Button>
                                <Button onClick={() => this.onSubmit(0)}>存为草稿</Button>
                            </Space>
                        }
                    </Col>
                </Row>
            </div>
        );
    }
}

const styles = {
    form: {
        width: '100%',
        marginTop: '20px'
    },
}
