import React, {Component} from 'react';
import {Input, Form, Row, Col, Button} from 'antd';


class VerifyInput extends Component {

  state = {
    formItemLayout: {
      labelCol: { span:8 },
      wrapperCol: { span: 12},
      
    },
   
  }
  componentDidMount(){
    // console.log(this.props.defaultData);
    if(this.props.defaultData){
      this.getDefault();
    }
  }
  // 默认值
  getDefault = ()=>{
    const defaultData = this.props.defaultData;
    let data = this.state.dataList
    data.forEach(i=>{
      Object.keys(defaultData).forEach((key)=>{
        if(i.name === key){
          i.value = defaultData[key];
        }
      });
    })
    this.setState({
      dataList:data,
    })
  }
  handleSubmit = (e) => {
    const data={};
      this.state.dataList.forEach(i=>{
        console.log(i);
        data[i.name]=i.value
      })
      if(this.props.defaultData){data.id=this.props.defaultData.id};
      this.props.submitAdd(data)
  }
  submitClick = () => {
      //验证用户名不能为纯数字（未做）
      // this.props.submitAdd(this.state.dataList)
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
      return (
          <div style={{marginTop:'20px'}}>
              <Form {...this.state.formItemLayout}>
                  <Row>
                    {
                      <Col span={24}>
                        <Form.Item label='Federal Tax ID'
                          name="TaxID"
                          rules={[{ required: true, message: 'Please input your username!' }]}
                        >
                          <Input
                              type='text'
                              name='TaxID'
                              onChange={this.handelChange}
                              placeholder='请输入Federal Tax ID'
                          />
                        </Form.Item>
                      </Col>
                    }
                  </Row>
                  <Row>
                      <Col span={24} style={{textAlign: 'center', marginTop: "20px",}}>
                          <Button type="primary" onClick={this.handleSubmit}>提交</Button>
                          &nbsp;&nbsp;&nbsp;
                          <Button onClick={this.props.onCancel}>关闭</Button>
                      </Col>
                  </Row>
              </Form>
          </div>
      );
  }
}

export default VerifyInput;