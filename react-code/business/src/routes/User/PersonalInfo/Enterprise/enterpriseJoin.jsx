import React, { Component } from 'react'
import {message,Input, Form, Row, Col, Button,Radio,Select} from 'antd';
import { DoubleRightOutlined} from '@ant-design/icons';
import '../index.css'

const {Option} = Select;

const layout = {
  layout:"vertical",
  labelCol: {
    span: 10,
  },
  wrapperCol: {
    span: 14,
  },
};
export default class enterpriseSet extends Component {
  state={
    toggle:true,
  }
  formRef = React.createRef()

  componentDidMount(){
  }
  onChange = (e,v)=>{
    console.log(e,v);
    this.setState({...v});
  }
  onToggle = ()=>{
    this.setState({
      toggle:!this.state.toggle,
    });
  }
  onSubmit = async ()=>{
    this.formRef.current.validateFields().then(()=>{
      this.props.submitJoin(this.state.InvitationCode)
    }).catch((errorInfo) => {
      message.error("请输入完整信息")
    })
  }
  render() {
    const {toggle,datalist} = this.state;
    return (
      <div>
        <Row gutter={[16, 16]}>
          <Col span={24} offset={5}>
            <Form {...layout} name="inviteCode" onValuesChange={this.onChange} style={toggle?{display:'block'}:{display:'none'}} ref={this.formRef}>
              <Form.Item noStyle>
                <Form.Item label="请输入邀请码" name='InvitationCode' rules={[{ required: true ,message:'请输入邀请码'}]}>
                  <Input placeholder='邀请码'/>
                </Form.Item>
                <p className="g-note" style={{width:'264px'}}>或者通过扫描二维码或打开分享链接加入！</p>
              </Form.Item>
              <Form.Item wrapperCol={{offset:7}}>
                <Button type="link" onClick={this.onToggle}>搜索企业加入<DoubleRightOutlined /></Button>
              </Form.Item>
            </Form>
            <Form {...layout} name="searchEnt" onValuesChange={this.onChange} style={!toggle?{display:'block'}:{display:'none'}}>
              <Form.Item name='inviteCode' label="请搜索企业" rules={[{ required: true ,message:'请搜索企业'}]}>
                {/* <Input placeholder='企业名称' name='enterpriseName'/> */}
                <Select
                  showSearch
                  placeholder="企业名称"
                  optionFilterProp="children"
                  onChange={this.onChange}
                  onSearch={this.onChange}
                  filterOption={(input, option) =>
                    option.toLowerCase().indexOf(input.toLowerCase()) >= 0
                  }
                >
                  { 
                    !datalist?null:datalist.map((item) => {
                      return <Option key={item.id} value={item.id}>{item.projectName}</Option>
                    })
                  }
                </Select>,
              </Form.Item>
              <Form.Item wrapperCol={{offset:7}}>
                <Button type="link" onClick={this.onToggle}>填写邀请码加入<DoubleRightOutlined /></Button>
              </Form.Item>
            </Form>
          </Col>
        </Row>
        <Row>
            <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                <Button type="primary" onClick={this.onSubmit}>申请</Button>
                &nbsp;&nbsp;&nbsp;
                <Button onClick={this.props.onCancel}>关闭</Button>
            </Col>
        </Row>
      </div>
    )
  }
}
