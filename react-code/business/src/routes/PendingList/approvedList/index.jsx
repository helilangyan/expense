import React, {Component} from 'react';
import {Row, Col, Tabs, Tree, Button, Modal, Form, Input, Table, Divider} from 'antd';


class approvedList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 1,
            pageSize: 10,
            total: 0,
            searchData: {
                userName: ''
            },
            columns: [
                {
                    title: '申请类型',
                    dataIndex: 'key',
                    render: (text, record) => {
                        if (record.key) {
                            if (record.key.split('_')[2] == '1') {
                                return (
                                    <span>报销申请</span>
                                )
                            } else if (record.key.split('_')[2] == '2') {
                                return (
                                    <span>出差申请</span>
                                )
                            } else if (record.key.split('_')[2] == '3') {
                                return (
                                    <span>借款申请</span>
                                )
                            } else {
                                return (
                                    <span>其他申请</span>
                                )
                            }
                        } else {
                            return (
                                <span>未知申请</span>
                            )
                        }
                    },
                    width: 120,
                },
                {
                    title: '申请名称',
                    dataIndex: 'billName',
                }, {
                    title: '申请人',
                    dataIndex: 'initorName',
                    width: 120,
                }, {
                    title: '填报人',
                    dataIndex: 'initorName',
                    width: 120,
                }, {
                    title: '填报时间',
                    dataIndex: 'startTime',
                    width: 220,
                }, {
                    title: '当前处理人',
                    dataIndex: 'nextUsername',
                    width: 120,
                },
                {
                    title: '操作',
                    key: 'action',
                    render: (text, record) => (
                        <span>
                             <a onClick={() => {
                                 this.editClick(record);
                             }}>查看</a>
                              <Divider type="vertical"/>
                              <a onClick={() => {
                                  this.seeBpmn(record);
                              }}>流程图</a>
                        </span>
                    ),
                    width: 200
                },
            ],
            tabData: [],
        }
    }

    componentDidMount() {
        this.getData(1);
    }

    getData(current) {
        this.post(`/bpm/use/my-tasks-done`,
            {
                limit: this.state.pageSize,
                page: current,
                startUserName:this.state.searchData.userName,
                status:this.state.searchData.status,
            }
        ).then(res => {
            if (res.code == 1) {
                this.setState({
                    tabData: res.data.data,
                    total: res.data.count
                });
            }

        })
    }

    seeBpmn = (data) => {
        this.props.seeBpmn(data);
    }

    editClick=(data)=>{
        this.props.editClick(data,'see');
    }
    searchChange=(e)=>{
        console.log(e.target.value);
        this.setState({
            searchData:{
                userName:e.target.value
            }
        })
        console.log(this.state.searchData);
    }
    searchClick=()=>{
        this.getData(this.state.page);
    }
    render() {
        const paginationProps = {
            showSizeChanger: true,//设置每页显示数据条数
            showQuickJumper: false,
            showTotal: () => `共${this.state.total}条`,
            pageSize: this.state.pageSize,
            total: this.state.total,  //数据的总的条数
            onChange: (current) => this.getData(current), //点击当前页码
            onShowSizeChange: (current, pageSize) => {//设置每页显示数据条数，current表示当前页码，pageSize表示每页展示数据条数
                this.onShowSizeChange(current, pageSize)
            }
        }
        return (
            <div>
                <div className="contentBox">
                    <div className="contentTitle">
                        <Row>
                            <Col span='10'>
                                <Form layout="inline" onSubmit={this.handleSubmit}>
                                    <Form.Item label="用户名">
                                        <Input
                                            value={this.state.searchData.userName}
                                            onChange={this.searchChange}
                                            placeholder="请输入用户名"
                                        />
                                    </Form.Item>
                                    <Form.Item>
                                        <Button type="primary" onClick={this.searchClick}>查询</Button>
                                        &nbsp; &nbsp;
                                        <Button onClick={this.searchReset}>重置</Button>
                                    </Form.Item>
                                </Form>
                            </Col>
                            {/*<Col span='2' offset={12} style={{textAlign: "right", height: '32px'}}>*/}
                                {/*<Button type="primary" onClick={this.roleInput}*/}
                                        {/*style={{verticalAlign: '10px'}}>添加</Button>*/}
                            {/*</Col>*/}
                        </Row>
                    </div>
                    <div className="content">
                        <Table columns={this.state.columns} dataSource={this.state.tabData}
                               rowKey={record => record.instanceId}
                               pagination={paginationProps}/>
                    </div>
                </div>
            </div>
        );
    }
}

export default approvedList;
