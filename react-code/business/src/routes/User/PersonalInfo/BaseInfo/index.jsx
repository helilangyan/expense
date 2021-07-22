import React, { Component } from 'react'
import { Row, Col, Avatar, Form, Input, Button, message, Modal, Upload } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { inject, observer } from 'mobx-react'
import { CheckOutlined } from '@ant-design/icons';
import axios from 'axios'
import { baseURL, timeout, basicUrl } from '@/service/url'
import { isAuthenticated, logout } from "@/utils/Session";

// import { post,get } from '../../../service/requestHttp';

import PhoneSet from './phoneSet'
import EmailSet from './emailSet'
import PasswordSet from './passwordSet'

const layout = {
    layout: "vertical",
    labelCol: {
        xl:{span: 8},
        xxl:{span: 6,}
    },
    wrapperCol: {
        xl:{span: 16},
        xxl:{span: 18,}
    },
};
@inject('appStore') @observer
export default class BaseInfo extends Component {
    state = {
        // data:toJS(this.props.appStore.curEnt),
        formdata: {},
        phoneModal: false,
        emailModal: false,
        passwordModal: false,
        isModify: false,
        fileID: '',
        imgId:0
    }
    formInfo = React.createRef()

    componentDidMount() {
        this.getData()
        this.getImg()
    }

    getData = () => {
        this.post("/userinfo/findById").then(res => {
            if (res.code === 1) {
                this.setState({ formdata: res.data })
                this.props.appStore.saveUser(res.data)
                res.data.password = res.data.password.substr(0, 10)
                this.formInfo.current.setFieldsValue(res.data);
            } else {
                message.error(res.massage)
            }
        });
    }
    onChange = (e, v) => {
        this.setState({
            formdata: { ...v },
        })
    }
    onCancel = () => {
        this.setState({
            phoneModal: false,
            emailModal: false,
            passwordModal: false
        })
    }
    modify = () => {
        this.setState({
            isModify: !this.state.isModify,
        })
    }
    changeInfo = (e) => {
        switch (e) {
            case 'name':
                this.setState({ isModify: !this.state.isModify, })
                break;
            case 'phone':
                this.setState({ phoneModal: true })
                break;
            case 'email':
                this.setState({ emailModal: true })
                break;
            case 'phone':
                this.setState({ phoneModal: true })
                break;
            case 'password':
                this.setState({ passwordModal: true })
                break;
        }
    }
    getImg = (e) => {
        this.post("/userinfo-img/find-userid" ).then(res => {
            if (res.code === 1) {
                if(res.data){
                    this.setState({
                        imgId:res.data.id
                    })
                    this.getImgSrc(res.data.fileId)
                  }
            } else {
            }   
        });
    }
    saveImg = (e) => {
        this.post("/userinfo-img/insert",{
            userId:this.state.formdata.id,
            fileId:this.state.fileID,
            id:this.state.imgId
        },'application/json' ).then(res => {
            if (res.code === 1) {
                message.success(res.msg)
            } else {
                message.error('保存失败，请重试')
            }   
        });
    }
    async getImgSrc(fileId) {
        var res = await axios({
          url: basicUrl + '/expense-file-server/file/openFile',
          method: 'post',
          headers: {
            'Authorization': isAuthenticated()
          },
          params: {
            id: fileId
          },
          responseType: "arraybuffer"
        });
        console.log(res);
        this.setState({
          imageUrl: 'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((data, byte) => data + String.fromCharCode(byte), ''))
        })
      }
    onSubmit = (type, data) => {
        const { formdata } = this.state;
        switch (type) {
            case 'name':
                let obj = {
                    firstName: formdata.firstName,
                    lastName: formdata.lastName,
                }
                this.post("/userinfo/update-username", obj,).then(res => {
                    if (res.code === 1) {
                        message.success("修改" + res.msg)
                        this.getData()
                        this.modify()
                    } else {
                        message.error(res.data)
                    }
                });
                break;
            case 'phone':
                this.post("/userinfo/update-phone", data).then(res => {
                    if (res.code === 1) {
                        message.success("修改" + res.msg)
                        this.getData()
                    } else {
                        message.error(res.data)
                    }
                });
                break;
            case 'email':
                this.post("/userinfo/update-email", data).then(res => {
                    if (res.code === 1) {
                        message.success("修改" + res.msg)
                        this.getData()
                    } else {
                        message.error(res.data)
                    }
                });
                break;
            case 'password':
                console.log(data);
                this.post("/userinfo/update-password", data).then(res => {
                    if (res.code === 1) {
                        message.success("修改" + res.msg)
                        this.getData()
                    } else {
                        message.error(res.data)
                    }
                });
                break;
        }
    }
    handleChange2 = info => {
        console.log(info);
        if (info.file.status === 'uploading') {
            this.setState({ loading: true });
            return;
        }
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            this.getBase64(info.file.originFileObj, imageUrl =>
                this.setState({
                    imageUrl,
                    loading: false,
                }),
            );
            console.log(info.file.response.data.id);
            this.setState({
                fileID: info.file.response.data.id
            })
        }
    };
    beforeUpload = (file) => {
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
    getBase64 = (img, callback) => {
        const reader = new FileReader();
        reader.addEventListener('load', () => callback(reader.result));
        reader.readAsDataURL(img);
    }

    render() {
        const { isModify, formdata, imageUrl } = this.state;
        return (
            <div style={{ marginLeft: '30px' }}>
                <Row>
                    <Col xxl={{ span: 2 }} xl={{ span: 4 }} style={{textAlign:'center',marginRight:'15px'}}>
                        <Upload
                            name="file"
                            listType="picture-card"
                            className="avatar-uploader userInfo"
                            style={{marginRight:'10px'}}
                            showUploadList={false}
                            headers={{ Authorization: isAuthenticated() }}
                            action={basicUrl + "/expense-file-server/file/upload"}
                            beforeUpload={this.beforeUpload}
                            onChange={this.handleChange2}
                        >
                            {imageUrl ? <img src={imageUrl} alt="logo"
                            style={{ width: '100%' }} /> : <Avatar size={64} icon={<UserOutlined />} />}
                        </Upload>
                        <Button type="default" size="small" onClick={this.saveImg} >保存头像</Button>
                    </Col>
                    <Col xxl={{ span: 10 }} xl={{ span: 17 }}>
                        <Form {...layout} name="nest-messages"
                            className="user-info"
                            ref={this.formInfo}
                            onValuesChange={this.onChange}>
                            {
                                !isModify ?
                                    <Form.Item label="姓名">
                                        <Form.Item name='firstName' noStyle>
                                            <Input
                                                style={{ width: '30%', marginRight: '7px' }}
                                                readOnly
                                                placeholder='First name' />
                                        </Form.Item>
                                        <Form.Item name='lastName' noStyle>
                                            <Input style={{ width: '30%' }} readOnly
                                                placeholder='Last name' />
                                        </Form.Item>
                                        <Button type="text" onClick={() => this.changeInfo('name')}>编辑</Button>
                                    </Form.Item> :

                                    <Form.Item label="姓名">
                                        <Form.Item name='firstName' noStyle>
                                            <Input
                                                style={{ width: '30%', marginRight: '7px' }}
                                                placeholder='First name' />
                                        </Form.Item>
                                        <Form.Item name='lastName' noStyle>
                                            <Input style={{ width: '30%' }}
                                                placeholder='Last name' />
                                        </Form.Item>
                                        <Button type="text" onClick={() => this.onSubmit('name')}>保存</Button>
                                    </Form.Item>
                            }
                            <Form.Item label="手机号码">
                                <Form.Item
                                    name="phone"
                                    noStyle
                                >
                                    <Input placeholder='手机号码' readOnly />
                                </Form.Item>
                                <Button type="text"
                                    onClick={() => this.changeInfo('phone')}>{formdata.phone ? '更换手机号码' : '绑定手机号码'}</Button>
                            </Form.Item>
                            <Form.Item label="邮箱">
                                <Form.Item name="email" noStyle>
                                    <Input placeholder='绑定邮箱' readOnly />
                                </Form.Item>
                                <Button type="text"
                                    onClick={() => this.changeInfo('email')}>{formdata.email ? '更换邮箱' : '绑定邮箱'}</Button>
                            </Form.Item>
                            <Form.Item label="登录密码">
                                <Form.Item
                                    name="password"
                                    rules={[{ required: true, message: 'Please input your password!' }]}
                                    noStyle
                                >
                                    <Input.Password visibilityToggle={false} placeholder='登录密码' readOnly />
                                </Form.Item>
                                <Button type="text" onClick={() => this.changeInfo('password')}>修改密码</Button>
                            </Form.Item>
                        </Form>
                    </Col>
                </Row>
                <Modal
                    width="25%"
                    title="更改手机号"
                    footer={null}
                    visible={this.state.phoneModal}
                    onCancel={this.onCancel}
                    destroyOnClose={true}
                >
                    <PhoneSet onSubmit={this.onSubmit} onCancel={this.onCancel} defaultData={formdata}></PhoneSet>
                </Modal>
                <Modal
                    width="25%"
                    title="更改邮箱"
                    footer={null}
                    visible={this.state.emailModal}
                    onCancel={this.onCancel}
                    destroyOnClose={true}
                >
                    <EmailSet onSubmit={this.onSubmit} onCancel={this.onCancel} defaultData={formdata}></EmailSet>
                </Modal>
                <Modal
                    width="25%"
                    title="修改密码"
                    footer={null}
                    visible={this.state.passwordModal}
                    onCancel={this.onCancel}
                    destroyOnClose={true}
                >
                    <PasswordSet onSubmit={this.onSubmit} onCancel={this.onCancel} defaultData={formdata}></PasswordSet>
                </Modal>
            </div>
        )
    }
}
