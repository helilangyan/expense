import React, { Component } from 'react'
import { Table, Tag, Space,Row,Col,Form,Button,Input,Select,message} from 'antd';
import {toJS} from "mobx"
import { inject, observer} from 'mobx-react'
import './index.css'

const { Column, } = Table;
const { Option } = Select;

@inject('appStore') @observer
export default class ApplyList extends Component {
  state={
    entId: toJS(this.props.appStore.curEnt).enterpriseId,
    searchData:{},
    tableData:[],
    loading: false,
    total:0,
    pageSize:10,
    pageNum:1,
    columns: [
      {
          title: '委托人',
          dataIndex: 'roleName',
          name:'',
          width: 120,
      },
      {
          title: '委托时间区间',
          dataIndex: 'roleName',
          width: 120,
      },
      {
          title: '状态',
          dataIndex: 'roleName',
          width: 120,
      },
      {
          title: '操作',
          key: 'action',
          render: (text, record) => (
            <span>
                <a >终止委托</a>
            </span>
          ),
          width: 100
      },
  ],
  }
  searchForm = React.createRef();

  componentDidMount(){
    this.getData(1);
  }
  handleChange = ()=>{
    
  }
  handleSubmit = (e)=>{
    this.setState({
      searchData:e.target.value
    })
  }
  searchReset = ()=>{
    this.searchForm.current.resetFields();
  }
  //获取数据
  getData = (current) => {
    const {pageSize,entId} = this.state;
    // let obj={
    //   limit:pageSize,
    //   page:current,
    //   enterpriseId:entId,
    // }
    this.post(`/enterprise-user-apply/user-list`).then(res => {
      if (res.code === 1) {
        this.setState({
          tableData:res.data.data,
        })
      } else {
          message.error(res.message)
      }
    });
  }
  render() {
    const {tableData,columns,total,pageSize} = this.state;
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
        <div style={styles.conTit}>
          <Row>
            <Col span='10'>
                <Form layout="inline" onSubmit={this.handleSubmit} ref={this.searchForm}>
                    <Form.Item label="用户名" style={{ width: '200px' }} >
                        <Select onChange={this.handleChange}>
                          <Option value="jack">全部</Option>
                          <Option value="lucy">正常</Option>
                          <Option value="Yiminghe">终止</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" onClick={this.handleSubmit}>查询</Button>
                        &nbsp; &nbsp;
                        <Button onClick={this.searchReset}>重置</Button>
                    </Form.Item>
                </Form>
            </Col>
          </Row>
        </div>
      <div>
        <Table columns={columns} dataSource={tableData} rowKey={record => record.id}  pagination={paginationProps}></Table>
      </div>
      </div>
    )
  }
}

const styles = {
  conTit:{
    padding: '4px 10px 20px',
    backgroundCcolor: '#fff',
    marginBottom: '20px',
    borderBottom: '1px solid #F0f0f0',
  }
}