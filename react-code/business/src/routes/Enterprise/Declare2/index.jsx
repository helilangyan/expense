import React, { Component } from 'react';
import { Row, Col, Tabs, Button, Modal, Form, Input, Space, Table, Divider, Select } from 'antd';
import { PlusOutlined, FilterFilled, CopyOutlined, UploadOutlined, DeleteOutlined, AccountBookOutlined } from '@ant-design/icons';
import '../index.css'

import ApplySet from './applySet'


const { TabPane } = Tabs;
const { Option } = Select;

class application extends Component {

  state = {
    formModal: false,
    searchData: {
      roleName: ''
    },
    tableData: [{
      id: '1',
      name: 'BHT1',
      type: '普通企业',
      phone: '0551-56234789',
      contact: 'Kobe Bean Bryant',
      status: '0',
      people: '10',
      rules: '按单收费，每单$2.5;结算方式：自然月',
      approval: '不合格',
      submit: '2021-03-10 15:35:36',
    }],
    tabBtn: '1',
    page: 1,
    pageSize: 10,
    total: 0,
    columns: [
      {
        title: '申请企业',
        dataIndex: 'name',
        width: 150,
      },
      {
        title: '申请人电话',
        dataIndex: 'phone',
        width: 120,
      },
      {
        title: '申请人',
        dataIndex: 'contact',
        width: 120,
      },
      {
        title: '收费规则',
        dataIndex: 'rules',
        width: 300,
      },
      {
        title: '申请状态',
        dataIndex: 'status',
        render: (text, record) => (
          text == 0 ?
            <span>待审批</span> :
            text == 1 ?
              <span>已终止</span> :
              text == 2 ?
                <span>已同意</span> :
                <span>已拒绝</span>
        ),
        width: 100,
      },
      {
        title: '操作',
        key: 'action',
        render: (text, record) => (
          text == 0 ?
            <Button type="link" style={{ padding: '0' }}>审批</Button> :
            text == 1 ?
              <Button type="link" disabled style={{ padding: '0' }}>审批</Button> :
              text == 2 ?
                <Button type="link" danger style={{ padding: '0' }}>终止</Button> :
                <Button type="link" disabled style={{ padding: '0' }}>审批</Button>
        ),
        width: 100
      },
    ],
    // defaultData: {},
  }
  componentDidMount() {
  }
  handleCancel = () => {
    this.setState({
      formModal: false,
    })
  }
  // 添加申请
  addItem = () => {
    this.setState({
      formModal: true
    })
  }
  handleTabClick = (activeKey) => {
    this.setState({ tabBtn: activeKey });
    // this.$child2.getData()
  }


  // onSubmit = (data) => {
  // 	console.log(data);
  // }

  render() {
    const { formModal, tableData,  columns, pageSize, total } = this.state;
    const paginationProps = {
      showSizeChanger: true,//设置每页显示数据条数
      showQuickJumper: false,
      showTotal: () => `共${total}条`,
      pageSize: pageSize,
      total: total,  //数据的总的条数
      onChange: (current) => this.getData(current), //点击当前页码
      onShowSizeChange: (current, pageSize) => {//设置每页显示数据条数，current表示当前页码，pageSize表示每页展示数据条数
        this.onShowSizeChange(current, pageSize)
      }
    }
    return (
      <div>
        <div className="headBox">
          <Row>
            <Col span='6'>
              带填报设置
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <div className="contentBox">
                <div className="contentTitle">
                  <Row>
                    <Col span='16'>
                      <Form layout="inline" onSubmit={this.handleSubmit}>
                        <Form.Item label="企业名称">
                          <Input
                            value={this.state.searchData.roleName}
                            onChange={this.searchChange}
                            placeholder="企业名称"
                          />
                        </Form.Item>
                        <Form.Item label="状态选择">
                          <Select style={{ width: 150 }} placeholder="状态选择">
                            <Option value="0">待提交</Option>
                            <Option value="1">待接单</Option>
                          </Select>
                        </Form.Item>
                        <Form.Item>
                          <Button type="primary" onClick={this.searchClick}>查询</Button>
                          &nbsp; &nbsp;
                          <Button onClick={this.searchReset}>重置</Button>
                        </Form.Item>
                      </Form>
                    </Col>
                  </Row>
                </div>
                <div className="content">
                  <Table columns={columns} dataSource={tableData} rowKey={record => record.id} pagination={paginationProps} />
                </div>
              </div>
            </Col>
          </Row>
        </div>
        <Modal
          width="35%"
          title="申请"
          visible={formModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
        >
          <ApplySet onCancel={this.handleCancel} />
        </Modal>
      </div>
    );
  }
}
export default application;
