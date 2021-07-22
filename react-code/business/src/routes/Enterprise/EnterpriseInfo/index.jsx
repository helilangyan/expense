import React, { Component } from 'react'
import { Card, Row, Col, Form, Input, Button, Modal, message, Upload } from 'antd';
import { CheckCircleOutlined, DoubleRightOutlined, PlusOutlined, LoadingOutlined } from '@ant-design/icons';
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import axios from 'axios'
import { isAuthenticated, logout } from "@/utils/Session";
import { baseURL, timeout, basicUrl } from '@/service/url'
import VerifyInput from './verify';
import '../index.css'

const layout = {
    // layout:"vertical",
    labelCol: {
        lg:{span: 6},
        xxl:{span: 3},
    },
    wrapperCol: {
        lg:{span: 15},
        xxl:{span: 18},
    },
};

@inject('appStore') @observer
export default class Enterprise extends Component {
    state = {
        data: toJS(this.props.appStore.curEnt),
        loading: false,
        dataList: [],
        setModal: false,
        formData: {},
        fileID: '',
        valiStatus: '',
        valiText: ''
    }
    formRef = React.createRef()

    componentDidMount() {
        this.getData();
    }

    getData = () => {
        const { data } = this.state
        if (data.id) {
            this.get(`/enterprise/` + data.enterpriseId).then(res => {
                if (res.code === 1) {
                    this.setState({
                        data: res.data,
                    })
                    if (res.data.fileId) {
                        this.getImgSrc(res.data.fileId);
                    }
                    this.formRef.current.setFieldsValue(res.data)
                } else {
                    // message.error(res.message)
                }
            })

        }
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
    handleChange = (e, v) => {
        v.taxCode = this.numLimit(v.taxCode)
        this.setState({ formData: v, })
        this.formRef.current.setFieldsValue(v)
    }
    // 限制输入数字和小数点
    numLimit = (num) => {
        return num = num ? num.toString().replace(/[^\d]/g, '') : ''
    }
    // handleChange = (event) => {
    //     console.log(event);
    //     //   let e = event.target;
    //     //   let List = this.state.data;
    //     //   for (var item in List) {
    //     //     if (e.name == item) {
    //     //       List[item] = e.value
    //     //     }
    //     //   }
    //     //   this.setState({ data: List });
    // }
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
    handleSubmit = () => {
        let SubData = this.state.data;
        SubData.fileId = this.state.fileID
        console.log(SubData);
        axios({
            headers: {
                'Content-Type': 'application/json',
                'Authorization': isAuthenticated()
            },
            method: 'post',
            url: baseURL + '/enterprise/insert',
            data: this.state.data
        }).then((res) => {
            if (res.data.code === 1) {
                message.success('保存' + res.data.msg)
                // this.handleCancel()
            } else {
                message.error('保存' + res.data.msg + '：' + res.data.data)
            }
        })
    }
    //打开认证对话框
    showModal = () => {
        this.setState({
            setModal: true,
        })
    }
    handleCancel = () => {
        this.setState({
            setModal: false,
        });
    };
    changeStatus=()=>{
        this.setState({
            valiStatus:'validating',
            valiText:'正在验证税号'
        });
    }
    render() {
        const { loading, data, imageUrl } = this.state
        const uploadButton = (
            <div>
                {loading ? <LoadingOutlined /> : <PlusOutlined />}
            </div>
        );
        const validateTaxCode =async (_, value) => {
            console.log(value);
            return new Promise((resolve, reject) => {
                if (!value) {
                    this.setState({
                        valiStatus:'',
                        valiText:''
                    });
                    reject()
                }
                else {
                    if (value !== '') {
                        this.get("/enterprise/check-tax-code/" + value).then(res => {
                            if (res.code === 1 && res.data == 0) {
                                this.setState({valiStatus:'success',valiText:''})
                                resolve();
                            } else if(res.code === 1 && res.data == 1) {
                                this.setState({valiStatus:'error',valiText:'税号重复'})
                                reject()
                            }else{
                                this.setState({valiStatus:'error',valiText:'验证失败'})
                                reject()
                            }
                        });
                    }
                    resolve();
                }
            })
        }
        return (
            !data ? null :
                <div className="headBox">
                    <Row>
                        <Col span='6'>
                            企业信息设置
                        </Col>
                    </Row>
                    <Card className="contentBox">
                        <Row>
                            <Col span={12}>
                                <Form {...layout} name="nest-messages" className="entInfo"
                                    onValuesChange={this.handleChange} ref={this.formRef}>
                                    <Form.Item
                                        label="是否认证">
                                        <div className={data.status == 1 ? "isveri veried" : "isveri"}>
                                            <CheckCircleOutlined />
                                            <span>{data.status == 1 ? ' 已认证' : ' 未认证'}</span>
                                        </div>
                                        {
                                            data.status == 1 ? '' :
                                                <Button type="text" className="verify"
                                                    onClick={this.showModal}>立即认证<DoubleRightOutlined /></Button>
                                        }
                                    </Form.Item>
                                    <Form.Item label="企业类型">
                                        <Form.Item noStyle >
                                            <Input disabled
                                                placeholder='请输入企业类型'
                                                value={data.type == 1 ? "普通企业" :
                                                    (data.type == 2 ? "代账企业" :
                                                        (data.type == 3 ? "其他企业" : ''))} />
                                        </Form.Item>
                                        <span className="note">不可更改</span>
                                    </Form.Item>
                                    <Form.Item label="企业名称" name='enterpriseName'>
                                        <Input
                                            placeholder='请输入企业名称'
                                            value={data.enterpriseName}
                                            onChange={this.handleChange} />
                                    </Form.Item>
                                    <Form.Item
                                        label="企业地址" name='address'>
                                        <Input
                                            placeholder='请输入企业地址'
                                            value={data.address}
                                            onChange={this.handleChange} />
                                    </Form.Item>
                                    <Form.Item name='logo' label="企业LOGO">
                                        <Form.Item className="logo">
                                            <Upload
                                                name="file"
                                                listType="picture-card"
                                                className="avatar-uploader"
                                                showUploadList={false}
                                                headers={{ Authorization: isAuthenticated() }}
                                                action={basicUrl + "/expense-file-server/file/upload"}
                                                beforeUpload={this.beforeUpload}
                                                onChange={this.handleChange2}
                                            >
                                                {imageUrl ? <img src={imageUrl} alt="logo"
                                                    style={{ height: '100%' }} /> : uploadButton}
                                            </Upload>
                                        </Form.Item>
                                        <span className="note"
                                            style={{ verticalAlign: '20px' }}>点击更换Logo，尺寸建议200*200</span>
                                    </Form.Item>
                                    <Form.Item label="企业电话" name="tel">
                                        <Input
                                            placeholder='请输入企业电话'
                                            value={data.tel}
                                            onChange={this.handleChange} />
                                    </Form.Item>
                                    <Form.Item label="联系人" name='linkman'>
                                        <Input
                                            placeholder='请输入联系人'
                                            value={data.linkman}
                                            onChange={this.handleChange} />
                                    </Form.Item>
                                    <Form.Item label="税号" name='taxCode'
                                        rules={[{ validator: validateTaxCode }]}
                                        hasFeedback
                                        validateStatus={this.state.valiStatus}
                                        help={this.state.valiText}
                                        className="valiInput">
                                        <Input
                                            placeholder='请输入税号'
                                            value={data.taxCode}
                                            onInput ={this.changeStatus}
                                        />
                                    </Form.Item>
                                    <Form.Item wrapperCol={{ span: 12, offset: 6 }}>
                                        <Button type="primary" onClick={this.handleSubmit}>保存</Button>
                                    </Form.Item>
                                </Form>
                                <Modal
                                    width="30%"
                                    title="企业认证"
                                    footer={null}
                                    visible={this.state.setModal}
                                    onCancel={this.handleCancel}
                                    destroyOnClose={true}
                                >
                                    <VerifyInput submitAdd={this.submitAdd} onCancel={this.handleCancel}></VerifyInput>
                                </Modal>
                            </Col>
                        </Row>
                    </Card>
                </div>

        )
    }
}
