import React, {Component} from 'react';
import {Input, Form, Button, Table, Divider, Avatar, Modal, Switch,Select} from 'antd';
import './style.css'
import UserAdd from './addUser'
import RoleSet from './roleSet'
import {message} from "antd/lib/index";
const { Option } = Select;

function showModal() {
    console.log('123456')
    // this.setState({
    //     visible: true,
    // });
};

class user extends Component {
    componentDidMount() {
      this.getData(1);

    }

    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            roleModal: false,
            tabData: [],
            columns: [
                {
                    title: '编号',
                    dataIndex: 'index',
                    key: 'index',
                    align: 'center',
                    width: 60,
                    render: (text,record,index) => `${index+1}`,
                },
                {
                    title: '头像',
                    className: 'column-money',
                    dataIndex: 'money',
                    render: icon => <Avatar size={44} icon="user"/>,
                    width: 100,
                },
                {
                    title: '账号',
                    dataIndex: 'user',
                    width: 120,
                },
                // {
                //     title: '密码',
                //     dataIndex: 'password',
                //     width:100,
                // },
                {
                    title: '姓名',
                    dataIndex: 'username',
                    width: 160,
                },
                {
                    title: '手机号码',
                    dataIndex: 'phone',
                    width: 150,
                },
                {
                    title: 'Email',
                    dataIndex: 'Email',
                    width: 150,
                },
                {
                    title: '角色',
                    dataIndex: 'roles',
                    render: roles => (
                        <span>
                            {
                            }
                        </span>
                    ),
                    width: 100,
                },
                {
                    title: '状态',
                    dataIndex: 'status',
                    render: (status,record) => (
                        <Switch defaultChecked={status=='1'} onClick={()=>{
                            this.statusSet(record)
                        }}/>
                    ),
                },
                {
                    title: '操作',
                    key: 'action',
                    render: (text, record) => (
                        <span>
                            <a onClick={showModal}>编辑</a>
                             <Divider type="vertical"/>
                              <a onClick={() => {
                                  this.setRole(record);
                              }}>设置角色</a>
                            <Divider type="vertical"/>
                            <a>删除</a>
                      </span>
                    ),
                    width: 200
                },
            ],
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
            page:1,
            pageSize:10,
            total:0,
            searchData:{
                username:'',
                state:''
            },
            authorityList:[],
            setId:'',
        }
        this.addModal = this.addModal.bind(this);
    }
    getData(current){
        this.post(`/user/list`,
            {
                limit: this.state.pageSize,
                page: current,
                username:this.state.searchData.username,
                state:this.state.searchData.state,
            }
            ).then(res => {
            this.setState({
                tabData: res.data.data,
                total:res.data.count
            });
        })
    }
    searchNameChange=(e)=>{
        this.setState({
            searchData: {
                username: e.target.value,
                state:this.state.searchData.state
            }
        })
    }
    searchStateChange=(value)=>{
        this.setState({
            // inputValue:e.target.value
            searchData: {
                username: this.state.searchData.username,
                state:value
            }
        })
    }
    searchClick=()=>{
        this.getData(this.state.page);
    }
    searchReset=()=>{
        this.setState({
            searchData: {
                username: '',
                state:''
            }
        })
    }
    submitAdd=(data)=>{
        let dataObj={};
        data.forEach((item,index)=>{
            dataObj[item.name]=item.value
        })
    }
    setRole(data){
        this.post(`/user-role/list`, {userId:data.id}).then(res => {
            if (res.code == 1) {
                this.setState({
                    authorityList:res.data,
                    roleModal: true,
                    setId:data.id
                });
            } else {
                message.error(res.msg)
            }

        })
    }
    statusSet(data){
        console.log(data);
        let s =data.status=='1'?'0':'1'
        this.putUrl(`/user/operation-status?userId=`+data.id+'&&status='+s).then(res => {
            if (res.code == 1) {
                message.success('操作成功')
                this.getData(this.state.page);
            } else {
                message.error(res.msg)
            }

        })
    }
    submitRole=(data)=>{
        this.post(`/user-role/insert`, {userId:this.state.setId,roleIds:data.toString()}).then(res => {
            if (res.code == 1) {
                this.setState({
                    roleModal: false,
                });
                message.success('操作成功')
            } else {
                message.error(res.msg)
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
                <div className="tableHead">
                    <Form layout="inline" onSubmit={this.handleSubmit}>
                        <Form.Item label="用户名">
                            <Input
                                value={this.state.searchData.username}
                                onChange={this.searchNameChange}
                                placeholder="请输入用户名"
                            />
                        </Form.Item>
                        <Form.Item label="状态">
                            <Select style={{ width: 120 }}  placeholder="请选择" onChange={this.searchStateChange}>
                                <Option value="1">启用</Option>
                                <Option value="0">禁用</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" onClick={this.searchClick}>查询</Button>
                            &nbsp; &nbsp;
                            <Button onClick={this.searchReset}>重置</Button>
                        </Form.Item>
                    </Form>
                </div>
                <div className="tableContent">
                    <Button type="danger" style={{marginBottom: '10px'}} onClick={this.addModal}>新增</Button>

                    <Table columns={this.state.columns} dataSource={this.state.tabData} rowKey={record => record.id}  pagination={paginationProps}/>
                </div>

                <Modal
                    width="40%"
                    title="新增用户"
                    visible={this.state.visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <UserAdd submitAdd={this.submitAdd} onCancel={this.handleCancel}></UserAdd>
                </Modal>
                <Modal
                    width="40%"
                    title="角色设置"
                    visible={this.state.roleModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <RoleSet authorityList={this.state.authorityList} submitAdd={this.submitRole} onCancel={this.handleCancel}></RoleSet>
                </Modal>
            </div>
        );
    }

    addModal() {
        this.setState({
            visible: true,
        });
    }


    handleCancel = e => {
        this.setState({
            visible: false,
            roleModal: false,
        });
    };
}

export default user;