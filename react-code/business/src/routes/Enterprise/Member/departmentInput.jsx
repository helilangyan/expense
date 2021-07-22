import React, { Component } from 'react';
import { Input,Form,Row, Col,Button,TreeSelect   } from 'antd';

const { TreeNode } = TreeSelect;


class roleInput extends Component {
    componentDidMount() {
        this.setState({
            formData: this.props.formData,
            superiorData: this.props.superiorData,
        });
    }
    constructor(props) {
        super(props);
        this.state = {
            formData:{},
            superiorData:[],
            formItemLayout: {
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 5},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 14},
                },
            },
        }
    }
    submitClick=(e)=>{
        this.props.submitAdd(this.state.formData);
    }
    onCancel=(e)=>{
        this.props.onCancel();
    }
    handelChange=(event)=>{
        let e = event.target;
        const List= this.state.formData;
        if (e.name=='departmentName') {
            List.departmentName=e.value
        }
        this.setState({ formData: List});
    }
    superiorChange=(value)=>{
        const List= this.state.formData;
        List.parentId=value
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
            }else {
                return <TreeNode value={item.id} key={item.id} title={item.departmentName} />;
            }

        });
    render() {
        return (
            <div>
                <Form {...this.state.formItemLayout}>
                    <Row>
                        <Col span={24}>
                            <Form.Item  label='部门名称'>
                                <Input placeholder='请输入名称'
                                       name='departmentName'
                                       value={this.state.formData.departmentName}
                                       onChange={this.handelChange}
                                />
                            </Form.Item>
                        </Col>
                        <Col span={24}>
                            <Form.Item  label='上级部门'>
                                <TreeSelect
                                    showSearch
                                    style={{ width: '100%' }}
                                    value={this.state.formData.parentId}
                                    dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                                    placeholder="Please select"
                                    allowClear
                                    treeDefaultExpandAll
                                    onChange={this.superiorChange}
                                >
                                    {
                                        this.renderTreeNodes(this.state.superiorData)
                                    }
                                </TreeSelect>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row>
                        <Col span={24} style={{textAlign:'center',marginTop:"20px"}}>
                            <Button type="primary" onClick={this.submitClick}>确定</Button>
                            &nbsp; &nbsp;
                            <Button onClick={this.onCancel}>取消</Button>
                        </Col>
                    </Row>
                </Form>
            </div>
         );
    }
}

export default roleInput;