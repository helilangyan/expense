import React, {Component} from 'react';
import {Input, Form, Row, Col, Button, message, Select,Modal} from 'antd';
import debounce from 'lodash/debounce';
import PubSub from "pubsub-js";
import SelectUser from '../../Public/selectUser'

const {Option} = Select

class BankCardSet extends Component {

    state = {
        formData: {},
        applyList: [],
        userModal:false
    }
    formRef = React.createRef()

    componentDidMount() {
        this.getApplyList();
    }

    handleSubmit = (e) => {
        const {formData} = this.state
        this.formRef.current.validateFields().then(() => {
            console.log(formData);
            console.log(this.props.applyData);
            let url='';
            if (this.props.applyType=='1'){
                url='/detail/apply-expense/start'
            }  if (this.props.applyType=='2'){
                url='/detail/apply-travel/start'
            }  if (this.props.applyType=='3'){
                url='/detail/apply-borrow/start'
            }
            this.post(url+`?nextUserId=`+formData.nextUserId+'&&copyUserIds='+formData.copyId, this.props.applyData, 'application/json').then(res => {
                if (res.code == 1) {
                    message.success(res.msg)
                    this.props.onCancel();
                    PubSub.publish('refresh', {tabBtn: this.props.applyType.toString()})
                } else {
                    message.error(res.message);
                }
            })
            this.props.submitAdd(formData)
            this.props.onCancel()
        }).catch((errorInfo) => {
            message.error("请输入完整信息");
        })
    }
    handelChange = (e, v) => {
        this.setState({formData: v});
    }
    getApplyList = () => {
        let subData = {
            key: JSON.parse(localStorage.getItem("entInfo")).enterpriseId + '_' + this.props.applyType
        }
        console.log(subData);
        this.post(`/bpm/use/stepuser/list`, subData).then(res => {
            if (res.code == 1) {
                this.setState({applyList: res.data.data});
            } else {
                message.error(res.message);
            }
        })
    }

    copyClick=()=>{
        this.setState({
            userModal:true
        })
    }

    handleCancel=()=>{
        this.setState({
            userModal:false
        })
    }
    submitInput=(user)=>{
        let userData='',copyId=[];
        console.log(user);
        const {formData} = this.state
        user.forEach(item=>{
            userData+=(item.firstName+item.lastName)+','
            copyId.push(item.userId)
        })
        if (userData) {
            userData = userData.substring(0, userData.lastIndexOf(','));
        }
        formData.copyUser=userData
        formData.copyId=copyId
        this.setState({
            formData:formData
        })
        console.log(this.state.formData);
        this.formRef.current.setFieldsValue(formData)
        this.handleCancel();
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
                            <Form.Item label="抄送" name="copyUser"
                                       rules={[{required: true, message: '请选择抄送人!'}]}>
                                <Input placeholder="请选择抄送人" onClick={this.copyClick} readOnly="readonly" />
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
                <Modal
                    width="30%"
                    title="新增角色"
                    visible={this.state.userModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <SelectUser submitAdd={this.submitInput} onCancel={this.handleCancel}></SelectUser>
                </Modal>
            </div>
        );
    }
}

export default BankCardSet;
