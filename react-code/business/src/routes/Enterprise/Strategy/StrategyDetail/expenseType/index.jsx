import React, {Component} from 'react';
import {Row, Col, Button, Tabs, Form, Input, Table, Divider, Modal, message, Switch} from 'antd';
// import './style.css'
import ProjectInput from './projectInput'
import axios from 'axios'
import {baseURL, timeout} from '@/service/url'
import {isAuthenticated, logout} from "@/utils/Session";
import {toJS} from "mobx"
import {inject, observer} from 'mobx-react'


const {confirm} = Modal;
@inject('appStore') @observer
export default class ExpenseType extends Component {
    state = {
        page: 1,
        pageSize: 10,
        total: 0,
        columns: [
            {
                title: '分类编号',
                dataIndex: 'classifyCode',
                width: 120,
            },
            {
                title: '分类名称',
                dataIndex: 'classifyName',
                width: 120,
            },
            {
                title: '限制类型',
                dataIndex: 'limitType',
                width: 120,
                render: (text, record) => (
                    <span>
                        {text=='0'?'无限制':'有限制'}
                    </span>

                ),
            },
            {
                title: '最大限制金额',
                dataIndex: 'money',
                width: 120,
            },
            {
                title: '状态',
                dataIndex: 'classifyStatus',
                render: (text, record) => (
                    <span>
                        <Switch defaultChecked={text == 1 ? true : false} onChange={() => { this.handleStatus(record) }} />
                    </span>

                ),
                width: 120,
            },
            {
                title: '操作',
                key: 'action',
                render: (text, record) => (
                    <span style={{marginLeft: '-15px'}}>
              <Button type="link" onClick={() => {
                  this.editClick(record);
              }}>编辑</Button>
              <Divider type="vertical"/>
              <Button type="text" danger onClick={() => {
                  this.deleteClick(record);
              }}>删除</Button>
            </span>
                ),
                width: 150
            },
        ],
        tabData: [],
        roleModal: false,
        inputForm: {
            enterpriseId: toJS(this.props.appStore.curEnt).enterpriseId,
        },
        enterpriseId: toJS(this.props.appStore.curEnt).enterpriseId
    }

    componentDidMount() {
        this.getData(1);
    }

    getData(current) {
        this.post(`/enterprise/strategy-classify/list`,
            {
                limit: this.state.pageSize,
                page: current,
                enterpriseId: this.state.enterpriseId,
                strategyId:this.props.strategyId
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

    roleInput = () => {
        this.setState({
            roleModal: true
        });
    }
    submitInput = (data) => {
        const that = this;
        console.log(data);
        this.post(`/enterprise/strategy-classify/insert`, {
            ...data,
            classifyStatus: 1,
            strategyId:this.props.strategyId
        }, 'application/json').then(res => {
            if (res.code == 1) {
                that.handleCancel();
                that.getData(this.state.page);
                message.success('操作成功')
            }
        })
    }
    handleStatus = (e) => {
        let status = {
            classifyStatus:e.classifyStatus == 1 ? 0 : 1,
            id:e.id
        }
        this.post(`/enterprise/strategy-classify/update-status`, status).then(res => {
            if (res.code == 1) {
                this.handleCancel();
                this.getData(this.state.page);
                message.success('操作成功')
            }
        })
    }
    handleCancel = () => {
        this.setState({
            roleModal: false,
            inputForm: {
                projectName: '',
                projectCode: '',
                enterpriseId: this.state.enterpriseId,
            },
        });
    }

    editClick(data) {
        this.setState({
            inputForm: data,
            roleModal: true,
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
                that.del(`/enterprise/strategy-classify/del/` + data.id).then(res => {
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
                this.onShowSizeChange(current, pageSize)
            }
        }
        return (
            <div>
                <Row gutter={[10, 10]}>
                    <Col span='2' offset={22} style={{textAlign: "right", marginBottom: '10px'}}>
                        <Button type="primary" onClick={this.roleInput}>添加</Button>
                    </Col>
                </Row>
                <Row>
                    <Col span='24'>
                        <Table columns={this.state.columns} dataSource={this.state.tabData} rowKey={record => record.id}
                               pagination={paginationProps}/>
                    </Col>
                </Row>
                <Modal
                    width="30%"
                    title="项目设置"
                    visible={this.state.roleModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <ProjectInput formData={this.state.inputForm} submitAdd={this.submitInput}
                                  onCancel={this.handleCancel}></ProjectInput>
                </Modal>
            </div>

        );
    }
}

;
