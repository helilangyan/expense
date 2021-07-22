import React, { Component } from 'react';
import { Input,Form,Row, Col,Button  } from 'antd';


class roleInput extends Component {
    componentDidMount() {
        this.setState({
            formData: this.props.formData
        });
    }
    constructor(props) {
        super(props);
        this.state = {
            formData:{},
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
    handelChange=(event)=>{
        let e = event.target;
        const List= this.state.formData;
        if (e.name=='departmentName') {
            List.departmentName=e.value
        }
        this.setState({ formData: List});
    }
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
                        {/*<Col span={24}>*/}
                            {/*<Form.Item  label='部门名称'>*/}
                                {/*<Input placeholder='请输入名称'*/}
                                       {/*name='departmentName'*/}
                                       {/*value={this.state.formData.departmentName}*/}
                                       {/*onChange={this.handelChange}*/}
                                {/*/>*/}
                            {/*</Form.Item>*/}
                        {/*</Col>*/}
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