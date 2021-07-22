import React, { Component } from 'react'
import { Table, Tag, Space, Row, Col, Form, Input, Select, message, DatePicker, Button, } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'

const { Column, } = Table;
const { Search } = Input;

@inject('appStore') @observer
export default class ApplyList extends Component {
  state = {
    entId: toJS(this.props.appStore.curEnt).enterpriseId,
    userId: toJS(this.props.appStore.curEnt).userId,
    loading: true,
    total: 0,
    pageSize: 10,
    pageNum: 1,
    searchData: {},
    tableData: [],
    selectedRowKeys: [],
    selectedRows: [],
    searchName: '',
    columns: [
      {
        title: '项目编号',
        dataIndex: 'projectCode',
        width: 50,
      },
      {
        title: '项目名称',
        dataIndex: 'projectName',
        name: '',
        width: 200,
      },
    ],
  }
  searchForm = React.createRef();

  componentDidMount() {
    this.getData(1);
  }
  handleChange = (e, v) => {
    console.log(e);
    this.setState({searchName:e.searchName})
  }
  // 表格选中
  onSelectChange = (selectedRowKeys, selectedRows) => {
    this.setState({ selectedRowKeys, selectedRows });
  }
  onSubmit = (e) => {
    const { selectedRowKeys, selectedRows, } = this.state;
    this.props.projSubmit(selectedRows)
    this.props.onCancel();
  }
  onCancel = () => {

  }
  onSearch = (e) => {
    this.getData()
  }
  //获取数据
  getData = (current) => {
    this.post("/enterprise/project/findByEnterpriseId", {
      limit: 100,
      page: 1,
      enterpriseId: this.state.entId,
      projectName: this.state.searchName
    }).then(res => {
      if (res.code === 1) {
        this.setState({
          tableData: res.data.data,
          total: res.data.count,
          loading: false
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
      type: 'radio',
      onChange: this.onSelectChange,
    };
    const suffix = (
      <a onClick={this.onSearch}><SearchOutlined style={{ fontSize: 16, color: '#666', }} /></a>
    );
    return (
      <div>
        <div style={styles.conTit}>
          <Row>
            <Col span={24}>
              <Form layout="inline" ref={this.searchForm} onValuesChange={this.handleChange}>
                <Form.Item name="searchName" style={{ width: '100%' }} >
                  <Search placeholder="搜索项目" className="greybg" onSearch={this.onSearch}/>
                </Form.Item>
              </Form>
            </Col>
          </Row>
        </div>
        <div>
          <Table columns={columns} dataSource={tableData} rowKey={record => record.id} showHeader={false}
            rowSelection={rowSelection} size="small" bordered={false} loading={this.state.loading} pagination={false}
            className="noborder-table"></Table>
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
}