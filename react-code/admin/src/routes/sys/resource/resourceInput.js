import React, { Component } from 'react';
import { Input,Form,Row, Col,Button,Radio,Select   } from 'antd';

const { TextArea } = Input;
const { Option } = Select;
@Form.create()
class userAdd extends Component {

    componentDidMount() {
        this.getResourceData();
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
                    sm: {span: 8},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 12},
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
            formData:{},
            resourceData:[]
        }
    }
    getResourceData=()=>{
        this.post(`/resource/resource-tree`).then(res => {
            console.log(this.state.tabData);
            this.setState({
                resourceData: res.data
            });

        })
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
        for (var input in List){
            if (input === e.name) {
                List[input] = e.value;
            }
        }
        this.setState({ formData: List});
    }
    selectChange=(value)=>{
        const List= this.state.formData;
        List.parentId = value
        this.setState({ formData: List});
    }
    render() {
        const {getFieldDecorator} = this.props.form
        return (
            <div>
                <Form {...this.state.formItemLayout}>
                    <Row>
                        <Col span={24}>
                            <Form.Item  label='资源名称' >
                                {
                                    getFieldDecorator('resourceName', {
                                        rules: [{required: true, message: '请输入资源名称'}],
                                        validateTrigger: 'onBlur',
                                        //初始赋值
                                        initialValue: this.state.formData.resourceName
                                    })(
                                        <Input placeholder='请输入资源名称'
                                               name='resourceName'
                                               onChange={this.handelChange}
                                        />
                                    )
                                }

                            </Form.Item>
                            <Form.Item  label='英文名称' >
                                <Input placeholder='请输入英文名称'
                                       value={this.state.formData.resourceNameEnglish}
                                       name='resourceNameEnglish'
                                       onChange={this.handelChange}
                                />
                            </Form.Item>
                            <Form.Item  label='资源URL地址' >
                                <Input placeholder='请输入资源URL地址'
                                       value={this.state.formData.resourceUrl}
                                       name='resourceUrl'
                                       onChange={this.handelChange}
                                />
                            </Form.Item>
                            <Form.Item  label='资源类型' >
                                {
                                    getFieldDecorator('isMenu', {
                                        rules: [{required: true, message: '请选择资源类型'}],
                                        validateTrigger: 'onBlur',
                                        //初始赋值
                                        initialValue: this.state.formData.isMenu
                                    })(
                                        <Radio.Group onChange={this.handelChange} name='isMenu'>
                                            <Radio value={'0'}>菜单</Radio>
                                            <Radio value={'1'}>按钮</Radio>
                                        </Radio.Group>
                                    )
                                }

                            </Form.Item>
                            <Form.Item  label='资源图标' >
                                <Input placeholder='请选择资源图标'
                                       value={this.state.formData.icon}
                                       name='icon'
                                       onChange={this.handelChange}
                                />
                            </Form.Item>
                            <Form.Item  label='上级资源' >
                                <Select value={this.state.formData.parentId} name='parentId' onChange={this.selectChange}>
                                    <Option value='0'></Option>
                                    {
                                        this.state.resourceData.map((item,index)=>{
                                            return (
                                                <Option key={index} value={item.id}>{item.resourceName}</Option>
                                            )
                                        })
                                    }
                                </Select>
                            </Form.Item>
                            <Form.Item  label='备注' >
                                <Input type='textarea ' placeholder='请输入备注'
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