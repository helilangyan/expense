import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import {Button, Table, Divider, Modal, message} from 'antd';
import './style.css'
import AuthorityInput from './authorityInput'
import ResourceTree from './resourceTree'
import {ExclamationCircleOutlined} from '@ant-design/icons';


const {confirm} = Modal;

class authorityList extends Component {
    componentDidMount() {
        this.getData(1);

    }

    constructor(props) {
        super(props);
        const that = this;
        this.state = {
            visible: false,
            treeModal: false,
            tabData: [],
            columns: [
                {
                    title: '编号',
                    dataIndex: 'index',
                    key: 'index',
                    width: 300,
                    render: (text, record, index) => `${index + 1}`,
                },
                {
                    title: '权限名称',
                    dataIndex: 'authorityName',
                    width: 350,
                },
                {
                    title: '备注',
                    dataIndex: 'remark',
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
                                  this.setResource(record);
                              }}>资源配置</a>
                             <Divider type="vertical"/>
                            <a onClick={() => {
                                this.deleteClick(record);
                            }}>删除</a>
                      </span>
                    ),
                    width: 200
                },
            ],
            page: 1,
            pageSize: 10,
            total: 10,
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
            addForm: {
                authorityName: '',
                remark: '',
            },
            resourceData:[],
            setId:''
        }
        this.addModal = this.addModal.bind(this);
    }

    getData(current) {
        this.post(`/authority/list`, {limit: this.state.pageSize, page: current}).then(res => {
            this.setState({
                tabData: res.data.data,
                total: res.data.count
            });

        })
    }

    submitAdd = (data) => {
        const that = this;
        this.post(`/authority/insert`, data).then(res => {
            if (res.code == 1) {
                that.handleCancel();
                that.getData(this.state.page);
                message.success('操作成功')
            } else {
                message.error(res.msg)
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
                that.del(`/authority/del/` + data.id).then(res => {
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
    setResource=(data)=>{
        const that = this;
        this.post(`/authority-resource/findByAuthorityId`, {authorityId:data.id}).then(res => {
            if (res.code == 1) {
                let checkData=[];
                if (res.data) {
                    res.data.map((item)=>{
                        checkData.push(item.resourceId)
                    })
                }else {
                    checkData=[];
                }
                this.setState({
                    resourceData: checkData,
                    treeModal: true,
                    setId:data.id
                });
            } else {
                message.error(res.msg)
            }

        })
    }

    editClick(data) {
        const that = this;
        that.get(`/authority/` + data.id).then(res => {
            if (res.code == 1) {
                this.setState({
                    addForm: res.data,
                    visible: true,
                });
            }
        })
    }
    submitResource=(data)=>{
        let obj={
            authorityId:this.state.setId,
            resourceIds:data.toString()
        }
        this.post(`/authority-resource/insert`,obj).then(res => {
            if (res.code == 1) {
                this.setState({
                    treeModal: false,
                });
                message.success('操作成功')
            }
        })
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
            <div className='boxCard'>
                <div className="tableContent">
                    <Button type="danger" style={{marginBottom: '10px'}} onClick={this.addModal}>新增</Button>

                    <Table
                        columns={this.state.columns}
                        dataSource={this.state.tabData}
                        rowKey={record => record.id}
                        pagination={paginationProps}
                    />
                </div>
                <Modal
                    width="30%"
                    title="权限管理"
                    visible={this.state.visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <AuthorityInput formData={this.state.addForm} submitAdd={this.submitAdd}
                               onCancel={this.handleCancel}></AuthorityInput>
                </Modal>
                <Modal
                    width="30%"
                    title="资源设置"
                    visible={this.state.treeModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <ResourceTree resourceData={this.state.resourceData} submitAdd={this.submitResource}
                               onCancel={this.handleCancel}></ResourceTree>
                </Modal>
            </div>
        );
    }


    addModal() {
        this.setState({
            addForm: {
                roleName: '',
                remark: '',
            },
            visible: true,
        });
    }

    handleCancel = () => {
        this.setState({
            visible: false,
            treeModal: false,
        });
    };
}


export default authorityList;