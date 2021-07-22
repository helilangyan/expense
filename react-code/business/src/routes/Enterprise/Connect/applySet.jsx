import React, { Component } from 'react'
import { message, Input, Form, Row, Col, Button, Switch, Select, DatePicker, Upload } from 'antd';
import { UploadOutlined, EnvironmentOutlined } from '@ant-design/icons';
import moment from 'moment';
import { Back } from 'gsap';

// import './index.css'

const { Option } = Select;

const layout = {
  labelCol: {
    span: 8,
  },
  wrapperCol: {
    span: 13,
  },
};
export default class enterpriseSet extends Component {
  state = {
    // entId: this.props.data.enterpriseId,
    formData: {},
    deptlist: [],
    cardlist: [],
    projlist: [],
  }
  formRef = React.createRef()

  componentDidMount() {
    this.setValue(this.props.defaultData)
  }



  onChange = (e, v) => {
    console.log(v);
    v.money = this.numLimit(v.money)
    this.setState({ formData: v, })
    this.formRef.current.setFieldsValue(v)
  }
  numLimit = (num) => {
    num = num ? num.toString().replace(/[^\d.]/g, '') : ''
    return Number(num)
  }
  onCancel = () => {
    this.props.onCancel();
  }
  setValue = (data) => {
    if (data) {
      data.giveDate = moment(data.giveDate)
      data.tags ? data.tags = data.tags : data.tags = []
      this.setState({ formData: data })
      this.formRef.current.setFieldsValue(data)
    }
  }
  onSubmit = () => {
    const { formData, entId } = this.state
    const { defaultData } = this.props
    let subData = { ...defaultData, ...formData };
    subData.enterpriseId = entId;
    subData.giveDate ? subData.giveDate = subData.giveDate.format('YYYY-MM-DD') : ''

    this.formRef.current.validateFields().then(() => {
      this.props.onSubmit(subData)
      this.onCancel()
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }

  render() {
    const { deptlist, cardlist, projlist, formData, } = this.state;
    return (
      <div>
        <Form {...layout} onValuesChange={this.onChange} ref={this.formRef}>
          <Row>
            <Col span={23}>
              <Form.Item name='explain' label="状态" rules={[{ required: true, message: '请输入连接用户名' }]}>
                <Switch checkedChildren="ON" unCheckedChildren="OFF" defaultChecked />
              </Form.Item>
              <Form.Item name='explain' label="连接用户名" rules={[{ required: true, message: '请输入连接用户名' }]}>
                <Input placeholder='请输入连接用户名' />
              </Form.Item>
              <Form.Item name='explain' label="连接密码" rules={[{ required: true, message: '请输入连接密码' }]}>
                <Input.Password placeholder='请输入连接密码' />
              </Form.Item>
            </Col>
          </Row>
        </Form>
        <Row>
          <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
            <Button type="primary" onClick={this.onSubmit}>确定</Button>
            &nbsp;&nbsp;&nbsp;
            <Button onClick={this.props.onCancel}>取消</Button>
          </Col>
        </Row>
      </div>
    )
  }
}
