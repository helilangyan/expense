import React, { Component } from 'react';
import { Row, Col, Tabs, Button, Modal, Form, Input, Space, Table, Divider,Select } from 'antd';
import { PlusOutlined, FilterFilled, CopyOutlined, UploadOutlined, DeleteOutlined, AccountBookOutlined } from '@ant-design/icons';
import '../style.css'
// import ApplyList from './applyList'
// import LoanList from './loanList'
// import TravelList from './travelList'

import ApplySet from './applySet'


const { TabPane } = Tabs;
const { Option } = Select;

class application extends Component {

  state = {
    formModal:false,
    searchData: {
      roleName: ''
    },
    tableData: [{
      id:'1',
      number:'NO.1001',
      type:'BHT1（财务）',
      applytype:'报销申请',
      detail:'3.10号出差至北京费用报销',
      date:'2021-03-10',
      createTime:'2021-03-10 15:35:36',
      submit:'2021-03-10 15:35:36',
      status:'0',
    }],
    tabBtn: '1',
    page: 1,
    pageSize: 10,
    total: 0,
    columns: [
      {
        title: '编号',
        dataIndex: 'number',
        width: 40,
      },
      {
        title: '第三方企业',
        dataIndex: 'type',
        width: 100,
      },
      {
        title: '申请类型',
        dataIndex: 'applytype',
        width: 100,
      },
      {
        title: '详细说明',
        dataIndex: 'detail',
        width: 250,
      },
      {
        title: '最迟完成日期',
        dataIndex: 'date',
        width: 120,
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
        width: 120,
      },
      {
        title: '提交时间',
        dataIndex: 'submit',
        width: 120,
      },
      {
        title: '状态',
        key: 'status',
        render: (text, record) => (
          text==1?
          <Button type="primary" >待提交</Button>:
          <Button type="primary" className="btn-success color-btn">待接单</Button>
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
            <Col span={24}>
              <Tabs onTabClick={(key) => this.handleTabClick(key)} activeKey={tabBtn}>
                <TabPane tab="申请中()" key="1">
                  {/* <ApplyList childEvevnt={this.childEvevnt} /> */}
                </TabPane>
                <TabPane tab="处理中()" key="2">
                  {/* <TravelList childEvevnt={this.childEvevnt2} /> */}
                </TabPane>
                <TabPane tab="待确认()" key="3">
                  {/* <LoanList childEvevnt={this.childEvevnt3} onSubmit={this.openSend} /> */}
                </TabPane>
                <TabPane tab="已完成()" key="4">
                  {/* <LoanList childEvevnt={this.childEvevnt3} onSubmit={this.openSend} /> */}
                </TabPane>
              </Tabs>
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <div className="contentBox">
                <div className="contentTitle">
                  <Row>
                    <Col span='16'>
                      <Form layout="inline" onSubmit={this.handleSubmit}>
                        <Form.Item label="状态选择">
                          <Select style={{ width: 150 }} placeholder="状态选择">
                            <Option value="0">待提交</Option>
                            <Option value="1">待接单</Option>
                          </Select>
                        </Form.Item>
                        <Form.Item label="编号搜索">
                          <Input
                            value={this.state.searchData.roleName}
                            onChange={this.searchChange}
                            placeholder="编号搜索"
                          />
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
          <ApplySet onCancel={this.handleCancel}/>
        </Modal>
      </div>
    );
  }
}
export default application;
