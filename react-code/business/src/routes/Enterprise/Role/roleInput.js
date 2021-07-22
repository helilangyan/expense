import React, { Component } from 'react';
import { Input,Form,Row, Col,Button  } from 'antd';
import {message} from "antd/lib/index";


class roleInput extends Component {
    componentDidMount() {
        this.setState({
            formData: this.props.formData
        });
        this.formRef.current.setFieldsValue(this.props.formData)
    }
    constructor(props) {
        super(props);
        this.state = {
            formData:{},
            formItemLayout: {
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 5},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 14},
                },
            },
        }
    }
    formRef=React.createRef();
    submitClick=(e)=>{
        this.formRef.current.validateFields().then(() => {
            this.props.submitAdd(this.state.formData);
        }).catch((errorInfo) => {
            message.error("请输入完整信息");
        })

    }
    onCancel=()=>{
        this.props.onCancel();
    }
    handelChange=(event)=>{
        let e = event.target;
        const List= this.state.formData;
        if (e.name=='roleName') {
            List.roleName=e.value
        }
        this.setState({ formData: List});
    }
    render() {
        return (
            <div>
                <Form {...this.state.formItemLayout} ref={this.formRef}>
                    <Row>
                        <Col span={24}>
                            <Form.Item  label='角色' name='roleName' rules={[{ required: true, message: '请输入角色名称!' }]}>
                                <Input placeholder='请输入角色'
                                       name='roleName'
                                       value={this.state.formData.roleName}
                                       onChange={this.handelChange}
                                />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row>
                        <Col span={24} style={{textAlign:'center',marginTop:"20px"}}>
                            <Button type="primary" onClick={this.submitClick}>确定</Button>
                            &nbsp; &nbsp;
                            <Button onClick={this.onCancel}>取消</Button>
                        </Col>
                    </Row>
                </Form>
            </div>
         );
    }
}

export default roleInput;
