import React, {Component} from 'react';
import {Input, Form, Row, Col, Button, Upload, Icon} from 'antd';
// import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import {isAuthenticated} from "../../../utils/Session";
import {upUrl} from "../../../utils/http";

function getBase64(img, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
}

function beforeUpload(file) {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
        message.error('You can only upload JPG/PNG file!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
        message.error('Image must smaller than 2MB!');
    }
    return isJpgOrPng && isLt2M;
}

@Form.create()
// const { getFieldDecorator } = this.props.form;
class userAdd extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            name: '',
            phone: '',
            Email: '',
            status: '',
            type: '',
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
            dataList: [
                {
                    text: '密码',
                    type: 'password',
                    placeholder: '请输入密码',
                    value: '',
                    name: 'password'
                }, {
                    text: '姓名',
                    placeholder: '请输入姓名',
                    value: '',
                    name: 'name'
                }, {
                    text: '手机号码',
                    placeholder: '请输入手机号码',
                    value: '',
                    name: 'phone'
                }, {
                    text: 'Email',
                    placeholder: '请输入Email',
                    value: '',
                    name: 'email'
                },
            ],
            loading: false,

        }
    }

    handleSubmit = (e) => {
        console.log(e);
        // e.preventDefault();
        // this.props.form.validateFieldsAndScroll((err, values) => {
        //     if (!err) {
        //         console.log('Received values of form: ', values);
        //     }
        // });
    }
    submitClick = () => {
        //验证用户名不能为纯数字（未做）
        this.props.submitAdd(this.state.dataList)
    }
    handelChange = (event) => {
        let e = event.target;
        const List = this.state.dataList;
        List.map((item, index) => {
            if (item.name === e.name) {
                item.value = e.value;
            }
        });
        this.setState({dataList: List});
        if (e.name = 'username') {
            console.log(e.name);
            this.setState({username: e.value});
        }
    }
    userBlur = event => {
        // console.log(event.target.value);
        // this.post(`/user/check`, {username: event.target.value,}).then(res => {
        //     if (res.code == 0) {
        //         alert(res.data)
        //     }
        // })
    }
    uploadChange = info => {
        if (info.file.status === 'uploading') {
            this.setState({loading: true});
            return;
        }
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, imageUrl =>
                this.setState({
                    imageUrl,
                    loading: false,
                }),
            );
        }
    };

    handleValidator = (rule, val, callback) => {
        if (!val) {
            callback();
        }
        var reg = /^[0-9]*$/;
        if ( reg.test(val)){
            callback('账号不能为纯数字！');
        }else {
            this.post(`/user/check`, {username: val}).then(res => {
                if (res.code == 0) {
                    callback(res.data);
                }
                if (res.code == 1) {
                    callback();
                }
            })
        }


    }

    render() {
        const {loading, imageUrl} = this.state;
        const uploadButton = (
            <div>
                <Icon type={this.state.loading ? 'loading' : 'plus'}/>
                <div className="ant-upload-text">上传头像</div>
            </div>
        );
        const {getFieldDecorator} = this.props.form
        return (
            <div>
                <Form {...this.state.formItemLayout} onSubmit={this.handleSubmit}>
                    <Row>
                        <Col span={12}>
                            <Form.Item label="头像" rules={[{required: true}]}>
                                <Upload
                                    name="file"
                                    listType="picture-card"
                                    className="avatar-uploader"
                                    showUploadList={false}
                                    action={upUrl}
                                    beforeUpload={beforeUpload}
                                    onChange={this.uploadChange}
                                    headers={
                                        {'Authorization': isAuthenticated()}
                                    }
                                >
                                    {imageUrl ?
                                        <img src={imageUrl} alt="avatar" style={{width: '100%'}}/> : uploadButton}
                                </Upload>
                            </Form.Item>
                        </Col>
                        <Col span={12}>
                            <Form.Item label='账号'
                            >
                                {getFieldDecorator('validator', {
                                    rules: [{
                                        required: true,
                                        message: '请输入账号'
                                    },
                                        {validator: this.handleValidator},
                                    ],
                                    validateTrigger: 'onBlur',
                                    //初始赋值
                                    initialValue: this.state.username
                                })(
                                    <Input
                                        name='username'
                                        onChange={this.handelChange}
                                        onBlur={this.userBlur}
                                        placeholder='请输入账号'
                                    />
                                )}

                            </Form.Item>
                        </Col>
                    </Row>
                    <Row>
                        {
                            this.state.dataList.map((item, index) => {
                                return (
                                    <Col span={12} key={index}>
                                        <Form.Item label={item.text}
                                                   rules={[{required: true, message: 'Please input your username!'}]}
                                        >
                                            <Input
                                                type={item.type ? item.type : 'text'}
                                                value={item.value}
                                                name={item.name}
                                                onChange={this.handelChange}
                                                placeholder={item.placeholder}
                                            />
                                        </Form.Item>
                                    </Col>
                                )
                            })
                        }
                    </Row>
                    <Row>
                        <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                            <Button type="primary" onClick={this.submitClick}>确定</Button>
                            &nbsp; &nbsp;
                            <Button onClick={this.props.onCancel}>取消</Button>
                        </Col>
                    </Row>
                </Form>
            </div>
        );
    }

}

export default userAdd;