import React, { Component } from 'react';
import { Row, Col, Tabs, Button, Modal, Form, Input, Space, Table, Divider,Select ,Tooltip} from 'antd';
import { QuestionCircleFilled,  } from '@ant-design/icons';
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
      type:'BHT1',
      date:'2021-03-10',
      createTime:'2021-03-10 15:35:36',
      submit:'2021-03-10 15:35:36',
      order:'Lily1(15356987563)',
      orderTime:'2021-03-10 15:35:36',
      orderSubmit:'2021-03-10 15:35:36',
      completeTime:'2021-03-10 15:35:36',
      totalTime:'7天12小时36分52秒',
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
        title: '申请提交时间',
        dataIndex: 'submit',
        width: 120,
      },
      {
        title: '接单人',
        dataIndex: 'submit',
        width: 120,
      },
      {
        title: '接单时间',
        dataIndex: 'orderTime',
        width: 120,
      },
      {
        title: '接单提交时间',
        dataIndex: 'orderSubmit',
        width: 120,
      },
      {
        title: '完成时间',
        dataIndex: 'completeTime',
        width: 120,
      },
      {
        title: 
        <div>总计花费时间 
          <Tooltip placement='bottom' title='花费时间=完成时间-申请提交时间'>
            <QuestionCircleFilled style={{color:'#666',paddingLeft:'10px'}}/>
          </Tooltip>
        </div>,
        dataIndex: 'totalTime',
        render: (text, record) => (
          <span>{text}</span>
        ),
        width: 120
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
            申请统计
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <div className="contentBox">
                <div className="contentTitle">
                  <Row>
                    <Col span='3' className="titleStats">
                      <div className="tit">创建申请</div>
                      <div className="count">100单</div>
                    </Col>
                    <Col span='1' >
                      <Divider type="vertical" style={{height:'100%'}}></Divider>
                    </Col>
                    <Col span='3' className="titleStats">
                      <div className="tit">提交申请</div>
                      <div className="count">100单</div>
                    </Col>
                    <Col span='1' >
                      <Divider type="vertical" style={{height:'100%'}}></Divider>
                    </Col>
                    <Col span='3' className="titleStats">
                      <div className="tit">被接单</div>
                      <div className="count">100单</div>
                    </Col>
                    <Col span='1' >
                      <Divider type="vertical" style={{height:'100%'}}></Divider>
                    </Col>
                    <Col span='3' className="titleStats">
                      <div className="tit">接单提交</div>
                      <div className="count">100单</div>
                    </Col>
                    <Col span='1' >
                      <Divider type="vertical" style={{height:'100%'}}></Divider>
                    </Col>
                    <Col span='3' className="titleStats">
                      <div className="tit">完成</div>
                      <div className="count">100单</div>
                    </Col>
                    <Col span='1' >
                      <Divider type="vertical" style={{height:'100%'}}></Divider>
                    </Col>
                    <Col span='4' className="titleStats">
                      <div className="tit">平均花费时间</div>
                      <div className="count">6天5小时23分22秒</div>
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
