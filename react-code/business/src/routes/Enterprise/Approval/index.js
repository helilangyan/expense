import React, { Component } from 'react';
import { Row, Col, Button, Tabs, message } from 'antd';
// import Bpmn from './bpmn/bpmn'
import Bpmn from './newBpmn/bnmn'
import './style.module.css'
import axios from 'axios'
import Qs from 'qs'
import { isAuthenticated, logout } from "@/utils/Session";

const { TabPane } = Tabs;

class approval extends Component {
  constructor(props) {
    super(props);
    this.state = {
      xmlText: '',
      xmlType: '1',
      enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId
    }
  }
  componentDidMount() {

  }

  textChange = (text) => {
    this.setState({
      xmlText: text
    })
    // console.log(text);
    // console.log(name);
  }
  xmlSave = () => {
    let data = {
      enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
      type: this.state.xmlType,
      workflowName: '报销申请',
      xmlText: this.state.xmlText,
    }
    console.log(this.state.xmlText);
    this.post(`/bpm/design/design/deploy/str`, data).then(res => {
      if (res.code == 1) {
        message.success('保存成功')
      }
    })
  }
  callback = (key) => {
    console.log(key);
    this.setState({
      xmlType: key
    })
  }
  render() {
    return (
      <div className="headBox">
        <Row>
          <Col span='6'>
            审批流程设计
          </Col>
          <Col span='2' offset={16} style={{ textAlign: "right" }}>
            <Button type="primary" onClick={this.xmlSave}>保存</Button>
          </Col>
        </Row>
        <div className="contentBox">
          <div className="content">
            <Tabs tabPosition={'left'} onChange={this.callback} value={this.state.xmlType}>
              <TabPane tab="报销申请" key="1">
                {this.state.xmlType == '1' ? <Bpmn xmlType={this.state.xmlType} textChange={this.textChange} /> : ''}
              </TabPane>
              <TabPane tab="出差申请" key="2">
                {this.state.xmlType == '2' ? <Bpmn xmlType={this.state.xmlType} textChange={this.textChange} /> : ''}
              </TabPane>
              <TabPane tab="借款申请" key="3">
                {this.state.xmlType == '3' ? <Bpmn xmlType={this.state.xmlType} textChange={this.textChange} /> : ''}
              </TabPane>
            </Tabs>
          </div>
        </div>
      </div>
    );
  }
}

export default approval;
