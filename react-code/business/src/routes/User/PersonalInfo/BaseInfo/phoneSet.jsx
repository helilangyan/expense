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
  
  Verify = ()=>{
    const {data} = this.state;
    // if(data.phone){
    //   // 发送验证码
    //   this.post("/email/email/sendVerificationCode", {phone:data.phone}).then(res => {
    //     if (res.code === 1) {
    //       //发送验证码后按钮倒计时
    //       this.countDown()
    //     } else {
    //         message.error(res.message)
    //     }
    //   });
    // }
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
      this.props.onSubmit('phone',this.state.data)
      setTimeout(this.onCancel,1000)
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }
  render() {
    const {btnText,btnDisable} = this.state;
    const {defaultData} = this.props;
    const prefixSelector = (
      <Form.Item name="prefix" noStyle>
        <Select style={{ width: 80 }}>
          <Option value="86">+86</Option>
          <Option value="87">+87</Option>
        </Select>
      </Form.Item>
    );
    // 验证邮箱
    const validatePhone = (_,value) => {//定义规则
      return new Promise((resolve, reject) =>{
        if (!value) {
          reject(new Error('请输入手机号'))
        }
        else{
          if(value !== ''){
              let reg = /^1[345789]\d{9}$/;
              if (reg.test(value)) {
                resolve();
              } else {
                reject(new Error('请输入正确的手机号'))
              }
          }
          resolve();
        }
      })
    };
    return (
      <div>
          {
            !defaultData.phone?null:
            <Row style={{marginBottom:'20px',textAlign:'center'}}> 
              <Col span={16} offset={3}>
                <p className="g-note">当前绑定手机号码</p>
                <p className="g-note">{defaultData.phone}</p>
              </Col>
            </Row>
          }
          <Row>
            <Col span={16} offset={4}>
              <Form ref={this.formRef} onValuesChange={this.onChange}>
                <Form.Item
                  name="phone"
                  rules={[{ required: true,message: 'Please input your phone number!' }]}
                >
                  <Input style={{ width: '98%' }} placeholder='Phone Number'/>
                </Form.Item>
                <Form.Item >
                  <Row gutter={8}>
                    <Col span={12}>
                      <Form.Item
                        name="VerificationCode"
                        noStyle
                        // rules={[{ required: false, message: 'Please input the captcha you got!' }]}
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
