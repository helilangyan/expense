import React, { Component } from 'react'
import { message, Input, Form, Row, Col, Button, Radio } from 'antd';
import '../index.css'

const layout = {
  labelCol: {
    span: 7,
  },
  wrapperCol: {
    span: 14,
  },
};
export default class enterpriseSet extends Component {
  state = {
    type: '',
    enterpriseName: '',
    address: '',
    tel: '',
    taxCode: '',
    linkman: '',
    fileId: '',
  }
  formRef = React.createRef()

  componentDidMount() {
    this.setState({ type: this.props.type })
  }
  onChange = (e, v) => {
    console.log(e,v);
    this.setState({ ...v });
  }
  onCancel = () => {
    this.props.onCancel()
  }
  handleSubmit = () => {
    console.log(this.state);
    this.formRef.current.validateFields().then(() => {
      this.props.submitAdd(this.state)
      setTimeout(this.onCancel, 1000)
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }
  render() {
    const { type } = this.state;

    return (
      <div>
        <Row gutter={[16, 16]}>
          <Col span={24}>
            <Form {...layout} onValuesChange={this.onChange} ref={this.formRef}>
              <Form.Item name='fileId' label="企业logo">
                <Input name='fileId' />
              </Form.Item>
              <Form.Item name='enterpriseName' label="企业名称" rules={[{ required: true, message: '请输入企业名称' }]}>
                <Input placeholder='企业名称' name='enterpriseName' />
              </Form.Item>
              {
                type == 1 ? null :
                  <Form.Item label="是否是财务公司">
                    <Form.Item name="type" rules={[{ required: true, message: '请选择是否是财务公司' }]} noStyle>
                      <Radio.Group>
                        <Radio value={2}>否</Radio>
                        <Radio value={3}>是</Radio>
                      </Radio.Group>
                    </Form.Item>
                    <p className="g-note" style={{ width: '264px' }}>注：如果勾选“是”，代表是财务企业，可以拉取合作企业财务数据，请谨慎选择。</p>
                  </Form.Item>
              }
              <Form.Item name='address' label="企业地址">
                <Input placeholder='企业地址' name='address' />
              </Form.Item>
              <Form.Item
                name="tel"
                label="手机号码"
              >
                <Input placeholder='手机号码' name="tel" />
              </Form.Item>
              <Form.Item name='taxCode' label="企业税号" rules={[{ required: true, message: '请输入企业税号' }]}>
                <Input placeholder='请输入企业税号' name='taxCode' />
              </Form.Item>
              <Form.Item
                label="联系人"
                name="linkman"
              >
                <Input placeholder='请输入联系人' name="linkman" />
              </Form.Item>
            </Form>
          </Col>
        </Row>
        <Row>
          <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
            <Button type="primary" onClick={this.handleSubmit}>确定</Button>
                &nbsp;&nbsp;&nbsp;
                <Button onClick={this.props.onCancel}>关闭</Button>
          </Col>
        </Row>
      </div>
    )
  }
}
