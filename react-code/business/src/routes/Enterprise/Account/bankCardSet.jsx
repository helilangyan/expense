import React, { Component } from 'react';
import { Input, Form, Row, Col, Button,message } from 'antd';


// const { getFieldDecorator } = this.props.form;
class BankCardSet extends Component {

  state = {
    formData:{}
  }
  formRef = React.createRef()

  componentDidMount() {
    if (this.props.defaultData) {
      this.getDefault();
    }
  }
  // 默认值
  getDefault = () => {
    const defaultData = this.props.defaultData;
    this.formRef.current.setFieldsValue(defaultData)
  }
  handleSubmit = (e) => {
    const {formData} = this.state
    const {defaultData,entId} = this.props
    let data = formData;
    data.enterpriseId=entId
    defaultData? data.id = defaultData.id:null
    this.formRef.current.validateFields().then(() => {
      this.props.submitAdd(data)
      setTimeout(this.onCancel, 1000)
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }
  handelChange = (e,v) => {
    this.setState({ formData: v });
  }

  render() {
    const formItemLayout= {
      labelCol: { span: 10 },
      wrapperCol: { span: 14 },
    }
    return (
      <div>
        <Form {...formItemLayout} onValuesChange={this.handelChange} ref={this.formRef}>
          <Row>
            <Col span={18}>
              <Form.Item label="银行名称" name="bankName" rules={[{ required: true, message: 'Please input bank name!' }]}>
                <Input placeholder="请输入银行名称" />
              </Form.Item>
              <Form.Item label="银行卡号" name="bankAccount" rules={[{ required: true, message: 'Please input bank account!' }]}>
                <Input placeholder="请输入银行卡号" />
              </Form.Item>
              <Form.Item label="开户姓名" name="bankHolder" rules={[{ required: true, message: 'Please input bank holder!' }]}>
                <Input placeholder="请输入开户姓名" />
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
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