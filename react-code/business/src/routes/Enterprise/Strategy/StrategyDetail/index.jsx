import React, { Component } from 'react'
import { withRouter, Switch, Redirect, Link } from 'react-router-dom'
import { Card, Tabs, Radio, Button, Row, Col, Form, Select, message } from 'antd';
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
    formData: {
      strategyId: this.props.strategyId
    },
    formItemLayout: {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 2 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24, offset: 1 },
      },
    },
      tabBtn: '1',
      basicData:{}
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
  componentDidMount() {
    this.getData()
  }
    handleTabClick = (activeKey) => {
        this.setState({ tabBtn: activeKey });
        if (activeKey=='1'){
            this.getData()
        }
        // // this.$child2.getData()
    }
  getData = (e) => {
    this.get(`/enterprise/strategy-basic/` + this.state.formData.strategyId).then(res => {
      if (res.code == 1) {
        this.setState({
            basicData:res.data.data
        })
        this.formRef.current.setFieldsValue(res.data.data)
      }
    })
  }
  handleStatus = (e,v) => {
    // console.log(e);
    let formData = { ...this.state.formData }
    let data = { ...this.state.basicData }
    data={...formData,...data,...v}
    this.post(`/enterprise/strategy-basic/insert`, data, 'application/json').then(res => {
      if (res.code == 1) {
        message.success('操作成功')
          this.getData();
      }
    })
  }

  goback = () => {
    // this.props.history.push({ pathname: "/enterprise/strategy" })
    this.props.closeDetail()
  }

  render() {
    const { currentKey,tabBtn } = this.state;
    return (
      <div className="headBox">
        <Row>
          <Col span='6'>
            费用策略详情
          </Col>
          <Col span='1' offset='17' align='right'>
            <CloseOutlined style={{ fontSize: '16px' }} onClick={this.goback} />
          </Col>
        </Row>
        <Card className="contentBox">
          <Tabs tabPosition="left" defaultActiveKey="1" onTabClick={(key) => this.handleTabClick(key)} activeKey={tabBtn}>
            <TabPane tab="基本设置" key="1">
              <Form {...this.state.formItemLayout} ref={this.formRef} onValuesChange={this.handleStatus}>
                <Row>
                  <Col span={24}>
                    <Form.Item label='当超标时' name="isSubmit" >
                      <Radio.Group name="isSubmit">
                        <Radio value='1'>允许提交并提示</Radio>
                        <Radio value='0'>禁止提交并提示</Radio>
                      </Radio.Group>
                    </Form.Item>
                    <Form.Item label='选择币种' name="currency">
                      <Select name="currency" style={{ width: 120 }}>
                        <Option value="USD">USD $</Option>
                        <Option value="CNY">CNY ¥</Option>
                      </Select>
                    </Form.Item>
                  </Col>
                </Row>
              </Form>
            </TabPane>
            <TabPane tab="费用分类" key="2">
                {tabBtn=='2'?  <ExpenseType strategyId={this.props.strategyId} />:''}
            </TabPane>
            <TabPane tab="标签设置" key="3">
                {tabBtn=='3'?<Tags strategyId={this.props.strategyId} />:''}
            </TabPane>
            <TabPane tab="交通工具" key="4">
                {tabBtn=='4'? <Vehicle strategyId={this.props.strategyId} />:''}
            </TabPane>
          </Tabs>
        </Card>
      </div>
    )
  }
}
