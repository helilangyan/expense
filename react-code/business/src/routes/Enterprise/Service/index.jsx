import React, { Component } from 'react';
import { Row, Col, Tabs, Button, Modal, Form, Input, Space, Table, DatePicker, Select, Tooltip } from 'antd';
import { CarryOutOutlined, } from '@ant-design/icons';
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
      type: '续费购买',
      detail: '30用户一年',
      money: '700.00',
      time: '2021-03-10 15:35:36',
      status: 0,
    },
    {
      id: '2',
      type: '增量购买',
      detail: '20用户',
      money: '150.00',
      time: '2021-03-10 15:35:36',
      status: 0,
    },
    {
      id: '3',
      type: '初始购买',
      detail: '10用户一年',
      money: '500.00',
      time: '2021-03-10 15:35:36',
      status: 1,
    }
    ],
    tabBtn: '1',
    page: 1,
    pageSize: 10,
    total: 0,
    columns: [
      {
        title: '购买服务',
        dataIndex: 'type',
        width: 300,
      },
      {
        title: '购买明细',
        dataIndex: 'detail',
        width: 300,
      },
      {
        title: '金额',
        dataIndex: 'money',
        width: 100,
      },
      {
        title: '交易时间',
        dataIndex: 'time',
        width: 120,
      },
      {
        title: '操作',
        key: 'action',
        render: (text, record) => (
          record.status == 1 ?
            <Button type="link" style={{ padding: '0' }}>支付完成</Button> :
            <Button type="link" style={{ color: '#ff0000', padding: '0' }}>立即支付</Button>
        ),
        width: 120
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
    const { formModal, tableData, tabBtn, columns, pageSize, total } = this.state;
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
              服务购买
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <div className="contentBox">
                <div className="content">
                  <p>当前服务：<span className="labelbox">试用</span></p>
                  <p style={{ margin: '20px 0' }}>有效期至：
                    <span className="validtime">2021-03-30</span>
                    <Button type="primary" icon={<CarryOutOutlined />} onClick={this.addItem}>立即购买
                    </Button></p>
                  <Table columns={columns} dataSource={tableData} rowKey={record => record.id} pagination={paginationProps} />
                </div>
              </div>
            </Col>
          </Row>
        </div>
        <Modal
          width="40%"
          title="购买"
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
