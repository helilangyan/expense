import React, { Component } from 'react';
import { Input,Form,Row, Col,Button  } from 'antd';

const { TextArea } = Input;
@Form.create()
class userAdd extends Component {
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
            dataList:[{
                text:'角色',
                placeholder:'请输入角色名',
                value:'',
                name:'roleName'
            },{
                text:'备注',
                placeholder:'请输入备注',
                value:'',
                name:'remark',
                type:'textarea '
            }],
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
        if (e.name=='roleName') {
            List.roleName=e.value
        }
        if (e.name=='remark') {
            List.remark=e.value
        }
        this.setState({ formData: List});
    }
    render() {
        const {getFieldDecorator, getFieldValue} = this.props.form
        return (
            <div>
                <Form {...this.state.formItemLayout}>
                    <Row>
                        <Col span={24}>
                            <Form.Item  label='角色'>
                                {
                                    getFieldDecorator('roleName', {
                                        rules: [{required: true, message: '请输入角色'}],
                                        validateTrigger: 'onBlur',
                                        //初始赋值
                                        initialValue: this.state.formData.roleName
                                    })(
                                        <Input placeholder='请输入角色'
                                               name='roleName'
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

export default userAdd;