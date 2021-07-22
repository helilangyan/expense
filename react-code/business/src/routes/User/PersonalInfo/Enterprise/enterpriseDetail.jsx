import React, {Component} from 'react';
import {Input, Form, Row, Col, Button} from 'antd';

class enterpriseDetail extends Component {

  state = {
    formItemLayout: {
      labelCol: { span: 10 },
      wrapperCol: { span: 20},
    },
  }
  formRef = React.createRef()
  componentDidMount(){
    if(this.props.defaultData){
      this.getDefault();
    }
  }
  // 默认值
  getDefault = ()=>{
    let defaultData = this.props.defaultData;
    let type = defaultData.type
    if(type==1){
      defaultData.type='普通企业'
      // defaultData.finance='是'
    }else if(type==2){
      defaultData.type='第三方企业'
      defaultData.finance='是'
    }else if(type==3){
      defaultData.type='其他企业'
      defaultData.finance='否'
    }
    this.formRef.current.setFieldsValue(this.props.defaultData);
  }
  handelChange=(event)=>{
      let e = event.target;
      const List= this.state.dataList;
      List.forEach((item, index) => {
        if (item.name === e.name) {
          item.value = e.value;
        }
      });
      this.setState({ dataList: List});
  }

  render() {
    const {defaultData} = this.props
      return (
          <div style={{margin:'0 -20px 0 40px'}}>
              <Form {...this.state.formItemLayout} 
              layout="vertical"
              ref = {this.formRef} 
              labelAlign="left">
                  <Row>
                    <Col span={12}>
                      <Form.Item label="企业Logo" 
                        rules={[{ required: true, message: 'Please input your username!' }]}
                      >
                        <div className="itemlogo">
                          <img src={require("@/assets/userImg/enter1.png")} alt=""/>
                        </div>
                      </Form.Item>
                      <Form.Item label="是否是财务企业" name="finance" >
                        <Input type="text" />
                      </Form.Item>
                      <Form.Item label="联系人" name="linkman" >
                        <Input type="text" />
                      </Form.Item>
                      <Form.Item label="联系电话" name="tel">
                        <Input type="text" />
                      </Form.Item>
                    </Col>
                    <Col span={12}>
                      <Form.Item label="企业名称" name="enterpriseName" >
                        <Input type="text" />
                      </Form.Item>
                      <Form.Item label="企业类型" name="type" >
                        <Input type="text" />
                      </Form.Item>
                      <Form.Item label="企业地址" name="address" >
                        <Input type="text" />
                      </Form.Item>
                      <Form.Item label="税号" name="taxCode" >
                        <Input type="text" />
                      </Form.Item>
                      <Form.Item label="邀请码" name="invitationCode" >
                        <Input type="text" />
                      </Form.Item>
                    </Col>
                  </Row>
                  <Row>
                      <Col span={24} style={{textAlign: 'center', marginTop: "20px",paddingRight:'60px'}}>
                          {/* <Button type="primary" onClick={this.handleSubmit}>确定</Button> */}
                          <Button onClick={this.props.onCancel}>关闭</Button>
                      </Col>
                  </Row>
              </Form>
          </div>
      );
  }
}

export default enterpriseDetail;