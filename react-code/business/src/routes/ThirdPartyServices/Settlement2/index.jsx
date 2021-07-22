import React, { Component } from 'react';
import { Row, Col, Tabs, Button, Modal, Form, Input, Space, Table, DatePicker, Select,Tooltip} from 'antd';
import { QuestionCircleFilled, } from '@ant-design/icons';
import '../style.css'
// import ApplyList from './applyList'
// import LoanList from './loanList'
// import TravelList from './travelList'

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
      month: '2021/04',
      type: 'BHT1',
      count: '55',
      money: '$ 156.00',
      date: '2021-03-10',
      createTime: '2021-03-10 15:35:36',
      submit: '2021-03-10 15:35:36',
      status: '0',
    }],
    tabBtn: '1',
    page: 1,
    pageSize: 10,
    total: 0,
    columns: [
      {
        title: '结算月份',
        dataIndex: 'month',
        width: 100,
      },
      {
        title: '企业名称',
        dataIndex: 'type',
        width: 100,
      },
      {
        title:
          <div>完成单数 
            <Tooltip placement='bottom' title='完成单数统计时间按最后的确认时间统计'>
              <QuestionCircleFilled style={{color:'#666',paddingLeft:'10px'}}/>
            </Tooltip>
          </div>,
        dataIndex: 'count',
        width: 100,
      },
      {
        title: '金额',
        dataIndex: 'money',
        width: 100,
      },
      {
        title: '状态',
        dataIndex: 'status',
        render: (text, record) => (
          text == 0 ?
            <span>待付款</span> :
            <span>已付款</span>
        ),
        width: 100,
      },
      {
        title: '操作',
        key: 'action',
        render: (text, record) => (
          record.status == 1 ?
            <Button type="link">催促付款</Button> :
            <Button type="link" style={{ color: '#08B365', padding: '0' }}>收款详情</Button>
        ),
        width: 80
      },
    ],
    // defaultData: {},
  }
  componentDidMount() {
    // this.token = PubSub.subscribe('refresh', (_, data) => {
    // 	this.setState({ data }, () => {
    // 		this['$child' + data.tabBtn].getData()
    // 	})
    // })
  }
  handleCancel = () => {
    this.setState({
      formModal: false,
    })
  }
  // // 打开审批人
  // openSend = (data, type) => {
  //   console.log(data);
  //   this.setState({
  //     formModal: false,
  //     sendModal: true,
  //     applyData: data,
  //     applyType: type
  //   })
  // }
  // //审批人确认
  // submitAdd = (data) => {
  //   console.log(data);
  //   console.log(this.state.applyData);
  // }
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
  // 此事件接收子对象
  childEvevnt = (childComp) => {
    this.$child = childComp
  };


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
              结算管理
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
                            placeholder="企业名称模糊搜索"
                          />
                        </Form.Item>
                        <Form.Item label="企业名称">
                          <DatePicker picker="month"/>
                        </Form.Item>
                        <Form.Item label="状态选择">
                          <Select style={{ width: 150 }} placeholder="状态选择">
                            <Option value="0">全部</Option>
                            <Option value="1">待付款</Option>
                            <Option value="2">已付款</Option>
                          </Select>
                        </Form.Item>
                        <Form.Item>
                          <Button type="primary" onClick={this.searchClick}>查询</Button>
                          &nbsp; &nbsp;
                          <Button onClick={this.searchReset}>重置</Button>
                        </Form.Item>
                      </Form>
                    </Col>
                    <Col span='2' offset={6} style={{ textAlign: "right", height: '32px' }}>
                      <Button type="primary" onClick={this.addItem} style={{ verticalAlign: '10px' }}>添加</Button>
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
