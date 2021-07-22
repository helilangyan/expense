import React, { Component } from 'react';
import { Row,Col,Button,Tabs,Form,Input,Table,Divider,Modal  } from 'antd';
import EditMember from './editMember'
import BindStrategy from './bindStrategy'
import {message} from "antd/lib/index";
const { confirm } = Modal;
class menberTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page:1,
            pageSize:10,
            total:0,
            searchData:{
                roleName:''
            },
            columns: [
                {
                    title: '员工编号',
                    dataIndex: 'employeeCode',
                    width: 120,
                },
                {
                    title: '姓名',
                    dataIndex: 'lastName',
                    width: 120,
                    render: (text, record) => (
                        <span>
                            {this.returnName(record)}
                      </span>
                    ),
                },
                {
                    title: '手机',
                    dataIndex: 'phone',
                    width: 120,
                },
                {
                    title: '邮箱',
                    dataIndex: 'email',
                    width: 120,
                },
                {
                    title: '部门',
                    dataIndex: 'departmentName',
                    width: 120,
                },
                {
                    title: '职位',
                    dataIndex: 'positionName',
                    width: 120,
                },
                {
                    title: '角色',
                    dataIndex: 'roleName',
                    width: 120,
                },
                {
                    title: '策列名称',
                    dataIndex: 'strategyName',
                    width: 120,
                },
                {
                    title: '操作',
                    key: 'action',
                    render: (text, record) => (
                        <span>
                            <a onClick={()=>{this.editMember(record)}}>编辑</a>
                             <Divider type="vertical"/>
                            <a onClick={()=>{this.bindStrategy(record)}}>绑定策略</a>
                             <Divider type="vertical"/>
                            <a onClick={()=>{this.deleteUser(record)}}>移出企业</a>
                      </span>
                    ),
                    width: 200
                },
            ],
            tabData: [],
            editModal:false,
            inputForm: {
                roleName: '',
            },
            editData:{},
            bindModal:false,
            bindData:{}
        }
    }
    returnName=(record)=>{
        return record.firstName+record.lastName
    }
    editMember(record){
        this.setState({
            editModal:true,
            editData:record
        })
    } bindStrategy(record){
        console.log(record);
        this.setState({
            bindModal:true,
            bindData:record
        })
    }
    deleteUser(record){
        console.log(record);
        const that = this;
        confirm({
            title: '是否确认移出企业?',
            content: '',
            okText: '确定',
            okType: 'danger',
            cancelText: '取消',
            onOk() {
                that.del(`/enterprise-user/del/` + record.id).then(res => {
                    if (res.code == 1) {
                        message.success('移出成功')
                        that.props.refresh();
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
    handleCancel=()=>{
        this.setState({
            editModal:false
        })
    }
    submitInput=(data)=>{
        console.log(data);
        let that=this;
        this.post(`/enterprise-user/enterprise-user/update`, data,'application/json').then(res => {
            if (res.code == 1) {
                message.success('操作成功')
                this.handleCancel();
                that.props.refresh();
            }
        })
    }
    searchChange=(e)=>{
        this.setState({
            searchData: {
                roleName: e.target.value
            }
        })
    }
    searchClick=()=>{

    }
    handleBind=()=>{
        this.setState({
            bindModal:false
        })
    }
    submitBind=(data)=>{
        console.log(data);
        let that=this;
        this.post(`/enterprise/department/insert-strategy`, data,'application/json').then(res => {
            if (res.code == 1) {
                message.success('操作成功')
                this.handleBind();
                that.props.refresh();
            }
        })

    }
    render() {
        return (
            <div>
                <div className="contentBox">
                    <div className="contentTitle">
                        <Row>
                            <Col span='10'>
                                <Form layout="inline" onSubmit={this.handleSubmit}>
                                    <Form.Item label="用户名">
                                        <Input
                                            value={this.state.searchData.roleName}
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
                            <Col span='2' offset={12} style={{textAlign: "right"}}>
                                <Button type="primary" onClick={this.roleInput}>添加</Button>
                            </Col>
                        </Row>
                    </div>
                    <div className="content">
                        <Table columns={this.state.columns} dataSource={this.props.memberData} rowKey={record => record.userId}/>
                    </div>
                </div>
                <Modal
                    width="30%"
                    title="编辑"
                    visible={this.state.editModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <EditMember superiorData={this.props.superiorData} editData={this.state.editData} submitAdd={this.submitInput} onCancel={this.handleCancel}></EditMember>
                </Modal>
                <Modal
                    width="30%"
                    title="编辑"
                    visible={this.state.bindModal}
                    footer={null}
                    onCancel={this.handleBind}
                    destroyOnClose={true}
                >
                    <BindStrategy bindData={this.state.bindData} submitAdd={this.submitBind} onCancel={this.handleBind}></BindStrategy>
                </Modal>
            </div>
         );
    }
}

export default menberTable;
