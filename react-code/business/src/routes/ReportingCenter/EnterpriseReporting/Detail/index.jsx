import React, { Component } from 'react';
import { Row, Col, Tabs, Button, Modal, Form, Input, TreeSelect, Table, DatePicker, Select, Tooltip } from 'antd';
import { QuestionCircleFilled, LeftOutlined } from '@ant-design/icons';
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import '../../style.css'
// import ApplySet from './applySet'
import UserSelect from '../../../Public/userSelect'


const { TabPane } = Tabs;
const { Option } = Select;

@inject('appStore') @observer
class application extends Component {

  state = {
    data: toJS(this.props.appStore.curEnt),
    formModal: false,
    value: [],
    namelist: [],
    userlist: [],
    searchData: {
      roleName: ''
    },
    tableData: [{
      id: '1',
      name: '报销名称报销名称报销名称报销名称',
      people: 'Jim0 Green',
      createTime: '2021-03-10 15:35:36',
      submit: '2021-03-10 15:35:36',
      count: '55',
      money: '156.00',
      date: '2021-03-10',
      status: '0',
    }],
    tabBtn: '1',
    page: 1,
    pageSize: 10,
    total: 0,
    columns: [
      {
        title: '报销名称',
        dataIndex: 'name',
        width: 500,
      },
      {
        title: '报销人',
        dataIndex: 'people',
        width: 100,
      },
      {
        title: '创建日期',
        dataIndex: 'createTime',
        width: 120,
      },
      {
        title: '完成日期',
        dataIndex: 'submit',
        width: 120,
      },
      {
        title: '状态',
        dataIndex: 'status',
        render: (text, record) => (
          text == 0 ?
            <Button type="primary" className="btn-purple color-btn">已报销</Button> :
            text == 1 ?
              <Button type="primary" className="btn-success color-btn">审批中</Button> :
              text == 2 ?
                <Button type="primary" className="btn-danger color-btn">已拒绝</Button> :
                <Button type="primary" >打开</Button>
        ),
        width: 100,
      },
      {
        title: '金额',
        dataIndex: 'money',
        render: (text, record) => (
          <span>$ {text}</span>
        ),
        width: 100,
      },
      {
        title: '操作',
        key: 'action',
        render: (text, record) => (
          <Button type="link" style={{ padding: '0' }}>详情</Button>
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
      userModal: false,
    })
  }
  userAdd = () => {
    this.setState({
      userModal: true
    })
  }
  userDel = (value) => {
    let arr = [];
    let userlist = this.state.userlist;
    userlist = userlist.filter(item => item.firstName + ' ' + item.lastName !== value)
    arr = userlist.map(item => item.firstName + ' ' + item.lastName)
    this.setState({
      userlist,
      namelist: arr,
    })
  }
  userSubmit = (e) => {
    console.log(e);
    let arr = [];
    let userlist = this.dedupe([...this.state.userlist, ...e]);
    arr = userlist.map(item => item.firstName + ' ' + item.lastName)
    this.setState({
      userlist,
      namelist: arr,
      userModal: false,
      valiStatus: '',
      valiText: ' ',
    })
  }
  	// 去重
	dedupe = (arr) => {
		return arr.reduce((pre, cur) => {
			let result = pre.some(item => {
				return item.userId == cur.userId
			})
			return !result ? pre.concat(cur) : pre
		}, [])
	}
  // 添加申请
  addItem = () => {
    this.setState({
      formModal: true
    })
  }
  goBack = () => {
    this.props.goReport(0)
  }
  // onSubmit = (data) => {
  // 	console.log(data);
  // }
  onTreeChange = value => {
    console.log('onChange ', value);
    this.setState({ value });
  };
  render() {
    const { formModal, tableData, data, columns, pageSize, total, userlist ,namelist} = this.state;
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
    const treeData = [
      {
        title: '全选',
        value: '0',
        key: '0',
      },
      {
        title: '打开',
        value: '1',
        key: '1',
      },
      {
        title: '审批中',
        value: '2',
        key: '2',
      },
      {
        title: '已拒绝',
        value: '3',
        key: '3',
      },
      {
        title: '已报销',
        value: '4',
        key: '4',
      },
    ]
    const tProps = {
      treeData,
      value: this.state.value,
      onChange: this.onTreeChange,
      treeCheckable: true,
      placeholder: 'Please select',
      style: {
        width: '150px',
      },
    };
    return (
      <div>
        <div className="headBox">
          <Row>
            <Col span='6'>
              <LeftOutlined className="backBtn" onClick={this.goBack} />报销明细报表
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <div className="contentBox">
                <div className="contentTitle">
                  <Row>
                    <Col span='24'>
                      <Form layout="inline" onSubmit={this.handleSubmit}>
                        <Form.Item label='时间区间' style={{ marginBottom: 0 }}>
                          <Form.Item name='startTime' style={{ display: 'inline-block', marginRight: '0' }}>
                            <DatePicker />
                          </Form.Item>
                          <Form.Item style={{ display: 'inline-block', margin: '0 10px' }}>
                            -
                          </Form.Item>
                          <Form.Item name='endTime' style={{ display: 'inline-block' }}>
                            <DatePicker />
                          </Form.Item>
                        </Form.Item>
                        <Form.Item label="报销人">
                          <Select
                            mode="multiple"
                            style={{ width: '400px' }}
                            placeholder="Please select"
                            onClick={this.userAdd}
                            open={false}
                            value={namelist}
                            onDeselect={this.userDel}
                          >
                          </Select>
                        </Form.Item>
                        <Form.Item label="状态选择">
                          <TreeSelect {...tProps} />
                        </Form.Item>
                        <Form.Item>
                          <Button type="primary" onClick={this.searchClick}>查询</Button>
                          &nbsp; &nbsp;
                          <Button onClick={this.searchReset}>重置</Button>
                        </Form.Item>
                      </Form>
                    </Col>
                    {/* <Col span='2' offset={6} style={{ textAlign: "right", height: '32px' }}>
                      <Button type="primary" onClick={this.addItem} style={{ verticalAlign: '10px' }}>添加</Button>
                    </Col> */}
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
          {/* <ApplySet onCancel={this.handleCancel} /> */}
        </Modal>
        <Modal
          width="30%"
          title="选择人员"
          style={{ top: '150px' }}
          visible={this.state.userModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
        >
          <UserSelect addTrip={this.addTrip} onCancel={this.handleCancel} entData={data} userSubmit={this.userSubmit} userlist={userlist} />
        </Modal>
      </div>
    );
  }
}
export default application;
