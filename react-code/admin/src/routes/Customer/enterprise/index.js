import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import {Input, Form, Button, Table, Divider, Avatar, Modal, Row, Col, Switch} from 'antd';
import './style.css'
import {message} from "antd/lib/index";

const { confirm } = Modal;
class enterprise extends Component {
    componentDidMount() {
        this.getData(1);

    }

    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            tabData: [],
            columns: [
                {
                    title: '企业LOGO',
                    className: 'column-money',
                    dataIndex: 'username',
                    width: 100,
                },
                {
                    title: '企业名称',
                    dataIndex: 'enterpriseName',
                    width: 150,
                },
                {
                    title: '企业地址',
                    dataIndex: 'address',
                    width: 150,
                }, {
                    title: '企业电话',
                    dataIndex: 'tel',
                    width: 150,
                },{
                    title: '联系人',
                    dataIndex: 'linkman',
                    width: 150,
                },
                {
                    title: '企业类型',
                    dataIndex: 'type',
                    render: type => (
                        <span>
                            {type=='1'?'普通企业':type=='2'?'代账企业':'其他企业'}
                        </span>
                    ),
                    width: 100,
                },
                {
                    title: '是否认证',
                    dataIndex: 'status',
                    render: status => (
                        <span>
                            {status=='1'?'已认证':'未认证'}
                        </span>
                    ),
                    width: 150,
                },{
                    title: 'Federal Tax ID',
                    dataIndex: 'runTime',
                    width: 150,
                },
                {
                    title: '操作',
                    key: 'action',
                    render: (text, record) => (
                        <span>
                            <a onClick={() => {
                                this.ApprovalClick(record);
                            }}>审批</a>
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
            }
        }
        this.addModal = this.addModal.bind(this);
    }
    getData(current){
        this.post(`/enterprise/list`, {limit: this.state.pageSize, page: current}).then(res => {
            console.log(this.state.tabData);
            this.setState({
                tabData: res.data.data,
                total:res.data.count
            });

        })
    }
    ApprovalClick(data){
        console.log(data);
        const that=this;
        confirm({
            title: '确认审批?',
            content: '',
            onOk() {
                let s = data.status=='0'?'1':'0'
                that.putUrl(`/enterprise/approve?id=`+data.id+'&&status='+s).then(res => {
                    if (res.code == 1) {
                        message.success('操作成功')
                        that.getData(that.state.page);
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
    deleteClick = (data) => {
        const that = this;
        confirm({
            title: '确认删除?',
            content: '',
            okText: '确定',
            okType: 'danger',
            cancelText: '取消',
            onOk() {
                that.del(`/enterprise/del/` + data.id).then(res => {
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
        return (
            <div className='boxCard'>
                <div className="tableContent">

                    <Table
                        columns={this.state.columns}
                        dataSource={this.state.tabData}
                        rowKey={record => record.id}
                        pagination={paginationProps}
                    />
                </div>
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
        console.log(e);
        this.setState({
            visible: false,
        });
    };
}


export default enterprise;