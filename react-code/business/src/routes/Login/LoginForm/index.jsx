import React, { Component } from 'react'
import { Form, Input, Button,Tabs, Checkbox,Select,Row,Col,message } from 'antd';
import {withRouter} from 'react-router-dom'
import {inject, observer} from 'mobx-react/index'
import {randomNum, calculateWidth} from '../../../utils/utils'
// import PromptBox from '../../../components/PromptBox'
import { UserOutlined,LockOutlined,MobileOutlined } from '@ant-design/icons';
import qs from 'qs'
// import {logout} from "../../../utils/Session";


const { TabPane } = Tabs;
const { Option } = Select;

//社交logo
const socialIcons =[
  'wx.svg',
  'fb.svg',
  'in.svg',
  'tw.svg',
]
@withRouter @inject('appStore') @observer
export default class LoginForm extends Component {
  //初始化表单
  state = {
    account:'18226140157',
    // account:'18715159550',
    password:'123456',
    focusItem: -1,   //保存当前聚焦的input
    code: '',
    showBox: 'register',
  }
  formRef = React.createRef()

  componentDidMount() {
    this.createCode()
  }
  // 登录时查找当前用户的企业列表和当前企业
  getCurEnt =()=>{
    this.post("/enterprise-user/user-list").then(res => {
      if (res.code === 1) {
          let cur = res.data.find((i)=>{
              return i.isDefault==1;
            })
          if (cur) {
              this.props.appStore.changeCurrent(cur)
          }else {
              if (res.data.length){
                  this.props.appStore.changeCurrent(res.data[0])
              } else {
                  this.props.appStore.changeCurrent({})
              }
          }
      } else {
          message.error(res.message)
      }
    });
  }
  getUser =async ()=>{
      await this.post("/userinfo/findById").then(res => {
      if (res.code === 1) {
        this.props.appStore.saveUser(res.data)
      } else {
        message.error(res.message)
      }
    });
  }
  /**
     * 生成验证码
     */
  createCode = () => {
    const ctx = this.canvas.getContext('2d')
    const chars = [1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
    let code = ''
    ctx.clearRect(0, 0, 80, 39)
    for (let i = 0; i < 4; i++) {
        const char = chars[randomNum(0, 57)]
        code += char
        ctx.font = randomNum(20, 25) + 'px SimHei'  //设置字体随机大小
        ctx.fillStyle = '#D3D7F7'
        ctx.textBaseline = 'middle'
        ctx.shadowOffsetX = randomNum(-3, 3)
        ctx.shadowOffsetY = randomNum(-3, 3)
        ctx.shadowBlur = randomNum(-3, 3)
        ctx.shadowColor = 'rgba(0, 0, 0, 0.3)'
        let x = 80 / 5 * (i + 1)
        let y = 39 / 2
        let deg = randomNum(-25, 25)
        /**设置旋转角度和坐标原点**/
        ctx.translate(x, y)
        ctx.rotate(deg * Math.PI / 180)
        ctx.fillText(char, 0, 0)
        /**恢复旋转角度和坐标原点**/
        ctx.rotate(-deg * Math.PI / 180)
        ctx.translate(-x, -y)
    }
    this.setState({
        code
    })
  }
  // 登录
  handleLogin =(e)=>{
    const that=this;
    this.setState({
        focusItem: -1
    })

    this.formRef.current.validateFields().then(() => {
      let obj= {...this.state}
      this.post("/user/login", obj).then(res => {
        console.log(res);
        if (res.code === 1) {
            that.props.appStore.toggleLogin(true, {token: res.data})
            that.getCurEnt()
            that.getUser()
            const {from} = that.props.location.state || {from: {pathname: '/'}}
            that.props.history.push(from)
        } else {
          message.error(res.message)
          this.props.appStore.toggleLogin(false)
        }
      });
    }).catch((errorInfo) => {
      message.error("请输入登录信息");
    })
  }
  // 输入框存state
  handleChange =(e)=>{
    this.setState({
      [e.target.name] : e.target.value,
    })
  }
  // 切换注册登录
  switchShowBox = (box) => {
    this.props.switchShowBox(box);
  }


  render() {
    const {account,password,code,showBox} = this.state;
    const validateCode = (_,value) => {//定义规则
      return new Promise((resolve, reject) =>{
        if (!value) {
          reject(new Error('请输入验证码'))
        }
        else{
          if(value !== ''){
            if (value.length >= 4 && code.toUpperCase() !== value.toUpperCase()) {
              return reject("验证码错误");
           }
          }
          resolve();
        }
      })
    };
    const prefixSelector = (
      <Form.Item name="prefix" noStyle>
        <Select
          style={{
            width: 70,
          }}
        >
          <Option value="86">+86</Option>
          <Option value="87">+87</Option>
        </Select>
      </Form.Item>
    );
    return (
        <div className={this.props.className}>
          <h3 className='title'>User Login</h3>
          <div className="socialBox">
              <ul>
                {
                  socialIcons.map((img,id)=>{
                    return(
                      <li key={id}><img src={require("@/assets/img/" + img)} alt=""/></li>
                    )
                  })
                }
              </ul>
              <span>Login by Social account</span>
          </div>
          <div className="loginBox">
            <Tabs defaultActiveKey="1">
              <TabPane tab={
                <span>
                  <UserOutlined/>
                  Login by account
                </span>
              } key="1">
                <Form onFinish={this.handleLogin} ref={this.formRef}>
                  <Form.Item
                    tooltip="What do you want others to call you?"
                    rules={[{ required: true, message: 'Please input your phone/email!', whitespace: true }]}
                  >
                    <Input placeholder='phone/email' className='large' defaultValue={account} name="account" onChange={this.handleChange}/>
                  </Form.Item>
                  <Form.Item
                    rules={[
                      {
                        required: true,
                        message: 'Please input your password!',
                      },
                    ]}
                    hasFeedback
                  >
                    <Input.Password placeholder='password' defaultValue={password} name="password" onChange={this.handleChange}/>
                  </Form.Item>
                  <Form.Item
                    style={{marginBottom:"10px"}}
                    name="Verification code"
                    rules={[{ required: true,validator: validateCode, whitespace: true }
                  ]}
                  >
                    <Row>
                        <Col span={17}>
                            <Input
                                maxLength={4}
                                placeholder='Verification code'
                                className='large'
                              />
                        </Col>
                        <Col span={7}>
                          <canvas onClick={this.createCode} width="80" height='39' className='veriCode'
                            ref={el => this.canvas = el}/>
                        </Col>
                    </Row>

                    {/* <Input placeholder="Verification code" className='large' style={{width:'73%'}}/>
                    <canvas onClick={this.createCode} width="80" height='39' className='veriCode'
                            ref={el => this.canvas = el}/> */}
                  </Form.Item>
                  <Form.Item style={{marginBottom:"10px"}}>
                    <Form.Item name="remember" valuePropName="checked" noStyle>
                      <Checkbox>Remember me</Checkbox>
                    </Form.Item>
                    <a className="login-form-forgot" href="">
                      Forgot password
                    </a>
                  </Form.Item>
                  <Form.Item>
                    <Button type="primary" htmlType="submit" className="loginBtn">
                      Log in
                    </Button>
                    <span onClick={()=>{this.switchShowBox(showBox)}}>register now!</span>
                  </Form.Item>
                </Form>
              </TabPane>
              <TabPane tab={
              <span>
                <MobileOutlined />
                Login by phone
              </span>
            } key="2">
              <Form>
                <Form.Item
                  name="phone"
                  rules={[{ required: true, message: 'Please input your phone number!' }]}
                >
                  <Input addonBefore={prefixSelector} style={{ width: '100%'}} />
                </Form.Item>
                <Form.Item>
                  <Row gutter={8}>
                    <Col span={12}>
                      <Form.Item
                        name="captcha"
                        noStyle
                        rules={[
                          {
                            required: true,
                            message: 'Please input the captcha you got!',
                          },
                        ]}
                      >
                        <Input className='large' placeholder='Verification code'/>
                      </Form.Item>
                    </Col>
                    <Col span={12}>
                      <Button className='capBtn'>Get captcha</Button>
                    </Col>
                  </Row>
                </Form.Item>
                <Form.Item>
                  <Button type="primary" htmlType="submit" className="loginBtn">
                    Log in
                  </Button>
                  <span onClick={()=>{this.switchShowBox(showBox)}}>register now!</span>
                </Form.Item>
              </Form>
            </TabPane>
            </Tabs>
          </div>
        </div>
    )
  }
}

