import React, { Component } from 'react';
import { Input,Form,Row, Col,Button  } from 'antd';
import {message} from "antd/lib/index";


class projectInput extends Component {
    componentDidMount() {
        this.formRef.current.setFieldsValue(this.props.formData)
    }
    constructor(props) {
        super(props);
        this.state = {
            formData:this.props.formData,
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
        if (e.name=='projectName') {
            List.projectName=e.value
        }  if (e.name=='projectCode') {
            List.projectCode=e.value
        }
        this.setState({ formData: List});
    }
    render() {
        return (
            <div>
                <Form {...this.state.formItemLayout} ref={this.formRef}>
                    <Row>
                        <Col span={24}>
                            <Form.Item  label='项目编号' name="projectCode" rules={[{ required: true, message: '请输入项目编号!' }]}>
                                <Input placeholder='请输入项目编号'
                                       name='projectCode'
                                       onChange={this.handelChange}
                                />
                            </Form.Item>
                        </Col>
                        <Col span={24}>
                            <Form.Item  label='项目名称' name="projectName" rules={[{ required: true, message: '请输入项目名称!' }]}>
                                <Input placeholder='请输入项目名称'
                                       name='projectName'
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

export default projectInput;
