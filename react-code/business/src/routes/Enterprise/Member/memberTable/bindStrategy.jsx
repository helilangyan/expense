import React, { Component } from 'react';
import { Form,Input,TreeSelect,Select,Row,Col,Button  } from 'antd';
import {message} from "antd/lib/index";

const {Option} = Select;
const {TreeNode} = TreeSelect;
class editMember extends Component {
    componentDidMount() {
        this.getStrategyData();
        this.setState({
            formData: this.props.bindData,
        });
    }
    constructor(props) {
        super(props);
        this.state = {
            formData:{},
            strategyData: [],
            strategyId:'',
            enterpriseId:JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
            formItemLayout: {
                labelCol: {
                    span: 5
                },
                wrapperCol: {
                    span: 14
                },
            },
        }
    }
    getStrategyData() {
        this.post(`/enterprise/strategy/list`, {
            enterpriseId: JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
            limit: 100,
            page: 1,
        }).then(res => {
            if (res.code == 1) {
                this.setState({
                    strategyData: res.data.data
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
        this.setState({ strategyId: value});
    }
    submitClick=()=>{
        let subData={
            enterpriseId:JSON.parse(localStorage.getItem("entInfo")).enterpriseId,
            strategyId:this.state.strategyId,
            userId:this.state.formData.userId,
            id:this.state.formData.strategyUserId,
        };
        this.props.submitAdd(subData);
    }
    handleCancel=()=>{
        this.props.onCancel();
    }
    render() {
        return (
            <div>
                <Form {...this.state.formItemLayout}>
                    <Form.Item label='选择策略'>
                        <Select
                                  onChange={(value)=>{this.superiorChange(value,'positionId')}}>
                            {
                                this.state.strategyData.map((item, index) => {
                                    return (
                                        <Option value={item.id} key={index}>{item.strategyName}</Option>
                                    )
                                })
                            }

                        </Select>
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
