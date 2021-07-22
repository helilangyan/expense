import React, { Component } from 'react'
import { withRouter, Switch, Redirect, Link } from 'react-router-dom'
import { Card, Tabs, Radio, Button, Row, Col, Form, Select,message } from 'antd';
import { CloseOutlined, PlusOutlined } from '@ant-design/icons';

import ExpenseType from './expenseType'
import Tags from './tags'
import Vehicle from './vehicle';


// import './index.css'
const { TabPane } = Tabs;
const { Option } = Select;

// @withrouter
export default class PersonalInfo extends Component {

  state = {
    tabPosition: 'left',
    currentKey: 1,
    formData:{
      strategyId:this.props.strategyId
    },
    formItemLayout: {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 2 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 ,offset:1},
      },
    },
  };
  formRef = React.createRef();

  // 切换顶部按钮
  // handleTab = (key) => {
  //   this.setState({
  //     currentKey: key,
  //   })
  // }

  // 此事件接收子对象
  // childEvevnt = childComp => {
  //   this.$child = childComp;
  // };
  // 父组件触发子组件的事件
  // newEnterprise = (i) => {
  //   this.$child.newEnterprise(i);
  // }
  componentDidMount(){
    this.getData()
  }
  getData = (e) => {
    this.get(`/enterprise/strategy-basic/`+this.state.formData.strategyId).then(res => {
      if (res.code == 1) {
        this.formRef.current.setFieldsValue(res.data.data)
      }
    })
  }
  handleStatus = (e) => {
    console.log(e);
    let data ={...this.state.formData}
    if(e=='CNY'){
      data.currency=e
    }else{
      data.isSubmit=e.target.value
    }
    console.log(data);
    this.post(`/enterprise/strategy-basic/update`, data, 'application/json').then(res => {
      if (res.code == 1) {
        message.success('操作成功')
      }
    })
  }

  goback = () => {
    // this.props.history.push({ pathname: "/enterprise/strategy" })
    this.props.closeDetail()
  }

  render() {
    const { currentKey } = this.state;
    return (
      <div className="headBox">
        <Row>
          <Col span='6'>
          收支设置
          </Col>
          <Col span='1' offset='17' align='right'>
            <CloseOutlined style={{ fontSize: '16px' }} onClick={this.goback} />
          </Col>
        </Row>
        <Card className="contentBox">
          <Tabs tabPosition="left" defaultActiveKey="1" >
            <TabPane tab="基本设置" key="1">
              <Form {...this.state.formItemLayout} ref={this.formRef}>
                <Row>
                  <Col span={24}>
                    <Form.Item label='选择币种' name="currency">
                      <Select name="currency" style={{ width: 120 }} onChange={this.handleStatus}>
                        <Option value="USD">USD $</Option>
                        <Option value="CNY">CNY ¥</Option>
                      </Select>
                    </Form.Item>
                  </Col>
                </Row>
              </Form>
            </TabPane>
            <TabPane tab="收支分类" key="2">
              <ExpenseType strategyId={this.props.strategyId}/>
            </TabPane>
            <TabPane tab="标签设置" key="3">
              <Tags strategyId={this.props.strategyId}/>
            </TabPane>
            {/* <TabPane tab="交通工具" key="4">
              <Vehicle strategyId={this.props.strategyId}/>
            </TabPane> */}
          </Tabs>
        </Card>
      </div>
    )
  }
}
