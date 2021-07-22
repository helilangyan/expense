import React, { Component } from 'react';
import { Input,Form,Row, Col,Button  } from 'antd';

const { TextArea } = Input;
@Form.create()
class authorityInput extends Component {
    componentDidMount() {
        this.setState({
            formData: this.props.formData
        });
    }
    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            autoCompleteResult: [],
            formItemLayout: {
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 10},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 14},
                },
            },
            formData:{}
        }
    }
    submitClick=(e)=>{
        e.preventDefault()
        this.props.form.validateFields({ force: true }, (err, values) => {
            if (!err) {
                this.props.submitAdd(this.state.formData);
            }
        })
    }
    onCancel=()=>{
        this.props.onCancel();
    }
    handelChange=(event)=>{
        let e = event.target;
        const List= this.state.formData;
        if (e.name=='authorityName') {
            List.authorityName=e.value
        }
        if (e.name=='remark') {
            List.remark=e.value
        }
        this.setState({ formData: List});
    }
    render() {
        const {getFieldDecorator} = this.props.form
        return (
            <div>
                <Form {...this.state.formItemLayout}>
                    <Row>
                        <Col span={24}>
                            <Form.Item  label='权限名称' >
                                {
                                    getFieldDecorator('authorityName', {
                                        rules: [{required: true, message: '请输入权限名称'}],
                                        validateTrigger: 'onBlur',
                                        //初始赋值
                                        initialValue: this.state.formData.authorityName
                                    })(
                                        <Input placeholder='请输入权限名称'
                                               name='authorityName'
                                               onChange={this.handelChange}
                                        />
                                    )
                                }

                            </Form.Item>
                            <Form.Item  label='备注' >
                                <TextArea placeholder='请输入备注'
                                       value={this.state.formData.remark}
                                          name='remark'
                                       onChange={this.handelChange}
                                />
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

export default authorityInput;