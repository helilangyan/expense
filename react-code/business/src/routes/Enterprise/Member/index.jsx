import React, {Component} from 'react';
import {Row, Col, Tabs, Tree, Button, Modal, Form, Input} from 'antd';
import './style.css'
import {StepBackwardOutlined, PlusCircleOutlined} from '@ant-design/icons';
import TitleMessage from './titleMessage'
import DepartmentInput from './departmentInput'
import {message} from "antd/lib/index";
import MebberTable from './memberTable/table'
import ApplyTable from './applyTable/table'
import {toJS} from "mobx/lib/mobx";
import {inject, observer} from "mobx-react/index";
import {withRouter} from "react-router-dom";

const {TabPane} = Tabs;
const {TreeNode} = Tree;
const {confirm} = Modal;

@withRouter @inject('appStore') @observer
class member extends Component {
    componentDidMount() {
        this.getData(1);
        this.getMemberData();
    }

    constructor(props) {
        super(props);
        this.state = {
            tabData: [],
            inputForm: {
                departmentName: '',
                parentId: '',
                enterpriseId: '',
            },
            editId: '',
            nameModal: false,
            roleModal: false,
            editName: '',
            memberData: [],
            typeKey: '1'
        }
    }

    renderTreeNodes = data =>
        data.map(item => {
            if (item.children) {
                return (
                    <TreeNode title={<TitleMessage
                        titleClick={this.titleClick}
                        name={item}
                        nodeKeys={item.id}/>} key={item.id} dataRef={item}>
                        {this.renderTreeNodes(item.children)}
                    </TreeNode>
                );
            } else {
                return <TreeNode key={item.id} title={<TitleMessage name={item.departmentName} nodeKeys={item.id}/>}/>;
            }

        });
    titleClick = (id, type) => {
        const that = this;
        that.setState({
            editId: id
        });
        if (type == 'add') {
            let List = that.state.inputForm;
            List.parentId = id
            that.setState({
                inputForm: List,
            });
            that.roleInput();
        }
        if (type == 'edit') {
            this.setState({
                nameModal: true
            });
        }
        if (type == 'del') {
            that.deleteClick(id);
        }
    }
    deleteClick = (id) => {
        const that = this;
        confirm({
            title: '确认删除?',
            content: '',
            okText: '确定',
            okType: 'danger',
            cancelText: '取消',
            onOk() {
                that.del(`/enterprise/department/delete/` + id).then(res => {
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

    callback=(key)=> {
        console.log(key);
        if (key==1){
            this.getMemberData()
        }
        this.setState({
            typeKey: key
        })
    }

    getData() {
        this.post(`/enterprise/department/findByEnterpriseId`, {enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId,}).then(res => {
            if (res.code == 1) {
                this.setChildren(res.data);
                let dataObj = this.setChildren(res.data);
                this.setState({
                    tabData: dataObj
                });
            }
        })
    }

    setChildren(list, parentId = 0) {
        list.forEach(item => {
            item.title = item.departmentName;
            item.key = item.id
        })
        let parentObj = {};
        list.forEach(o => {
            parentObj[o.id] = o;
        })
        if (!parentId) {
            return list.filter(o => !parentObj[o.parentId]).map(o => (o.children = this.setChildren(list, o.id), o));
        } else {
            return list.filter(o => o.parentId == parentId).map(o => (o.children = this.setChildren(list, o.id), o));
        }
    }

    roleInput = () => {
        this.setState({
            roleModal: true
        });
    }
    handleCancel = (data) => {
        this.setState({
            roleModal: false,
            nameModal: false,
        });
    }
    submitInput = (data) => {
        const that = this;
        this.post(`/enterprise/department/insert`, data).then(res => {
            if (res.code == 1) {
                that.handleCancel();
                that.getData(this.state.page);
                message.success('操作成功')
            }
        })
    }
    editNameChange = (data) => {
        this.setState({
            editName: data.target.value,
        });
    }
    editNameSubmit = () => {
        const that = this;
        this.post(`/enterprise/department/updateDepartmentName`, {
            id: this.state.editId,
            departmentName: this.state.editName
        }).then(res => {
            if (res.code == 1) {
                that.handleCancel();
                that.getData(this.state.page);
                message.success('操作成功')
            }
        })
    }

    getMemberData(id) {
        this.post(`/enterprise/department/findUsers`, {
            departmentId: id,
            enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId
        }).then(res => {
            if (res.code == 1) {
                this.setState({
                    memberData: res.data
                })
            }
        })
    }

    refresh = () => {
        this.getMemberData();
    }

    render() {
        const onSelect = (selectedKeys, info) => {
            console.log('selected', selectedKeys, info);
            this.getMemberData(selectedKeys[0]);
        };
        return (
            <div className="headBox">
                <Tabs onChange={this.callback}>
                    <TabPane tab="成员列表" key="1">
                        <div className="contentBox">
                            <div className="content">
                                <Row>
                                    <Col span={4}>
                                        <div className="departmentTitle">
                                            部门架构
                                        </div>
                                        <div className="departmentContent">
                                            <div style={{
                                                color: "#0f8cff",
                                                fontSize: '18px',
                                                fontWeight: 'bold',
                                                marginBottom: '10px',
                                                cursor: 'pointer'
                                            }}
                                                 onClick={() => this.getMemberData()}
                                            >
                                                {toJS(this.props.appStore.curEnt).enterpriseName}
                                            </div>
                                            <Tree
                                                blockNode={true}
                                                showLine={true}
                                                defaultExpandAll={true}
                                                onSelect={onSelect}
                                            >
                                                {
                                                    this.renderTreeNodes(this.state.tabData)
                                                }
                                            </Tree>
                                        </div>
                                        <div className='departmentAdd'>
                                            <Button type="primary" onClick={this.roleInput}
                                                    icon={<PlusCircleOutlined/>}>
                                                添加部门
                                            </Button>
                                        </div>
                                    </Col>
                                    <Col span={20}>
                                        <MebberTable superiorData={this.state.tabData}
                                                     memberData={this.state.memberData}
                                                     refresh={this.refresh}></MebberTable>
                                    </Col>
                                </Row>
                            </div>
                        </div>
                    </TabPane>
                    <TabPane tab="待审核列表" key="2">
                        {this.state.typeKey=='2'? <ApplyTable superiorData={this.state.tabData}></ApplyTable>:''}
                    </TabPane>
                </Tabs>
                <Modal
                    width="30%"
                    title="新增角色"
                    visible={this.state.roleModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <DepartmentInput formData={this.state.inputForm}
                                     superiorData={this.state.tabData}
                                     submitAdd={this.submitInput}
                                     onCancel={this.handleCancel}></DepartmentInput>
                </Modal>
                <Modal
                    width="30%"
                    title="修改名称"
                    visible={this.state.nameModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <Form>
                        <Row>
                            <Col span={18}>
                                <Form.Item label='部门名称'>
                                    <Input placeholder='请输入新的名称'
                                           name='departmentName'
                                           value={this.state.editName}
                                           onChange={this.editNameChange}
                                    />
                                </Form.Item>
                            </Col>
                        </Row>

                    </Form>
                    <Row>
                        <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                            <Button type="primary" onClick={this.editNameSubmit}>确定</Button>
                            &nbsp; &nbsp;
                            <Button onClick={this.handleCancel}>取消</Button>
                        </Col>
                    </Row>
                </Modal>
            </div>
        );
    }
}

export default member;
