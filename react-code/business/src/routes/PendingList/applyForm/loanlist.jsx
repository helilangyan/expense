import React, { Component } from 'react'
import { Table, Tag, Space, Row, Col, Form, Button, Input, Select, message, DatePicker } from 'antd';
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import { utc2bj } from '../../../utils/UTCtoBJ'


const { Column, } = Table;

@inject('appStore') @observer
export default class ApplyList extends Component {
  state = {
    entId: toJS(this.props.appStore.curEnt).enterpriseId,
    userId: toJS(this.props.appStore.curEnt).userId,
    loading: false,
    total: 0,
    pageSize: 10,
    pageNum: 1,
    searchData: {},
    tableData: [],
    selectedRowKeys: [],
    selectedRows: [],
    columns: [
      {
        title: '申请名称',
        dataIndex: 'applyName',
        name: '',
        width: 150,
      },
      {
        title: '申请日期',
        dataIndex: 'createTime',
        width: 120,
      },
      {
        title: '状态',
        dataIndex: 'roleName',
        width: 100,
        render: (text, record) => (
          <Space size="middle">
            <div className="rnd-pl">已批准</div>
          </Space>
        ),
      },
      {
        title: '金额',
        dataIndex: 'money',
        width: 100,
      },
    ],
  }
  searchForm = React.createRef();

  componentDidMount() {
    this.getData(1);
  }
  handleChange = () => {

  }
  // 表格选中
  onSelectChange = (selectedRowKeys,selectedRows) => {
    this.setState({ selectedRowKeys,selectedRows});
  }
  onSubmit = (e) => {
    const {selectedRowKeys,selectedRows,} = this.state;
    this.props.addList(selectedRowKeys,selectedRows)
    this.props.onCancel();
  }
  onCancel=()=>{

  }
  //获取数据
  getData = (current) => {
    const { pageSize, entId, userId, searchData} = this.state;
    let obj = {
      limit: pageSize,
      page: current,
      enterpriseId: entId,
      userId: userId,
      expenseName:searchData.expenseName,
    }
    this.post(`/detail/apply-borrow/list`, obj).then(res => {
      if (res.code === 1) {
        let list = res.data.data.map(item => {
          if (item.createTime) {
            item.createTime = utc2bj(item.createTime).slice(0, -9)
            return item
          }
        })
        this.setState({
          tableData: list,
          total: res.data.count
        })
      } else {
        message.error(res.message)
      }
    });
  }
  render() {
    const { tableData, columns, total, pageSize, selectedRowKeys } = this.state;
    const paginationProps = {
      showQuickJumper: false,
      showTotal: () => `共${total}条`,
      pageSize: pageSize,
      total: total,  //数据的总的条数
      onChange: (current) => this.getData(current), //点击当前页码
    }
    const rowSelection = {
      selectedRowKeys,
      onChange: this.onSelectChange,
    };
    return (
      <div>
        <div style={styles.conTit}>
          <Row>
            <Col span={24}>
              <Form layout="inline" onSubmit={this.handleSubmit} ref={this.searchForm}>
                <Form.Item style={{ width: '200px' }} >
                  <Input placeholder="请输入申请名称" />
                </Form.Item>
                <Form.Item >
                  <DatePicker placeholder="年-月-日" />
                </Form.Item>
                <Form.Item >
                  <DatePicker placeholder="年-月-日" />
                </Form.Item>
                <Form.Item>
                  <Button type="primary" onClick={this.handleSubmit}>搜索</Button>
                </Form.Item>
              </Form>
            </Col>
          </Row>
        </div>
        <div>
          <Table columns={columns} dataSource={tableData} rowKey={record => record.id}
            rowSelection={rowSelection} size="small"
            pagination={paginationProps} style={styles.table}></Table>
        </div>
        <Row>
          <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
            <Space>
              <Button type="primary" onClick={this.onSubmit}>确定</Button>
              <Button onClick={this.props.onCancel}>关闭</Button>
            </Space>
          </Col>
        </Row>
      </div>
    )
  }
}

const styles = {
  conTit: {
    paddingBottom: '20px',
    backgroundColor: '#fff',
  },
  table: {
    minHeight: '400px'
  }
}
