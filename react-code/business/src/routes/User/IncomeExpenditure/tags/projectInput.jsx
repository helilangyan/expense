import React, { Component } from 'react';
import { Input, Form, Row, Col, Button, Select } from 'antd';
import { message } from "antd/lib/index";


const { Option } = Select;
class projectInput extends Component {
  constructor(props) {
    super(props);
    this.state = {
      formData: this.props.formData,
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
  componentDidMount() {
    this.formRef.current.setFieldsValue(this.props.formData)
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
    console.log(data);
    this.setState({formData: data})
}
  render() {
    return (
      <div>
        <Form {...this.state.formItemLayout} ref={this.formRef} onValuesChange={this.handleChaange}>
          <Row>
            <Col span={22} offset={2}>
              <Form.Item label='标签编号' name="labelCode" rules={[{ required: true, message: '请输入标签编号!' }]}>
                <Input placeholder='请输入标签编号'/>
                
              </Form.Item>
              <Form.Item label='标签名称' name="labelName" rules={[{ required: true, message: '请输入标签名称!' }]}>
                <Input placeholder='请输入标签名称'/>
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

export default projectInput;
