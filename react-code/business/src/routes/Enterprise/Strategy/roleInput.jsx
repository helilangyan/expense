import React, { Component } from 'react';
import { Input, Form, Row, Col, Button } from 'antd';
import { message } from "antd/lib/index";


class roleInput extends Component {
    componentDidMount() {
        this.setState({
            formData: this.props.formData
        });
        console.log(this.props.formData);
        this.formRef.current.setFieldsValue(this.props.formData)
    }
    constructor(props) {
        super(props);
        this.state = {
            formData: {},
            formItemLayout: {
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 5 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 14 },
                },
            },
        }
    }
    formRef = React.createRef();
    submitClick = (e) => {
        this.formRef.current.validateFields().then(() => {
            this.props.submitAdd(this.state.formData);
        }).catch((errorInfo) => {
            message.error("请输入完整信息");
        })

    }
    onCancel = () => {
        this.props.onCancel();
    }
    handleChaange = (o, v) => {
        let data = {...this.state.formData, ...v}
        this.setState({formData: data})
    }
    render() {
        return (
            <div>
                <Form {...this.state.formItemLayout} ref={this.formRef} onValuesChange={this.handleChaange}>
                    <Row>
                        <Col span={24}>
                            <Form.Item label='策略名称' name='strategyName' rules={[{ required: true, message: '请输入策略名称!' }]}>
                                <Input placeholder='请输入策略名称' />
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row>
                        <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
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
