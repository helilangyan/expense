import React, {Component} from 'react'
import {Table, Space, Row, Col, Form, Button, Input, message, DatePicker} from 'antd';
import {toJS} from "mobx"
import {inject, observer} from 'mobx-react'
import {utc2bj} from '../../../../utils/UTCtoBJ'


const {Column,} = Table;

@inject('appStore') @observer
export default class ApplyList extends Component {
    state = {
        entId: toJS(this.props.appStore.curEnt).enterpriseId,
        userId: toJS(this.props.appStore.curEnt).userId,
        total: 0,
        pageSize: 10,
        pageNum: 1,
        searchData: {},
        tableData: [],
        loading: false,
        selectedRowKeys: [],
        selectedRows: [],
        columns: [
            {
                title: '费用名称',
                dataIndex: 'expenseName',
                name: '',
                width: 150,
            },
            {
                title: '发生日期',
                dataIndex: 'giveDate',
                width: 120,
            },
            {
                title: '金额',
                dataIndex: 'money',
                width: 100,
            },
            {
                title: '可报销',
                dataIndex: 'money',
                width: 100,
            },
        ],
    }
    searchForm = React.createRef();

    componentDidMount() {
        this.getData(1);
    }

    handleChange = (e, v) => {
        console.log(v);
        this.setState({searchData: v,})
    }
    // 表格选中
    onSelectChange = (selectedRowKeys, selectedRows) => {
        this.setState({selectedRowKeys, selectedRows});
    }
    handleSearch = (e) => {
        let searchData =  this.state.searchData
        searchData.startTime ? searchData.startTime = searchData.startTime.format('YYYY-MM-DD')+' 00:00:00' : ''
        searchData.endTime ? searchData.endTime = searchData.endTime.format('YYYY-MM-DD')+' 00:00:00' : ''
        searchData.expenseName = searchData.expenseName
        this.setState(searchData,()=>{
            this.getData(1)
        })
    }
    onSubmit = (e) => {
        const {selectedRowKeys, selectedRows,} = this.state;
        if (selectedRowKeys.length > 0) {
            this.props.addList(selectedRowKeys, selectedRows)
            this.props.onCancel();
        } else {
            message.error("请选择至少一项")
        }
    }
    //获取数据
    getData = (current) => {
        const {pageSize, entId, userId, searchData} = this.state;
        let obj = {
            limit: pageSize,
            page: current,
            enterpriseId: entId,
            userId: userId,
            expenseName: searchData.expenseName,
            startTime:searchData.startTime,
            endTime:searchData.endTime,
        }
        this.post(`/detail/business-expense/list`, obj).then(res => {
            if (res.code === 1) {
                let list = res.data.data.map(item => {
                    if (item.giveDate) {
                        item.giveDate = utc2bj(item.giveDate).slice(0, -9)
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
        const {tableData, columns, total, pageSize, selectedRowKeys} = this.state;
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
                            <Form layout="inline" ref={this.searchForm} onValuesChange={this.handleChange}>
                                <Form.Item name="expenseName" style={{width: '200px'}}>
                                    <Input placeholder="请输入费用名称" allowClear/>
                                </Form.Item>
                                <Form.Item name="startTime">
                                    <DatePicker placeholder="年-月-日"/>
                                </Form.Item>
                                <Form.Item name="endTime">
                                    <DatePicker placeholder="年-月-日"/>
                                </Form.Item>
                                <Form.Item>
                                    <Button type="primary" onClick={this.handleSearch}>搜索</Button>
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
                    <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
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
