import React, {Component} from 'react';
import {Row, Col, Button, Radio, Form, Input, Table, TreeSelect, Modal, Select} from 'antd';
import {message} from "antd/lib/index";


const {TreeNode} = TreeSelect;
const {Option} = Select;

class menberTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 1,
            pageSize: 10,
            total: 0,
            searchData: {
                roleName: ''
            },
            columns: [
                {
                    title: '姓名',
                    width: 160,
                    dataIndex: 'firstName',
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
                    title: '申请方式',
                    dataIndex: 'roleName',
                    width: 120,
                },
                {
                    title: '申请时间',
                    dataIndex: 'applyTime',
                    width: 120,
                    render: applyTime => (
                        <span>
                            {applyTime.split('T')[0]}&nbsp;
                            {applyTime.split('T')[1].split('.')[0]}
                        </span>
                    ),
                },
                {
                    title: '操作',
                    key: 'action',
                    render: (text, record) => (
                        <span>
                            {record.status=='0'?<a onClick={() => {
                                this.approvalApply(record)
                            }}>审核</a>:<a>已通过</a>}

                      </span>
                    ),
                    width: 200
                },
            ],
            tabData: [],
            applyModal: false,
            inputForm: {
                roleName: '',
            },
            formData: {
                status:1
            },
            superiorData: [],
            formItemLayout: {
                labelCol: {
                    span: 6
                },
                wrapperCol: {
                    span: 14
                },
            },
            positionData: [],
            roleData: [],
            applyData:'',
            enterpriseId:JSON.parse(localStorage.getItem("entInfo")).enterpriseId
        }
    }
    formRef = React.createRef()

    componentDidMount() {
        this.getData();
        this.getPositionData();
        this.getRoleData();
        this.setState({
            superiorData: this.props.superiorData,
        });
    }
    returnName=(record)=>{
        return record.firstName+record.lastName
    }

    getData() {
        this.post(`/enterprise-user-apply/enterprise-list`, {
            enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
            limit: this.state.pageSize,
            page: this.state.page,
        }).then(res => {
            if (res.code == 1) {
                this.setState({
                    tabData: res.data.data,
                    total: res.data.count
                });
            }
        })
    }

    getPositionData() {
        this.post(`/enterprise/position/findByEnterpriseId`, {
            enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
            limit: 100,
            page: this.state.page,
        }).then(res => {
            if (res.code == 1) {
                this.setState({
                    positionData: res.data.data
                });
            }
        })
    }
    getRoleData() {
        this.post(`/enterprise/role/list`, {
            enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
            limit: 100,
            page: this.state.page,
        }).then(res => {
            if (res.code == 1) {
                this.setState({
                    roleData: res.data.data
                });
            }
        })
    }

    approvalApply(record) {
        console.log(record);
        this.setState({
            applyModal: true,
            applyData:record
        })
    }

    handleCancel = () => {
        this.setState({
            applyModal: false
        })
    }
    handelChange = (event) => {
        let e = event.target;
        console.log(e.value);
        const List= this.state.formData;
        if (e.name=='status') {
            List.status=e.value
        }  if (e.name=='employeeCode') {
            List.employeeCode=e.value
        }if (e.name=='message') {
            List.message=e.value
        }
        this.setState({ formData: List});
    }
    superiorChange=(value,type)=>{
        const List= this.state.formData;
        List[type]=value;
        this.setState({ formData: List});
    }
    renderTreeNodes = data =>
        data.map(item => {
            if (item.children) {
                return (
                    <TreeNode value={item.id} title={item.departmentName} key={item.id} dataRef={item}>
                        {this.renderTreeNodes(item.children)}
                    </TreeNode>
                );
            } else {
                return <TreeNode value={item.id} key={item.id} title={item.departmentName}/>;
            }

        });

    submitClick=()=>{
        this.formRef.current.validateFields().then(() => {
            let subData={...this.state.formData,
                enterpriseId:JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
                applyId:this.state.applyData.applyId,
                userId:this.state.applyData.userId};
            this.post(`/enterprise-user-apply/approve`, subData,'application/json').then(res => {
                if (res.code==1){
                    this.handleCancel();
                    message.success('操作成功')
                }
            })
            console.log(subData);
        }).catch((errorInfo) => {
            message.error("请输入完整信息");
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
                        </Row>
                    </div>
                    <div className="content">
                        <Table columns={this.state.columns} dataSource={this.state.tabData}
                               rowKey={record => record.applyId} pagination={paginationProps}/>
                    </div>
                </div>
                <Modal
                    width="30%"
                    title="审批"
                    visible={this.state.applyModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <Form {...this.state.formItemLayout} ref={this.formRef}>
                        <Form.Item label='请选择审批结果' required>
                            <Radio.Group name='status' onChange={this.handelChange} value={this.state.formData.status}>
                                <Radio value={1}>通过</Radio>
                                <Radio value={0}>拒绝</Radio>
                            </Radio.Group>
                        </Form.Item>
                        <Form.Item label='员工编号' name="employeeCode" rules={[{ required: true, message: '请输入员工编号!' }]}>
                            <Input placeholder='请输入员工编号'
                                   name='employeeCode'
                                   onChange={this.handelChange}
                            />
                        </Form.Item>
                        <Form.Item label='部门' name="departmentId" rules={[{ required: true, message: '请选择部门!' }]}>
                            <TreeSelect
                                showSearch
                                style={{width: '100%'}}
                                value={this.state.formData.departmentId}
                                dropdownStyle={{maxHeight: 400, overflow: 'auto'}}
                                placeholder="请选择"
                                allowClear
                                treeDefaultExpandAll
                                onChange={(value)=>{this.superiorChange(value,'departmentId')}}
                            >
                                {
                                    this.renderTreeNodes(this.state.superiorData)
                                }
                            </TreeSelect>
                        </Form.Item>
                        <Form.Item label='职位' name="positionId" rules={[{ required: true, message: '请选择职位!' }]}>
                            <Select placeholder="请选择"
                                      onChange={(value)=>{this.superiorChange(value,'positionId')}}>
                                {
                                    this.state.positionData.map((item, index) => {
                                        return (
                                            <Option value={item.id} key={index}>{item.positionName}</Option>
                                        )
                                    })
                                }

                            </Select>
                        </Form.Item>
                        {/*<Form.Item label='上级'>*/}
                            {/*<Select value={this.state.formData.superior} onChange={(value)=>{this.superiorChange(value,'superior')}}>*/}
                                {/*{*/}
                                    {/*this.state.positionData.map((item, index) => {*/}
                                        {/*return (*/}
                                            {/*<Option value={item.id} key={item.id}>{item.positionName}</Option>*/}
                                        {/*)*/}
                                    {/*})*/}
                                {/*}*/}

                            {/*</Select>*/}
                        {/*</Form.Item>*/}
                        <Form.Item label='角色'  name="roleId" rules={[{ required: true, message: '请选择角色!' }]}>
                            <Select placeholder="请选择" onChange={(value)=>{this.superiorChange(value,'roleId')}}>
                                {
                                    this.state.roleData.map((item, index) => {
                                        return (
                                            <Option value={item.id} key={index}>{item.roleName}</Option>
                                        )
                                    })
                                }

                            </Select>
                        </Form.Item>
                        <Form.Item label='描述'>
                            <Input.TextArea  name='message'
                                             value={this.state.formData.message}
                                             onChange={this.handelChange} />
                        </Form.Item>
                        <Row>
                            <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                                <Button type="primary" onClick={this.submitClick}>确定</Button>
                                &nbsp; &nbsp;
                                <Button onClick={this.handleCancel}>取消</Button>
                            </Col>
                        </Row>
                    </Form>
                </Modal>
            </div>
        );
    }
}

export default menberTable;
