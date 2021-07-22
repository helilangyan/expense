import React, { Component } from 'react';
import { Row,Col,Button,Tabs,Form,Input,Table,Divider,Modal  } from 'antd';
import './style.css'
import DepartmentInput from './departmentInput'
import {message} from "antd/lib/index";

class department extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page:1,
            pageSize:10,
            total:0,
            searchData:{
                departmentName:''
            },
            columns: [
                {
                    title: '角色名称',
                    dataIndex: 'departmentName',
                    width: 120,
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

                              }}>设置角色</a>
                            <Divider type="vertical"/>
                            <a>删除</a>
                      </span>
                    ),
                    width: 200
                },
            ],
            tabData: [],
            roleModal:false,
            inputForm: {
                departmentName: '',
                parentId: '',
                enterpriseId: '3',
            }
        }
    }
    componentDidMount() {
        this.getData(1);
    }
    getData(current){
        this.post(`/enterprise/department/findByEnterpriseId`,
            {
                limit: this.state.pageSize,
                page: current,
                enterpriseId:'3',
                departmentName:this.state.searchData.departmentName
            }
        ).then(res => {
            if (res.code==1){
                this.setState({
                    tabData: res.data,
                    total:res.data.count
                });
            }

        })
    }
    roleInput=()=>{
        this.setState({
            roleModal: true
        });
    }
    handleCancel=(data)=>{
        this.setState({
            roleModal: false
        });
    }
    submitInput=(data)=>{
        const that=this;
        this.post(`/enterprise/department/insert`, data).then(res => {
            if (res.code==1){
                that.handleCancel();
                that.getData(this.state.page);
                message.success('操作成功')
            }
        })
    }
    editClick(data) {
        const that = this;
        that.get(`/enterprise/role/` + data.id).then(res => {
            if (res.code == 1) {
                this.setState({
                    inputForm: res.data,
                    roleModal: true,
                });
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
            <div>
                <div className="heardBox">
                    <Row>
                        <Col span='6'>
                            部门设置
                        </Col>
                        <Col span='2' offset={16} style={{textAlign: "right"}}>
                            <Button type="primary" onClick={this.roleInput}>添加</Button>
                        </Col>
                    </Row>
                </div>
                <div className="contentBox">
                    <div className="content">
                        <Table columns={this.state.columns} dataSource={this.state.tabData} rowKey={record => record.id}  pagination={paginationProps}/>
                    </div>
                </div>
                <Modal
                    width="30%"
                    title="新增角色"
                    visible={this.state.roleModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <DepartmentInput formData={this.state.inputForm} submitAdd={this.submitInput} onCancel={this.handleCancel}></DepartmentInput>
                </Modal>
            </div>
         );
    }
}

export default department;