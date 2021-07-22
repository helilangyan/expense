import React,{Component,useEffect} from 'react'
import { Form, Input, Button,Tabs, Checkbox,Select,Row,Col,message } from 'antd';
import { GithubOutlined, LockOutlined, UserOutlined } from '@ant-design/icons';


const { TabPane } = Tabs;
const { Option } = Select;

const FormItem = Form.Item;
// @inject('appStore') @observer @Form.create()
class RegisterForm extends Component {
  //初始化表单
  state = {
    showBox: 'login',
  }
  // 切换注册登录
  switchShowBox = (box) => {
    this.props.switchShowBox(box);
    // return ()=>{
    //   console.log(123);
    // }
  }
  // 输入框存state
  handleChange =(e)=>{
    this.setState({
      [e.target.name] : e.target.value,
    })
  }
  // 注册
  handleRegister = ()=>{
    const {phone,password,showBox} = this.state
    let obj= {
      phone:phone,
      password:password,
    }
    // let postObj=qs.stringify(obj)
    this.post("/user/reg", obj).then(res => {
      if (res.code === 1) {
          message.success('注册'+res.msg);
          this.switchShowBox(showBox)
      } else {
          message.error('注册'+res.msg)
      }
    });
  }
  

  render() {
    const prefixSelector = (
      <Form.Item name="prefix" noStyle>
        <Select
          style={{width: 70,}}
        >
          <Option value="86">+86</Option>
          <Option value="87">+87</Option>
        </Select>
      </Form.Item>
    );
    const {showBox} = this.state
    const that = this;
    // 验证用户名是否重复
    const isRepeatPhone = (_,value)=>{
      return new Promise((resolve, reject) =>{
        that.post("/user/check-account", {account:value}).then(res => {
          if (res.code === 1) {
           resolve();
          }else{
           reject(new Error('Repeat Phone'))
          }
        })
      })
    }
    return (
      <div className={this.props.className}>
        <h3 className='title' style={{marginTop:'20px'}}>Register User</h3>
        <div className="registerBox">
        <Form onFinish={this.handleRegister} initialValues={{prefix:'86'}}>
          <Form.Item
            name="phone"
            rules={[
              { required: true, message: 'Please input your phone number!' },
              {
                validator: isRepeatPhone
              }, 
          ]}
          >
            <Input addonBefore={prefixSelector} style={{ width: '100%'}} placeholder='Phone number' name='phone' onChange={this.handleChange}/>
          </Form.Item>
          {/* <Form.Item>
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
          </Form.Item> */}
          <Form.Item
            name="password"
            rules={[
              {
                required: true,
                message: 'Please input your password!',
              },
            ]}
            hasFeedback
          >
            <Input.Password placeholder='Password' name='password' onChange={this.handleChange}/>
          </Form.Item>
          <Form.Item
            name="confirm"
            dependencies={['password']}
            hasFeedback
            rules={[
              {
                required: true,
                message: 'Please confirm your password!',
              },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue('password') === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(new Error('The password is inconsistent!'));
                },
              }),
            ]}
          >
            <Input.Password placeholder='Confirm password'/>
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" className="loginBtn">
              Confirm
            </Button>
            <span onClick={()=>{this.switchShowBox(showBox)}}>login now!</span>
          </Form.Item>
        </Form>
      </div>
    </div>
    
    )
  }
}

export default RegisterForm