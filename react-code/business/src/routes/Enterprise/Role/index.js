import React, { Component } from 'react';
import { Row, Col, Button, Tabs, Form, Input, Table, Divider, Modal, message } from 'antd';
import './style.css'
import RoleInput from './roleInput'
import axios from 'axios'
import Qs from 'qs'
import { baseURL, timeout } from '@/service/url'
import { isAuthenticated, logout } from "@/utils/Session";

const { confirm } = Modal;
class approval extends Component {
  constructor(props) {
    super(props);
    this.state = {
      page: 1,
      pageSize: 10,
      total: 0,
      searchData: {
        roleName: ''
      },
      columns: [
        {
          title: '角色名称',
          dataIndex: 'roleName',
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
              <a >设置角色</a>
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
        roleName: '',
      },
    }
  }
  componentDidMount() {
    this.getData(1);
  }
  getData(current) {
    this.post(`/enterprise/role/list`,
      {
        limit: this.state.pageSize,
        page: current,
        enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId
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
  searchChange = (e) => {
    this.setState({
      searchData: {
        roleName: e.target.value
      }
    })
  }
  searchClick = () => {

  }
  roleInput = () => {
    this.setState({
      roleModal: true
    });
  }
  submitInput = (data) => {
    console.log(data);
    data.enterpriseId = JSON.parse(localStorage.getItem("entInfo")).enterpriseId;
    let newData = {
      roleEntityDto: data
    }
    console.log(data.toString());
    const that = this;
    this.post(`/enterprise/role/insert`, data, 'application/json').then(res => {
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
        that.del(`/enterprise/role/del/` + data.id).then(res => {
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
            角色设置
          </Col>
        </Row>
        <div className="contentBox">
          <div className="contentTitle">
            <Row>
              <Col span='10'>
                <Form layout="inline" onSubmit={this.handleSubmit}>
                  <Form.Item label="用户名">
                    <Input
                      value={this.state.searchData.roleName}
                      onChange={this.searchChange}
                      placeholder="请输入用户名"
                    />
                  </Form.Item>
                  <Form.Item>
                    <Button type="primary" onClick={this.searchClick}>查询</Button>
                    &nbsp; &nbsp;
                    <Button onClick={this.searchReset}>重置</Button>
                  </Form.Item>
                </Form>
              </Col>
              <Col span='2' offset={12} style={{ textAlign: "right",height:'32px' }}>
                <Button type="primary" onClick={this.roleInput} style={{ verticalAlign:'10px' }}>添加</Button>
              </Col>
            </Row>
          </div>
          <div className="content">
            <Table columns={this.state.columns} dataSource={this.state.tabData} rowKey={record => record.id} pagination={paginationProps} />
          </div>
        </div>
        <Modal
          width="30%"
          title="新增角色"
          visible={this.state.roleModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
        >
          <RoleInput formData={this.state.inputForm} submitAdd={this.submitInput} onCancel={this.handleCancel}></RoleInput>
        </Modal>
      </div>
    );
  }
}

export default approval;
