import React, { Component } from 'react'
import { withRouter, Switch, Redirect, Link } from 'react-router-dom'
import { Card, Tabs, Radio, Button, Row, Col } from 'antd';
import { PlusOutlined } from '@ant-design/icons';

import BankCard from './BankCard'
import BaseInfo from './BaseInfo';
import BindInfo from './BindInfo';
import Enterprise from './Enterprise'
import Entrust from './Entrust'

import './index.css'
const { TabPane } = Tabs;

// @withrouter
export default class PersonalInfo extends Component {

  state = {
    tabPosition: 'left',
    currentKey: '1',
  };

  // 切换顶部按钮
  handleTab = (key) => {
    this.setState({
      currentKey: key,
    })
  }
 
  // 此事件接收子对象
  childEvevnt = childComp => {
    this.$child = childComp;
  };
  // 父组件触发子组件的事件
  newEnterprise = (i) => {
    this.$child.newEnterprise(i);
  }

  render() {
    const { currentKey } = this.state;
    return (
      <div className="headBox">
        <Row>
          <Col span='6'>
          个人信息
          </Col>
          <Col span='5' offset={13} style={{ textAlign: "right"}}>
            {/* 我创建的企业 */}
            <div style={currentKey == 4 ? { display: 'block' } : { display: 'none' }}>
              <Button type="primary" onClick={() => this.newEnterprise(1)}>创建企业</Button>
              &nbsp;
              <Button type="primary" onClick={() => this.newEnterprise(2)}>加入企业</Button>
              &nbsp;
              <Button type="primary" onClick={() => this.newEnterprise(3)}>申请记录</Button>
              {/* <Link to="/user/personal-info/apply-list"><Button type="primary" onClick={()=>this.newEnterprise(3)}>申请记录</Button></Link> */}
            </div>
            {/* 我的委托 */}
            <div style={currentKey == 5 ? { display: 'block' } : { display: 'none' }}>
              <Button type="primary" ><PlusOutlined />添加委托</Button>
            </div>
          </Col>
        </Row>
        <Card className="contentBox">
          <Tabs tabPosition="left" onTabClick={(key) => this.handleTab(key)} activeKey={currentKey}>
            <TabPane tab="基本信息" key="1">
              {currentKey=='1'?<BaseInfo />:''}
            </TabPane>
            <TabPane tab="绑定信息" key="2">
              {currentKey=='2'?<BindInfo />:''}
            </TabPane>
            <TabPane tab="银行卡" key="3">
              {currentKey=='3'?<BankCard />:''}
            </TabPane>
            <TabPane tab="我创建的企业" key="4">
              {currentKey=='4'?<Enterprise childEvevnt={this.childEvevnt} />:''}
            </TabPane>
            <TabPane tab="工作委托" key="5">
              {currentKey=='5'?<Entrust />:''}
            </TabPane>
          </Tabs>
        </Card>
      </div>
    )
  }
}
