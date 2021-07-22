import React, { Component } from 'react';
import { Form,Input,TreeSelect,Select,Row,Col,Button  } from 'antd';
import {message} from "antd/lib/index";

const {Option} = Select;
const {TreeNode} = TreeSelect;
class editMember extends Component {
    componentDidMount() {
        this.getPositionData();
        this.getRoleData();
        this.setState({
            superiorData: this.props.superiorData,
            formData: this.props.editData,
        });
    }
    constructor(props) {
        super(props);
        this.state = {
            formData:{},
            positionData: [],
            roleData: [],
            applyData:'',
            superiorData: [],
            formItemLayout: {
                labelCol: {
                    span: 5
                },
                wrapperCol: {
                    span: 14
                },
            },
            enterpriseId:JSON.parse(localStorage.getItem("entInfo")).enterpriseId
        }
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

    getPositionData() {
        this.post(`/enterprise/position/findByEnterpriseId`, {
            enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
            limit: 100,
            page: 1,
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
            page: 1,
        }).then(res => {
            if (res.code == 1) {
                this.setState({
                    roleData: res.data.data
                });
            }
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
    submitClick=()=>{
        let subData={...this.state.formData,
            enterpriseId:JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
            applyId:this.state.applyData.applyId,
            userId:this.state.applyData.userId};
        this.props.submitAdd(this.state.formData);
    }
    handleCancel=()=>{
        this.props.onCancel();
    }
    render() {
        return (
            <div>
                <Form {...this.state.formItemLayout}>
                    <Form.Item label='员工编号'>
                        <Input placeholder='请输入员工编号'
                               name='employeeCode'
                               value={this.state.formData.employeeCode}
                               onChange={this.handelChange}
                        />
                    </Form.Item>
                    <Form.Item label='部门'>
                        <TreeSelect
                            showSearch
                            style={{width: '100%'}}
                            value={this.state.formData.departmentId}
                            dropdownStyle={{maxHeight: 400, overflow: 'auto'}}
                            placeholder="Please select"
                            allowClear
                            treeDefaultExpandAll
                            onChange={(value)=>{this.superiorChange(value,'departmentId')}}
                        >
                            {
                                this.renderTreeNodes(this.state.superiorData)
                            }
                        </TreeSelect>
                    </Form.Item>
                    <Form.Item label='职位'>
                        <Select   value={this.state.formData.positionId}
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
                    <Form.Item label='角色'>
                        <Select value={this.state.formData.roleId} onChange={(value)=>{this.superiorChange(value,'roleId')}}>
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
            </div>
         );
    }
}

export default editMember;
