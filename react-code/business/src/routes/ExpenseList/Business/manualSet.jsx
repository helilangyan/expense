import React, { Component } from 'react'
import { message, Input, Form, Row, Col, Button, Radio, Select, DatePicker, Upload, TreeSelect, Modal } from 'antd';
import { InboxOutlined, EnvironmentOutlined } from '@ant-design/icons';
import moment from 'moment';
import axios from 'axios'

import { baseURL, timeout, basicUrl } from '@/service/url'
import { isAuthenticated, logout } from "@/utils/Session";


// import './index.css'
import ProjList from './reportList'


const { Option } = Select;
const {confirm} = Modal;

const { TreeNode, SHOW_PARENT } = TreeSelect;

const layout = {
  labelCol: {
    span: 7,
  },
  wrapperCol: {
    span: 16,
  },
};

export default class enterpriseSet extends Component {
  state = {
    entId: this.props.data.enterpriseId,
    formData: {},
    departmentId: [],
    deptlist: [],
    cardlist: [],
    projlist: [],
    tagslist: [],
    tags: [],
    proj: [],
    typelist: [],
    vehiclelist: [],
    reportlist: [],
    strategyId: 0,
    projModal: false,
    fileID: ''
  }
  formRef = React.createRef()

  componentDidMount() {
    this.getTagsList()
    this.getTypeList()
    this.getDeptList()
    this.getCardList()
    if (this.props.type == 4) {
      this.getVehicleList()
    }
    this.setValue(this.props.defaultData)
  }
  getDeptList = () => {
    this.post(`/enterprise/department/findByEnterpriseId`, {
      enterpriseId: this.state.entId,
    }).then(res => {
      if (res.code === 1) {
        this.setChildren(res.data);
        let dataObj = this.setChildren(res.data);
        this.setState({
          deptlist: dataObj,
        })
      } else {
        // message.error(res.message)
      }
    });
  }
  setChildren(list, parentId = 0) {
    list.forEach(item => {
      item.title = item.departmentName;
      item.key = item.id
      item.value = item.id
    })
    let parentObj = {};
    list.forEach(o => {
      parentObj[o.id] = o;
    })
    if (!parentId) {
      return list.filter(o => !parentObj[o.parentId]).map(o => (o.children = this.setChildren(list, o.id), o));
    } else {
      return list.filter(o => o.parentId == parentId).map(o => (o.children = this.setChildren(list, o.id), o));
    }
  }
  getCardList = () => {
    this.post("/enterprise/bank/list", {
      limit: 100,
      page: 1,
      enterpriseId: this.state.entId
    }).then(res => {
      if (res.code === 1) {
        this.setState({
          cardlist: res.data.data,
        })
      } else {
        // message.error(res.message)
      }
    });
  }
  getTagsList = () => {
    this.post("/enterprise/strategy/user-label", {
      limit: 100,
      page: 1,
      enterpriseId: this.state.entId
    }).then(res => {
      if (res.code === 1) {
        this.setState({
          tagslist: res.data,
        })

      } else {
        // message.error(res.message)
      }
    });
  }

  getTypeList = () => {
    this.post("/enterprise/strategy/user-classify", {
      limit: 100,
      page: 1,
      enterpriseId: this.state.entId
    }).then(res => {
      if (res.code === 1) {
        this.setState({
          typelist: res.data,
        })
      } else {
        // message.error(res.message)
      }
    });
  }
  getVehicleList = () => {
    this.post("/enterprise/strategy/user-vehicle", {
      limit: 100,
      page: 1,
      enterpriseId: this.state.entId
    }).then(res => {
      if (res.code === 1) {
        this.setState({
          vehiclelist: res.data,
        })
      } else {
        // message.error(res.message)
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
  getBase64 = (img, callback) => {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
  }
  onChange = (e, v) => {
    console.log(v);
    v.money = this.numLimit(v.money)
    let data = { ...this.state.formData, ...v };
    this.setState({ formData: data, })
    this.formRef.current.setFieldsValue(v)
  }
  handleChange = info => {
    console.log(info);
    if (info.file.status === 'uploading') {
        this.setState({loading: true});
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
    const isLt10M = file.size / 1024 / 1024 < 10;
    if (!isLt10M) {
      message.error('Image must smaller than 10MB!');
    }
    return isJpgOrPng && isLt10M;
  }
  numLimit = (num) => {
    num = num ? num.toString().replace(/[^\d.]/g, '') : ''
    return Number(num)
  }
  onCancel = () => {
    this.props.onCancel();
  }
  setValue = (data) => {
    if (data) {
      data.giveDate = moment(data.giveDate)
      data.tags ? data.tags = data.tags.split(',') : data.tags = []
      this.setState({ formData: data, departmentId: [{ value: data.departmentId }], proj: data.projectName })
      data.fileId ? this.getImgSrc(data.fileId) : '';
      data.expenseType=parseInt(data.expenseType)
      this.formRef.current.setFieldsValue(data)
    }
  }
  onSubmit = () => {
    const { formData, entId, departmentId, fileID } = this.state
    let subData = { ...this.props.defaultData, ...formData };
    subData.enterpriseId = entId;
    subData.departmentId = departmentId[0].value;
    subData.tags ? subData.tags = subData.tags.toString() : ''
    subData.giveDate ? subData.giveDate = subData.giveDate.format('YYYY-MM-DD') : ''
    subData.fileId = fileID
    console.log(subData);
    this.formRef.current.validateFields().then(() => {
      this.post("/detail/business-expense/check-insert", subData).then(res => {
        if (res.code === 1) {
          if(res.data.isSubmit&&res.data.isSubmit==1){
            if(res.data.message=='0000'){
              this.props.onSubmit(subData)
              this.onCancel()
            }else if(res.data.message=='-0001'){
              this.isContinue("warning","货币类型不匹配，是否继续保存",subData)
            }else if(res.data.message=='-0002'){
              this.isContinue("warning","金额超出限制，是否继续保存",subData)
            }
          }else if(res.data.isSubmit&&res.data.isSubmit==0){
            if(res.data.message=='0000'){
              this.props.onSubmit(subData)
              this.onCancel()
            }else if(res.data.message=='-0001'){
              this.isContinue("danger","货币类型不匹配，请修改后再保存")
            }else if(res.data.message=='-0002'){
              this.isContinue("danger","金额超出限制，请修改后再保存")
            }
          }
        } else {
          message.error("策略验证失败,请重新提交")
        }
    });
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }
  isContinue = (type,data,subData) => {
    const that = this;
    if(type=="warning"){
      confirm({
          title: `${data}`,
          content: '',
          okText: '确定',
          okType: 'warning',
          cancelText: '取消',
          onOk() {
            that.props.onSubmit(subData)
            that.onCancel()
          },
          onCancel() {
              console.log('Cancel');
          },
      });
    }else{
      confirm({
        title: `${data}`,
        content: '',
        okText: '确定',
        okType: 'danger',
        cancelText: '取消',
        onOk() {
        },
        onCancel() {
        },
      });
    }
}
  depChange = (value) => {
    let dept = value.slice(-1)
    this.setState({ departmentId: dept });
  }
  selectProj = () => {
    this.setState({
      projModal: true
    })
  }
  projSubmit = (e) => {
    console.log(e);
    // let proj = this.dedupe([...this.state.proj, ...e]);
    // let arr = e.map(item => item.projectName)
    let form = this.state.formData
    form.projectId = e[0].id
    this.setState({
      formData: form,
      proj: [e[0].projectName],
      projModal: false,
    })
  }
  // 去重
  dedupe = (arr) => {
    return arr.reduce((pre, cur) => {
      let result = pre.some(item => {
        return item.id == cur.id
      })
      return !result ? pre.concat(cur) : pre
    }, [])
  }
  handleCancel = () => {
    this.setState({
      projModal: false
    })
  }
  render() {
    const { deptlist, cardlist, imageUrl, formData, tagslist, typelist, vehiclelist } = this.state;
    const { type } = this.props;

    // const normFile = (e) => {
    //   console.log('Upload event:', e);
    //   if (Array.isArray(e)) {
    //     return e;
    //   }
    //   return e && e.fileList;
    // };
    const tProps = {
      treeData: deptlist,
      value: this.state.departmentId,
      onChange: this.depChange,
      treeCheckable: true,
      treeCheckStrictly: true,
      showCheckedStrategy: 'SHOW_ALL',
      treeDefaultExpandAll: true,
      placeholder: '请选择部门',
      style: {
        width: '100%',
      },
    };
    return (
      <div>
        <Form {...layout} onValuesChange={this.onChange} ref={this.formRef}>
          <Row>
            <Col span={12}>
              <Form.Item name="billType" label="是否计费" rules={[{ required: true, message: '请选择' }]}>
                <Radio.Group initialValues='0'>
                  <Radio value='0'>个人支付</Radio>
                  <Radio value='1'>企业支付</Radio>
                </Radio.Group>
              </Form.Item>
              <Form.Item label="费用名称" name='expenseName' rules={[{ required: true, message: '请输入费用名称' }]}>
                <Input placeholder="请输入费用名称" />
              </Form.Item>
              {
                type == 3 ?
                  <Form.Item label="花费时间" name='workTime' rules={[{ required: true, message: '请输入花费时间' }]}>
                    <Input placeholder="请输入花费时间" suffix="小时" />
                  </Form.Item>
                  : type == 4 ?
                    <Form.Item noStyle>
                      <Form.Item label="出发地点" name='startAddress' rules={[{ required: true, message: '请输入出发地点' }]}>
                        <Input placeholder="请输入出发地点" suffix={<EnvironmentOutlined style={styles.color} />} />
                      </Form.Item>
                      <Form.Item label="到达地点" name='stopAddress' rules={[{ required: true, message: '请输入到达地点' }]}>
                        <Input placeholder="请输入到达地点" suffix={<EnvironmentOutlined style={styles.color} />} />
                      </Form.Item>
                      <Form.Item name="vehicle" label="交通工具" rules={[{ required: true, message: '请选择' }]}>
                        <Select>
                          {
                            !vehiclelist ? null : vehiclelist.map((item) => {
                              return <Option key={item.id} value={item.vehicleName}>{item.vehicleName}</Option>
                            })
                          }
                        </Select>
                      </Form.Item>
                    </Form.Item>
                    : null
              }
              <Form.Item className="multiItem" label="金额" required>
                <Form.Item name="money"
                  rules={[{ required: true }]}
                  style={{ display: 'inline-block', width: 'calc(50% - 8px)', marginRight: '8px' }}
                >
                  <Input placeholder="请输入金额" />
                </Form.Item>
                <Form.Item name="moneyType" rules={[{ required: true, message: '请选择' }]}
                  style={{ width: '50%' }}
                >
                  <Select placeholder="货币类型">
                    <Option value="usd">USD $</Option>
                    <Option value="cny">CNY ¥</Option>
                  </Select>
                </Form.Item>
              </Form.Item>
              <Form.Item name='giveDate' label="发生日期" rules={[{ required: true, message: '请选择' }]}>
                <DatePicker style={{ width: '100%' }} format='YYYY-MM-DD' />
              </Form.Item>
              <Form.Item name="expenseType" label="分类" rules={[{ required: true, message: '请选择' }]}>
                <Select>
                  {
                    !typelist ? null : typelist.map((item) => {
                      return <Option key={item.id} value={item.id}>{item.classifyName}</Option>
                    })
                  }
                </Select>
              </Form.Item>
              <Form.Item label="部门" >
                <TreeSelect {...tProps} />
              </Form.Item>
              <Form.Item label="项目">
                {/* <Select placeholder="请选择项目" onClick={this.selectProj}>
                  {
                    !projlist ? null : projlist.map((item) => {
                      return <Option key={item.id} value={item.id}>{item.projectName}</Option>
                    })
                  }
                </Select> */}
                <Select
                  style={{ width: '100%' }}
                  placeholder="请选择项目"
                  onClick={this.selectProj}
                  open={false}
                  value={this.state.proj}
                >
                </Select>
              </Form.Item>
              {
                formData.billType !== '1' ? null :
                  <Form.Item name="enterpriseBankId" label="公司卡" >
                    <Select placeholder="请选择银行卡">
                      {
                        !cardlist ? null : cardlist.map((item) => {
                          return <Option key={item.id} value={item.id}>{item.bankName} {item.bankAccount.slice(0, 4)} ******** {item.bankAccount.slice(-4)}</Option>
                        })
                      }
                    </Select>
                  </Form.Item>
              }
              <Form.Item name="tags" label="标签" >
                <Select style={{ width: '100%' }} placeholder="请选择标签">
                  {
                    !tagslist ? null : tagslist.map((item) => {
                      return <Option key={item.id} value={item.id.toString()}>{item.labelName}</Option>
                    })
                  }
                </Select>
              </Form.Item>
              <Form.Item name='remark' label="说明">
                <Input.TextArea placeholder='请输入说明' />
              </Form.Item>
            </Col>
            <Col span={11}>
              <Form.Item wrapperCol={{ span: 24 }} style={styles.uploadBg}>
                <Upload.Dragger name="file"
                  action={basicUrl + "/expense-file-server/file/upload"}
                  listType="picture-card"
                  className="avatar-uploader"
                  headers={{ Authorization: isAuthenticated() }}
                  style={{ border: 'none' }}
                  showUploadList={false}
                  beforeUpload={this.beforeUpload}
                  onChange={this.handleChange}>
                  {imageUrl ? <img src={imageUrl} alt="logo" style={{ width: '100%' }} /> :
                    <div>
                      <p className="ant-upload-drag-icon">
                        <InboxOutlined />
                      </p>
                      <p className="ant-upload-text">上传收据</p>
                      <p className="ant-upload-hint">Support for a receipt upload.</p>
                    </div>
                  }
                </Upload.Dragger>
              </Form.Item>
            </Col>
          </Row>
        </Form>
        <Row>
          <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
            <Button type="primary" onClick={this.onSubmit}>确定</Button>
            &nbsp;&nbsp;&nbsp;
            <Button onClick={this.props.onCancel}>关闭</Button>
          </Col>
        </Row>
        <Modal
          width="30%"
          title="选择项目"
          visible={this.state.projModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
        >
          <ProjList onCancel={this.handleCancel} projSubmit={this.projSubmit} proj={this.state.proj} /> :
        </Modal>
      </div>
    )
  }
}
const styles = {
  addText: {
    lineHight: '30px',
  },
  color: {
    color: '#1890FF'
  },
  uploadBg: {
    background: '#FAFAFA',
    height: '96.4%',
    borderColor: '#D9D9D9',
    borderWidth: '1px',
    borderStyle: 'dashed',
    display: 'flex',
    alignItems: 'center',
  },

}
