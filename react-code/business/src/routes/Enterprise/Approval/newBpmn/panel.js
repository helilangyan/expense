import React, {Component} from 'react';
import '../style.css'
import {Row, Col, Button, Tabs, Form, Input, Select, Tag, Space,Modal,message} from 'antd';
import SelectUser from '../../../Public/selectUser'
import {diagramXML} from "./xml";

const {TabPane} = Tabs;
const {Option} = Select;

class Panel extends Component {

    componentDidMount() {
        // this.getApplyData(this.props.businessObject.id);
    }

    componentDidUpdate() {

    }

    constructor(props) {
        super(props);
        this.state = {
            formItemLayout: {
                labelCol: {
                    span: 4
                },
                wrapperCol: {
                    span: 20
                },
            },
            businessObject: this.props.businessObject,
            id: this.props.businessObject.id,
            type: this.props.businessObject.$type,
            name: this.props.businessObject.name,
            userModal:false,
            formProperty: [],
            tagData:this.props.applyData
        }
    }
    getApplyData=(id)=>{
        this.get('/bpm/design/design/usertasks/'+id).then(res=>{
            if (res.code==1){
                let userData=[];
                res.data.forEach(item=>{
                    userData.push({
                        userid:item.operatorId,
                        username:item.operatorName,
                    })
                })
                this.setState({
                    tagData:userData
                })
            }
        })
    }
    handleChange(value) {
        console.log(`selected ${value}`);
    }

    log(index) {
        this.props.clearApply(index)
    }

    addUser=()=>{
        this.setState({
            userModal:true
        })
    }
    saveUser=()=>{
        this.props.saveUser();
        // let userData=[];
        // this.state.tagData.forEach((i,x)=>{
        //     userData.push({
        //         userid:i.userId,
        //         username:i.firstName + i.lastName,
        //     })
        // })
        // let obj = {
        //     stepid:this.props.businessObject.id,
        //     auditUserDtos:JSON.stringify(userData)
        // };
        // this.post('/bpm/design/design/stepuser/add',obj).then(res=>{
        //     message.success('保存成功')
        // })
    }
    handleCancel=()=>{
        this.setState({
            userModal:false
        })
    }
    submitInput=(user)=>{
        let userData=[];
        let tagData=this.props.applyData;
        user.forEach((item,index) => {
            tagData.forEach((i,x)=>{
                if(item.userId==i.userid){
                    tagData.splice(x,1)
                };
            })
           tagData.push({
               userid:item.userId,
               username:item.firstName + item.lastName,
           });
        });
        this.props.setApply(tagData)
        // this.setState({
        //     tagData:tagData
        // })
        this.handleCancel();
    }
    //修改表单
    updateAttr(type, v) {
        this.props.updateAttr(type, v)
    }
    // 默认处理
    defaultHandle() {
        let documentation = this.props.businessObject.documentation && this.props.businessObject.documentation.length ? this.businessObject.props.documentation : [this.props.moddle.create('bpmn:Documentation', {text: ""})]
        let obj = {documentation}
        if (this.formType.includes(this.businessObject.$type)) {
            obj.extensionElements = this.extensionElements
        }
        this.modeling.updateProperties(this.element, obj);
        if (this.formType.includes(this.businessObject.$type)) {
            this.formProperty = this.extensionElements.values
        }
        if (['bpmn:SequenceFlow'].includes(this.businessObject.$type)) {
            this.conditionType = this.businessObject.conditionExpression ? 'Expression' : ''
        }

    }

    render() {
        return (
            <div className='panelWrapper'>
                <div className="panelHeader">{this.props.businessObject.name}</div>
                <Tabs defaultActiveKey="1">
                    <TabPane tab="普通" key="1">

                    </TabPane>
                </Tabs>
                <Form className="panelForm" layout="vertical">
                    <Form.Item label='id'>
                        <Input placeholder='请输入id'
                               name='projectCode'
                               value={this.props.businessObject.id}
                               onChange={v=>this.updateAttr('id',v)}
                        />
                    </Form.Item>
                    <Form.Item label='名称'>
                        <Input placeholder='请输入名称'
                               name='projectCode'
                               value={this.props.businessObject.name}
                               onChange={v=>this.updateAttr('name',v)}
                        />
                    </Form.Item>
                    {
                        this.props.businessObject.$type=='bpmn:UserTask'?<Form.Item label='审批人'>
                            <div className='applyList'>
                                {
                                    this.props.applyData.map((item,index)=>{
                                        return (
                                            <Tag key={index} closable onClose={()=>this.log(index)}>
                                                {item.username}
                                            </Tag>
                                        )
                                    })
                                }

                            </div>
                            <Row>

                                <Col span={24} style={{textAlign:'right',marginTop:'10px'}}>
                                    <Space>
                                        <Button type="primary" onClick={this.addUser}>新增</Button>

                                        <Button type="primary" onClick={this.saveUser}>保存</Button>
                                    </Space>
                                </Col>
                            </Row>
                        </Form.Item>:''
                    }

                </Form>
                <Modal
                    width="30%"
                    title="新增角色"
                    visible={this.state.userModal}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={true}
                >
                    <SelectUser submitAdd={this.submitInput} onCancel={this.handleCancel}></SelectUser>
                </Modal>
            </div>
        );
    }
}

export default Panel;
