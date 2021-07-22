import React, { Component } from 'react';
import { Row, Col, Button, Tabs, Form, Input, Table, Divider, Modal, message } from 'antd';
import './style.css'
import ProjectInput from './projectInput'
import axios from 'axios'
import Qs from 'qs'
import { baseURL, timeout } from '@/service/url'
import { isAuthenticated, logout } from "@/utils/Session";
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'


const { confirm } = Modal;
@inject('appStore') @observer
class project extends Component {
  constructor(props) {
    super(props);
    this.state = {
      page: 1,
      pageSize: 10,
      total: 0,
      columns: [
        {
          title: '项目编号',
          dataIndex: 'projectCode',
          width: 120,
        },
        {
          title: '项目名称',
          dataIndex: 'projectName',
          width: 120,
        },
        {
          title: '操作',
          key: 'action',
          render: (text, record) => (
            <span>
              <a onClick={() => {
                this.editClick(record);
              }}>编辑</a>
              <Divider type="vertical" />
              <a onClick={() => {
                this.deleteClick(record);
              }}>删除</a>
            </span>
          ),
          width: 200
        },
      ],
      tabData: [],
      roleModal: false,
      inputForm: {
        projectName: '',
        enterpriseId:toJS(this.props.appStore.curEnt).enterpriseId,
      },
      enterpriseId: toJS(this.props.appStore.curEnt).enterpriseId
    }
  }
  componentDidMount() {
    this.getData(1);
  }
  getData(current) {
    this.post(`/enterprise/project/findByEnterpriseId`,
      {
        limit: this.state.pageSize,
        page: current,
        enterpriseId: this.state.enterpriseId
      }
    ).then(res => {
      if (res.code == 1) {
        this.setState({
          tabData: res.data.data,
          total: res.data.count
        });
      }

    })
  }
  roleInput = () => {
    this.setState({
      roleModal: true
    });
  }
  submitInput = (data) => {
    const that = this;
    this.post(`/enterprise/project/insert`, data, 'application/json').then(res => {
      if (res.code == 1) {
        that.handleCancel();
        that.getData(this.state.page);
        message.success('操作成功')
      }
    })
  }
  handleCancel = () => {
    this.setState({
      roleModal: false,
      inputForm: {
        projectName: '',
        projectCode: '',
        enterpriseId: this.state.enterpriseId,
      },
    });
  }
  editClick(data) {
    this.setState({
      inputForm: data,
      roleModal: true,
    });
  }
  deleteClick = (data) => {
    const that = this;
    confirm({
      title: '确认删除?',
      content: '',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        that.del(`/enterprise/project/delete/` + data.id).then(res => {
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
      <div className="headBox">
        <Row>
          <Col span='6'>
            个人信息
          </Col>
          <Col span='2' offset={16} style={{ textAlign: "right" }}>
            <Button type="primary" onClick={this.roleInput}>添加</Button>
          </Col>
        </Row>
        <div className="contentBox">
          <div className="content">
            <Table columns={this.state.columns} dataSource={this.state.tabData} rowKey={record => record.id} pagination={paginationProps} />
          </div>
        </div>
        <Modal
          width="30%"
          title="项目设置"
          visible={this.state.roleModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
        >
          <ProjectInput formData={this.state.inputForm} submitAdd={this.submitInput} onCancel={this.handleCancel}></ProjectInput>
        </Modal>
      </div>

    );
  }
}

export default project;
