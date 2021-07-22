import React, {Component} from 'react'
import {Row, Col, Tabs, Tree, Button, Modal, Form, Input, Table, Divider} from 'antd';
import {StepBackwardOutlined, PlusCircleOutlined} from '@ant-design/icons';
import './style.css'
import LoanForm from './loanForm/index'
import TravelForm from './travelForm/index'
import ApplyForm from './applyForm/index'
import SendApproval from './sendApproval'
import {message} from "antd/lib/index";
import Bpmn from './newBpmn/bnmn'
import ApprovedList from './approvedList/index'
import CopyUserList from './copyUserList/index'


const {TabPane} = Tabs;
const {TreeNode} = Tree;
const {confirm} = Modal;
export default class index extends Component {
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
                    dataIndex: 'applyName',
                }, {
                    title: '申请人',
                    dataIndex: 'roleName',
                    width: 120,
                }, {
                    title: '填报人',
                    dataIndex: 'roleName',
                    width: 120,
                }, {
                    title: '填报时间',
                    dataIndex: 'startTime',
                    width: 220,
                }, {
                    title: '金额',
                    dataIndex: 'roleName',
                    width: 120,
                },
                {
                    title: '操作',
                    key: 'action',
                    render: (text, record) => (
                        <span>
                          <a onClick={() => {
                              this.editClick(record);
                          }}>审批</a>
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
            roleModal: false,
            inputForm: {
                roleName: '',
            },
            editModal: false,
            defaultData: {},
            checkData: {},
            sendModal: false,
            bpmnModal: false,
            seeType:'',
            TabType:'1',
            applicType:'1',
            businessKey:''
        }
    }

    componentDidMount() {
        this.getData(1);
    }

    getData(current) {
        this.post(`/approval/list`,
            {
                maxResults: this.state.pageSize,
                firstResult: 0,
                // key: 'key_3_3'
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

    callback=(key)=> {
        console.log(key);
        if (key=='1'){
            this.getData(1);
        }
        this.setState({
            TabType:key
        })
    }

   editClick =(data,types) =>{
       let type = data.key.split('_')[2]
       this.setState({
           seeType: types,
           applicType: type,
       })
        console.log(type);
        let url = '';
       if (type == '1') {
           url = `/detail/apply-expense/` + data.businessKey
       }
       if (type == '2') {
           url = `/detail/apply-travel/` + data.businessKey
       }
        if (type == '3') {
            url = `/detail/apply-borrow/` + data.businessKey
        }
         this.get(url).then(res => {
            if (res.code === 1) {
                this.setState({
                    defaultData: res.data,
                    editModal: true,
                    checkData: data,
                    businessKey:data.businessKey
                })
            } else {
                message.error(res.message)
            }
        });
    }

    onSubmit = () => {
        this.setState({
            sendModal: true,
        })
    }
    submitAdd = (data) => {
        console.log(data);
        console.log(this.state.checkData);
        this.post(`/approval/workflow/use/approve`,
            {
                ...data, taskId: this.state.checkData.taskId, billId: this.state.checkData.billId
            }
        ).then(res => {
            if (res.code == 1) {
                this.setState({
                    sendModal: false,
                    editModal: false
                });
                message.success('操作' + res.msg)
                this.getData(1);
            }

        })
    }

    seeBpmn=(data) =>{
        console.log(data);
        this.setState({
            bpmnModal: true,
            checkData: data
        })
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
            <div className="headBox">
                <Tabs defaultActiveKey="1" onChange={this.callback}>
                    <TabPane tab="待审批列表" key="1">
                        {this.state.TabType=='1'?  <div className="contentBox">
                            <div className="content">
                                <Table columns={this.state.columns} dataSource={this.state.tabData}
                                       rowKey={record => record.instanceId}
                                       pagination={paginationProps}/>
                            </div>
                        </div>:''}
                    </TabPane>
                    <TabPane tab="我审批的" key="2">
                        {this.state.TabType=='2'?  <ApprovedList seeBpmn={this.seeBpmn} editClick={this.editClick} ></ApprovedList>:''}

                    </TabPane>
                    <TabPane tab="抄送给我的" key="3">
                        {this.state.TabType=='3'?  <CopyUserList seeBpmn={this.seeBpmn} editClick={this.editClick} ></CopyUserList>:''}

                    </TabPane>
                </Tabs>

                <Modal
                    width="40%"
                    title="借款申请"
                    centered
                    visible={this.state.editModal}
                    footer={null}
                    onCancel={() => this.setState({editModal: false})}
                    destroyOnClose={true}
                >
                    {this.state.applicType=='1'? <ApplyForm seeType={this.state.seeType} onSubmit={this.onSubmit} onCancel={() => this.setState({editModal: false})} defaultData={this.state.defaultData} businessKey={this.state.businessKey} />
                        :this.state.applicType=='2'?
                        <TravelForm seeType={this.state.seeType} onSubmit={this.onSubmit} onCancel={() => this.setState({editModal: false})} defaultData={this.state.defaultData} businessKey={this.state.businessKey}/>:
                        <LoanForm seeType={this.state.seeType} onSubmit={this.onSubmit} onCancel={() => this.setState({editModal: false})}
                                                                                            defaultData={this.state.defaultData} businessKey={this.state.businessKey}/>}


                </Modal>
                <Modal
                    width="30%"
                    title="审批"
                    visible={this.state.sendModal}
                    footer={null}
                    onCancel={() => this.setState({sendModal: false})}
                    destroyOnClose={true}
                >
                    <SendApproval checkData={this.state.checkData}
                                  submitAdd={this.submitAdd}
                                  onCancel={() => this.setState({sendModal: false})}></SendApproval>
                </Modal>
                <Modal
                    width="80%"
                    title="查看流程图"
                    visible={this.state.bpmnModal}
                    footer={null}
                    onCancel={() => this.setState({bpmnModal: false})}
                    destroyOnClose={true}
                >
                    <Bpmn checkData={this.state.checkData}/>
                </Modal>
            </div>

        )
    }
}
