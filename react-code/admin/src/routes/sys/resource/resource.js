import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import {Input, Form, Button, Table, Divider, Avatar, Modal, Row, Col, Switch,Tree,Spin  } from 'antd';
import './style.css'
import ResourceInput from './resourceInput'
import {message} from "antd/lib/index";


const { TreeNode } = Tree;

class resource extends Component {
    componentDidMount() {
        this.getTreeData();
        this.getData(1);
    }

    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            treeData:[],
            tabData: [],
            columns: [
                {
                    title: '资源名称',
                    dataIndex: 'resourceName',
                },
                {
                    title: '备注',
                    dataIndex: 'remark',
                },
                {
                    title: '资源URL地址',
                    dataIndex: 'resourceUrl',
                },
                {
                    title: '操作',
                    key: 'action',
                    render: (text, record) => (
                        <span>
                            <a onClick={() => {
                                this.editClick(record);
                            }}>编辑</a>
                            <Divider type="vertical"/>
                            <a onClick={() => {
                                this.deleteClick(record);
                            }}>删除</a>
                      </span>
                    ),
                    width: 200
                },
            ],
            page:1,
            pageSize:10,
            total:10,
            formItemLayout: {
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 10},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 14},
                },
            },
            selectedRowKeys: [],
            loading: false,
            addForm: {
                resourceName: '',
                resourceNameEnglish: '',
                resourceUrl: '',
                isMenu: '0' ,
                remark: '',
                icon: '',
                parentId: '',
            }
        }
        this.addModal = this.addModal.bind(this);
    }
    getTreeData(){
        this.post(`/resource/resource-tree`).then(res => {
            if (res.code==1){
                this.setState({
                    treeData: res.data
                });
            }
        })
    }
    getData(current){
        this.post(`/resource/list`, {limit: this.state.pageSize, page: current}).then(res => {
            console.log(this.state.tabData);
            this.setState({
                tabData: res.data.data,
                total:res.data.count
            });

        })
    }
    onSelect = (selectedKeys, info) => {
        console.log('selected', selectedKeys, info);
    };
    renderTreeNodes = data =>
        data.map(item => {
            if (item.children) {
                return (
                    <TreeNode title={item.resourceName } key={item.id} dataRef={item}>
                        {this.renderTreeNodes(item.children)}
                    </TreeNode>
                );
            }else {
                return <TreeNode key={item.id} title={item.resourceName } />;
            }

        });

    start = () => {
        this.setState({ loading: true });
        // ajax request after empty completing
        setTimeout(() => {
            this.setState({
                selectedRowKeys: [],
                loading: false,
            });
        }, 1000);
    };
    onSelectChange = selectedRowKeys => {
        this.setState({ selectedRowKeys });
    };
    //提交新增
    submitAdd = (data) => {
        console.log(data);
        const that = this;
        this.post(`/resource/insert`, data).then(res => {
            if (res.code == 1) {
                that.handleCancel();
                that.getTreeData();
                that.getData(this.state.page);
                message.success('操作成功')
            } else {
                message.error(res.msg)
            }

        })
    }
    editClick(data) {
        const that = this;
        that.get(`/resource/` + data.id).then(res => {
            if (res.code == 1) {
                this.setState({
                    addForm: res.data,
                    visible: true,
                });
            }
        })
    }
    deleteClick = (data) => {
        const that = this;
        confirm({
            title: '确认删除?',
            content: '',
            okText: '确定',
            okType: 'danger',
            cancelText: '取消',
            onOk() {
                that.del(`/resource/del/` + data.id).then(res => {
                    if (res.code == 1) {
                        message.success('删除成功')
                        that.getData(that.state.page)
                    } else {
                        message.error(res.msg)
                    }
                })
            },
            onCancel() {
                console.log('Cancel');
            },
        });
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
                console.log(pageSize);
                this.onShowSizeChange(current, pageSize)
            }
        }
        const { loading, selectedRowKeys } = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        const hasSelected = selectedRowKeys.length > 0;
        return (
            <div className=''>
                <Row >
                    <Col span={4} className='treeCol gutter-row'>
                        <div className="treeCard">
                            <Spin spinning={!this.state.treeData.length} >
                                <div className="spinCard">
                                    <Tree
                                        onSelect={this.onSelect}
                                    >
                                        {
                                            this.renderTreeNodes(this.state.treeData)
                                        }

                                    </Tree>
                                </div>

                            </Spin>

                        </div>
                    </Col>
                    <Col span={20} className='tableCard gutter-row'>
                        <div className="tableContent">
                            <div className="btnBox">
                                <Button type="primary" onClick={this.addModal} >
                                    新增
                                </Button>
                                <Button type="danger" onClick={this.start} disabled={!hasSelected} loading={loading}>
                                    删除
                                </Button>
                                <Button type="" onClick={()=>{
                                    this.getData(1);
                                }}>
                                    刷新
                                </Button>
                            </div>

                            <Table
                                rowSelection={rowSelection}
                                columns={this.state.columns}
                                dataSource={this.state.tabData}
                                rowKey={record => record.id}
                                pagination={paginationProps}
                            />
                        </div>
                    </Col>
                </Row>
                <Modal
                    width="30%"
                    title="资源管理"
                    visible={this.state.visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <ResourceInput formData={this.state.addForm} submitAdd={this.submitAdd}
                               onCancel={this.handleCancel}></ResourceInput>
                </Modal>
            </div>
        );
    }


    addModal() {
        this.setState({
            visible: true,
        });
    }

    handleOk = e => {
        console.log(e);
        this.setState({
            visible: false,
        });
    };

    handleCancel = e => {
        this.setState({
            visible: false,
        });
    };
}


export default resource;