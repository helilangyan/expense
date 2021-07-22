import React, { Component } from 'react'
import { Table, Tag, Space,message,Row,Col,Button } from 'antd';
import {utc2bj } from '../../../../utils/UTCtoBJ'

const { Column, } = Table;

export default class ApplyList extends Component {
  state={
    data:JSON.parse(localStorage.getItem('entInfo')),
    total:0,
    pageSize:10,
    pageNum:1,
    tableData:[],
    loading: false,
    columns: [
      {
          title: '加入企业',
          dataIndex: 'enterpriseName',
          width: 120,
      },
      {
          title: '企业类型',
          dataIndex: 'type',
          width: 120,
          render: (text, record) => {
            if(record.status==1){
              return (
                <span>普通企业</span>
              )
            }else if(record.status==2){
              return (
                <span>拒绝</span>
              )
            } else if(record.status==3){
              return (
                <span>第三方企业</span>
              )
            }
          },
      },
      {
          title: '申请时间',
          dataIndex: 'applyTime',
          width: 120,
          render: (text, record) => {
            let date = utc2bj(record.applyTime);
            return (
              <span>{date}</span>
            )
          },
      },
      {
          title: '处理结果',
          dataIndex: 'status',
          width: 120,
          render: (text, record) => {
            if(record.status==1){
              return (
                <span>通过</span>
              )
            }else{
              return (
                <span>拒绝</span>
              )
            }
          },
      },
      {
          title: '处理信息',
          dataIndex: 'message',
          width: 120,
      },
    ],
  }
  componentDidMount(){
    this.getData()
  }
  //获取数据
  getData = () => {
    this.post(`/enterprise-user-apply/user-list`).then(res => {
      if (res.code === 1) {
        this.setState({
          tableData:res.data,
        })
      } else {
          message.error(res.message)
      }
    });
  }
  render() {
    const {tableData,columns} = this.state;
    return (
      <div>
        <Table columns={columns} dataSource={tableData} rowKey={record => record.applyId} style={{minHeight:'600px'}}></Table>
        <Row>
          <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
            <Button onClick={this.props.handleCancel}>关闭</Button>
          </Col>
        </Row>
      </div>
    )
  }
}
