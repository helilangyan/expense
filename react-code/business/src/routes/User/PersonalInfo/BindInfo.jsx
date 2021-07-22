import React, { Component } from 'react'
import { Row,Col,Avatar,Form,Input,Button } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { post,get } from '../../../service/requestHttp';


const layout = {
  layout:"vertical",
  labelCol: {
    span: 6,
  },
  wrapperCol: {
    span: 18,
  },
};

export default class BaseInfo extends Component {
  state={
    data:{},
  }
  onChange = (e,v)=>{
    console.log(v);
    // let formdata =v;
    this.setState({
      data:{...v},
    })
  }
  onSubmit = (e)=>{
    const {data} = this.state
    Object.keys(data).forEach(function(key){
     if(e==key){
       [key]=data[key]
     }
    });
    post("/expense-user-server/userinfo/insert", data).then(res => {
      if (res.code === 1) {

      } else {
          // message.error(res.message)
      }
    });
  }
  render() {
    return (
      <div style={{marginLeft:'30px'}}>
        <Row>
          <Col span={10}>
            <Form {...layout} name="nest-messages" className="user-info" onValuesChange={this.onChange}>
              <Form.Item label="绑定微信">
                <Form.Item
                  name="weixin"
                  rules={[{ required: true, message: 'Please input your phone number!' }]}
                  noStyle
                >
                  <Input placeholder='手机号码'/>
                </Form.Item>
                <Button type="text" onClick={()=>this.onSubmit('phone')}>绑定</Button>
              </Form.Item>
              <Form.Item label="绑定facebook">
                <Form.Item
                  name="facebook"
                  rules={[{ required: true, message: 'Please input your phone number!' }]}
                  noStyle
                >
                  <Input placeholder='手机号码'/>
                </Form.Item>
                <Button type="text" onClick={()=>this.onSubmit('phone')}>绑定</Button>
              </Form.Item>
              <Form.Item label="绑定linkedin">
                <Form.Item name="email" rules={[{ type: 'email' }]} noStyle>
                  <Input placeholder='绑定邮箱'/>
                </Form.Item>
                <Button type="text">绑定</Button>
              </Form.Item>
              <Form.Item label="绑定Twitter">
                <Form.Item
                  name="twitter"
                  rules={[{ required: true, message: 'Please input your password!' }]}
                  noStyle
                >
                  <Input placeholder='登录密码'/>
                </Form.Item>
                <Button type="text" onClick={()=>this.onSubmit('password')}>绑定</Button>
              </Form.Item>
            </Form>
          </Col>
        </Row>
      </div>
    )
  }
}
