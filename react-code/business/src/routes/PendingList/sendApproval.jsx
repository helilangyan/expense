import React, {Component} from 'react';
import {Input, Form, Row, Col, Button, message, Select,Radio } from 'antd';
import debounce from 'lodash/debounce';
import PubSub from "pubsub-js";

const {Option} = Select

class BankCardSet extends Component {

    state = {
        formData: {},
        applyList: []
    }
    formRef = React.createRef()

    componentDidMount() {
        this.getApplyList();
    }

    handleSubmit = (e) => {
        const {formData} = this.state
        this.formRef.current.validateFields().then(() => {

            this.props.submitAdd(formData)
            // this.props.onCancel()
        }).catch((errorInfo) => {
            message.error("请输入完整信息");
        })
    }
    handelChange = (e, v) => {
        this.setState({formData: v});
    }
    getApplyList = () => {
        console.log(this.props.checkData);
        let checkData=this.props.checkData;
        let subData = {
            key: checkData.key.split('_')[1]+ '_' + checkData.key.split('_')[2],
            businessKey:checkData.businessKey,
            taskId:checkData.taskId,
        }
        console.log(subData);
        this.post(`/bpm/use/stepuser/list`, subData).then(res => {
            if (res.code == 1) {
                if (res.data.approve) {
                    this.setState({applyList: res.data.data});
                }else {
                    this.setState({applyList: []});
                }

            } else {
                message.error(res.message);
            }
        })
    }

    render() {
        const formItemLayout = {
            labelCol: {span: 10},
            wrapperCol: {span: 14},
        }
        return (
            <div>
                <Form {...formItemLayout} onValuesChange={this.handelChange} ref={this.formRef}>
                    <Row>
                        <Col span={18}>
                            <Form.Item label="请选择审批结果" name="approve"
                                       rules={[{required: true, message: 'Please input bank name!'}]}>

                                <Radio.Group>
                                    <Radio value='pass'>通过</Radio>
                                    <Radio value='reject'>拒绝</Radio>
                                </Radio.Group>
                            </Form.Item>
                        </Col>
                        {
                            this.state.applyList.length? <Col span={18}>
                                <Form.Item label="审批人" name="nextUserId"
                                           rules={[{required: true, message: 'Please input bank name!'}]}>
                                    <Select
                                        showSearch
                                        placeholder="姓名模糊搜索"
                                    >
                                        {
                                            this.state.applyList.map((item)=>{
                                                return (
                                                    <Option value={item.operatorId} key={item.id}>{item.operatorName}</Option>
                                                );
                                            })
                                        }
                                    </Select>
                                </Form.Item>
                            </Col>:''
                        }

                        <Col span={18}>
                            <Form.Item label="备注" name="comment">
                                <Input.TextArea />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row>
                        <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                            <Button type="primary" onClick={this.handleSubmit}>确定</Button>
                            &nbsp; &nbsp;&nbsp;
                            <Button onClick={this.props.onCancel}>取消</Button>
                        </Col>
                    </Row>
                </Form>
            </div>
        );
    }
}

export default BankCardSet;
