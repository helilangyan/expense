import React, { Component } from 'react'
import { Row,Col,Avatar,Form,Input,Button,message,Modal ,Select} from 'antd';
const {Option } = Select;

export default class emailSet extends Component {
  state={
    data:{},
    phoneModal:false,
    emailModal:false,
    btnText:'Verification Code',
    btnDisable:false,
  }
  formRef = React.createRef()
  
  onChange = (e,v)=>{
    this.setState({
      data:{...v},
    })
  }
  onCancel = ()=>{
    this.props.onCancel()
  }
  onSubmit = ()=>{
    this.formRef.current.validateFields().then(()=>{
      console.log(this.state.data);
      this.props.onSubmit('password',this.state.data)
      setTimeout(this.onCancel,1000)
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }
  render() {
    return (
      <div>
          <Row>
            <Col span={16} offset={4}>
              <Form ref={this.formRef} onValuesChange={this.onChange}>
                <Form.Item
                  name="oldPassword"
                  rules={[{ required: true,message: 'Please input Old Password!' }]}
                >
                  <Input style={{ width: '98%' }} placeholder='Old Password'/>
                </Form.Item>
                <Form.Item
                  name="newPassword"
                  rules={[{ required: true,message: 'Please input New Password!' }]}
                >
                  <Input style={{ width: '98%' }} placeholder='New Password'/>
                </Form.Item>
              </Form>
            </Col>
          </Row>
          <Row>
            <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                <Button type="primary" onClick={this.onSubmit}>确定</Button>
                &nbsp;&nbsp;&nbsp;
                <Button onClick={this.onCancel}>关闭</Button>
            </Col>
          </Row>
      </div>
    )
  }
}
