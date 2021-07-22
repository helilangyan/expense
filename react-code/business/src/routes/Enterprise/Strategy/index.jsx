import React, { Component } from 'react';
import { Row, Col, Button, Tabs, Form, Input, Table, Divider, Modal, message } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import '../index.css'
import axios from 'axios'
import Qs from 'qs'
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import { baseURL, timeout } from '@/service/url'
import { isAuthenticated, logout } from "@/utils/Session";
import RoleInput from './roleInput'
import StaffInput from './staffInput'
import StrategyDetail from './StrategyDetail'

const { confirm } = Modal;

@inject('appStore') @observer
class approval extends Component {
  state = {
    data: toJS(this.props.appStore.curEnt),
    page: 1,
    pageSize: 10,
    total: 0,
    visible: false,//策略详情
    strategyId:0,
    searchData: {
      roleName: ''
    },
    tableData: [],
    roleModal: false,
    staffModal: false,
    columns: [
      {
        title: '策略名称',
        dataIndex: 'strategyName',
        width: 720,
      },
      {
        title: '操作',
        key: 'action',
        render: (text, record) => (
          <span style={{ marginLeft: '-15px' }}>
            <Button type="text" style={{ color: '#0046FF' }} onClick={()=>this.setDetail(record)}>策略详情</Button>
            <Divider type="vertical" />
            <Button type="link" onClick={() => { this.editClick(record); }}>编辑</Button>
            <Divider type="vertical" />
            <Button type="text" danger onClick={() => { this.deleteClick(record); }}>删除</Button>
          </span>
        ),
        width: 300
      },
    ],

  }
  componentDidMount() {
    this.getData(1);
  }
  getData(current) {
    this.post(`/enterprise/strategy/list`,
      {
        limit: this.state.pageSize,
        page: current,
        enterpriseId: this.state.data.enterpriseId
      }
    ).then(res => {
      if (res.code == 1) {
        this.setState({
          tableData: res.data.data,
          total: res.data.count
        });
      }

    })
  }
  searchChange = (e) => {
    this.setState({
      searchData: {
        roleName: e.target.value
      }
    })
  }
  searchClick = () => {

  }
  setDetail = (e) => {
    // this.props.history.push({ pathname: "/enterprise/strategy-detail" })
    this.setState({
      visible:true,
      strategyId:e.id
    })
  }
  closeDetail = () => {
    // this.props.history.push({ pathname: "/enterprise/strategy-detail" })
    this.setState({
      visible:false
    })
  }
  setStaff = ()=>{
    this.setState({
      staffModal: true
    });
  }
  submitStaff = ()=>{
    this.post(`/enterprise/strategy/insert`, data, 'application/json').then(res => {
      if (res.code == 1) {
        that.handleCancel();
        that.getData(this.state.page);
        message.success('操作成功')
      }
    })
  }
  roleInput = () => {
    this.setState({
      roleModal: true
    });
  }
  submitInput = (data) => {
    console.log(data);
    data.enterpriseId = this.state.data.enterpriseId
    const that = this;
    this.post(`/enterprise/strategy/insert`, data, 'application/json').then(res => {
      if (res.code == 1) {
        that.handleCancel();
        that.getData(this.state.page);
        message.success('操作成功')
      }
    })

  }
  handleCancel = (data) => {
    this.setState({
      roleModal: false,
      staffModal: false,
      inputForm: {
        roleName: '',
      }
    });
  }
  editClick(data) {
    console.log(data);
    this.setState({
      inputForm: data,
      roleModal: true,
    });
  }
  deleteClick(data) {
    const that = this;
    confirm({
      title: '确认删除?',
      content: '',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        that.del(`/enterprise/strategy/dels/` + data.id).then(res => {
          if (res.code == 1) {
            message.success('删除成功')
            that.getData(that.state.page)
          } else {
            message.error(res.msg)
          }
        })
      },
      onCancel() {
        console.log('Cancel');
      },
    });
  }
  render() {
    const { visible } = this.state
    const paginationProps = {
      showSizeChanger: true,//设置每页显示数据条数
      showQuickJumper: false,
      showTotal: () => `共${this.state.total}条`,
      pageSize: this.state.pageSize,
      total: this.state.total,  //数据的总的条数
      onChange: (current) => this.getData(current), //点击当前页码
      onShowSizeChange: (current, pageSize) => {//设置每页显示数据条数，current表示当前页码，pageSize表示每页展示数据条数
        this.onShowSizeChange(current, pageSize)
      }
    }
    return (
      <div>
        {
          !visible ?
            <div className="headBox">
              <Row>
                <Col span='6'>
                  费用策略设置
                </Col>
              </Row>
              <div className="contentBox">
                <div className="contentTitle">
                  <Row>
                    <Col span='10'>
                      <Form layout="inline" onSubmit={this.handleSubmit}>
                        <Form.Item label="策略名称">
                          <Input
                            value={this.state.searchData.roleName}
                            onChange={this.searchChange}
                            placeholder="请输入策略名称"
                          />
                        </Form.Item>
                        <Form.Item>
                          <Button type="primary" onClick={this.searchClick}>查询</Button>
                          &nbsp; &nbsp;
                          <Button onClick={this.searchReset}>重置</Button>
                        </Form.Item>
                      </Form>
                    </Col>
                    <Col span='2' offset={12} style={{ textAlign: "right", height: '32px' }}>
                      <Button type="primary" onClick={this.roleInput} style={{ verticalAlign: '10px' }}>添加</Button>
                    </Col>
                  </Row>
                </div>
                <div className="content">
                  <Table columns={this.state.columns} dataSource={this.state.tableData} rowKey={record => record.id} pagination={paginationProps} />
                </div>
              </div>
              <Modal
                width="30%"
                title="新增策略"
                visible={this.state.roleModal}
                footer={null}
                onCancel={this.handleCancel}
                destroyOnClose={true}
              >
                <RoleInput formData={this.state.inputForm} submitAdd={this.submitInput} onCancel={this.handleCancel}></RoleInput>
              </Modal>
              <Modal
                width="30%"
                title="配置人员"
                visible={this.state.staffModal}
                footer={null}
                onCancel={this.handleCancel}
                destroyOnClose={true}
              >
                <StaffInput formData={this.state.inputForm} submitAdd={this.submitInput} onCancel={this.handleCancel}></StaffInput>
              </Modal>
            </div> :
            <StrategyDetail closeDetail={this.closeDetail} strategyId={this.state.strategyId} defaultData={this.state.data}/>
        }
      </div>

    );
  }
}

export default approval;
