import React, { Component } from 'react'
import { Row,Col,Avatar,Form,Input,Button,message,Modal ,Select} from 'antd';

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
  

  Verify = ()=>{
    const {data} = this.state;
    if(data.email){
      // 发送验证码
      this.post("/email/email/sendVerificationCode", {emailAddress:data.email}).then(res => {
        if (res.code === 1) {
          //发送验证码后按钮倒计时
          this.countDown()
        } else {
            message.error(res.message)
        }
      });
    }
  }
  countDown = ()=>{
    let that = this
    let times=60
    let timer = setInterval(function () {
      times--;
      if (times <= 0) {
        that.setState({btnText:"重发"}) 
        clearInterval(timer);
        times = 60;
      } else {
        that.setState({btnText:times+"秒后重发",btnDisable:true,})
      }
    }, 1000)
  }
  onSubmit = ()=>{
    this.formRef.current.validateFields().then(()=>{
      console.log(this.state.data);
      this.props.onSubmit('email',this.state.data)
      setTimeout(this.onCancel,1000)
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }
  render() {
    const {btnText,btnDisable} = this.state;
    const {defaultData} = this.props;
    // 验证邮箱
    const validateEmail =(_,value)=>{
      return new Promise((resolve, reject) =>{
        if (value === '') {
          reject(new Error('请输入邮箱'))
        } else {
          if (value !== '') {
              let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
              if (!reg.test(value)) {
                reject(new Error('请输入正确的邮箱'))
              }
          }
          resolve();
        }
      })
    };
    return (
      <div>
        {
          !defaultData.email?null:
          <Row style={{marginBottom:'20px',textAlign:'center'}}> 
            <Col span={16} offset={3}>
              <p className="g-note">当前绑定邮箱</p>
              <p className="g-note">{defaultData.email}</p>
            </Col>
          </Row>
        }
          <Row>
            <Col span={16} offset={4}>
              <Form ref={this.formRef} onValuesChange={this.onChange}>
                <Form.Item
                  name="email"
                  rules={[{ required: true,validator:validateEmail},
                  ]}
                >
                  <Input style={{ width: '98%' }} placeholder='Email'/>
                </Form.Item>
                <Form.Item >
                  <Row gutter={8}>
                    <Col span={12}>
                      <Form.Item
                        name="verificationCode"
                        noStyle
                        rules={[{ required: true, message: 'Please input verification Code!' }]}
                      >
                        <Input style={{ width: '100%' }} placeholder='Verification Code'/>
                      </Form.Item>
                    </Col>
                    <Col span={12}>
                      <Button type="primary" onClick={this.Verify} style={{width:'133px'}} disabled={btnDisable}>{btnText}</Button>
                    </Col>
                  </Row>
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
