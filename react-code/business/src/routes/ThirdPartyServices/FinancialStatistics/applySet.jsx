import React, { Component } from 'react'
import { message, Input, Form, Row, Col, Button, Radio, Select, DatePicker, Upload } from 'antd';
import { UploadOutlined, EnvironmentOutlined } from '@ant-design/icons';
import moment from 'moment';
import { Back } from 'gsap';

// import './index.css'

const { Option } = Select;

const layout = {
  labelCol: {
    span: 6,
  },
  wrapperCol: {
    span: 17,
  },
};
export default class enterpriseSet extends Component {
  state = {
    // entId: this.props.data.enterpriseId,
    formData: {},
    deptlist: [],
    cardlist: [],
    projlist: [],
  }
  formRef = React.createRef()

  componentDidMount() {
    this.setValue(this.props.defaultData)
    this.getDeptList()
    this.getCardList()
    this.getProjList()
  }
  getDeptList = () => {
    this.post(`/enterprise/department/findByEnterpriseId`, {
      enterpriseId: this.state.EnterpriseId,
    }).then(res => {
      if (res.code === 1) {
        this.setState({
          deptlist: res.data,
        })
      } else {
        // message.error(res.message)
      }
    });
  }
  getCardList = () => {
    let obj = {
      limit: 100,
      page: 1,
      enterpriseId: this.state.entId
    }
    this.post("/enterprise/bank/list", obj).then(res => {
      if (res.code === 1) {
        this.setState({
          cardlist: res.data.data,
        })
      } else {
        // message.error(res.message)
      }
    });
  }
  getProjList = () => {
    let obj = {
      limit: 100,
      page: 1,
      enterpriseId: this.state.entId
    }
    this.post("/enterprise/project/findByEnterpriseId", obj).then(res => {
      if (res.code === 1) {
        this.setState({
          projlist: res.data.data,
        })
      } else {
        // message.error(res.message)
      }
    });
  }

  onChange = (e, v) => {
    console.log(v);
    v.money = this.numLimit(v.money)
    this.setState({ formData: v, })
    this.formRef.current.setFieldsValue(v)
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
      data.tags ? data.tags = data.tags : data.tags = []
      this.setState({ formData: data })
      this.formRef.current.setFieldsValue(data)
    }
  }
  onSubmit = () => {
    const { formData, entId } = this.state
    const { defaultData } = this.props
    let subData = { ...defaultData, ...formData };
    subData.enterpriseId = entId;
    subData.giveDate ? subData.giveDate = subData.giveDate.format('YYYY-MM-DD') : ''

    this.formRef.current.validateFields().then(() => {
      this.props.onSubmit(subData)
      this.onCancel()
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }

  render() {
    const { deptlist, cardlist, projlist, formData, } = this.state;
    const { type } = this.props;

    const props = {
      action: 'https://www.mocky.io/v2/5cc8019d300000980a055e76',
      onChange: this.handleChange,
      multiple: true,
    };
    return (
      <div>
        <Form {...layout} onValuesChange={this.onChange} ref={this.formRef}>
          <Row>
            <Col span={23}>
              <Form.Item name="vehicle" label="选择代填报企业" rules={[{ required: true, message: '请选择' }]}>
                <Select>
                  {
                    !projlist ? null : projlist.map((item) => {
                      return <Option key={item.id} value={item.id}>{item.projectName}</Option>
                    })
                  }
                </Select>
              </Form.Item>
              <Form.Item name="vehicle" label="选择申请类型" rules={[{ required: true, message: '请选择' }]}>
                <Select>
                  <Option value="飞机">报销申请</Option>
                </Select>
              </Form.Item>
              <Form.Item name='explain' label="详细说明" rules={[{ required: true, message: '请输入说明' }]}>
                <Input.TextArea placeholder='请输入说明' />
              </Form.Item>
              <Form.Item name='giveDate' label="最迟完成日期" rules={[{ required: true, message: '请选择' }]}>
                <DatePicker style={{ width: '100%' }} format='YYYY-MM-DD' />
              </Form.Item>
              <Form.Item name="file" label="上传附件" >
                <Upload {...props} fileList={this.state.fileList}>
                  <Button icon={<UploadOutlined />}>Upload</Button>
                </Upload>
              </Form.Item>
            </Col>
          </Row>
        </Form>
        <Row>
          <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
            <Button type="primary" onClick={this.onSubmit}>保存并提交</Button>
            &nbsp;&nbsp;&nbsp;
            <Button onClick={this.props.onCancel}>存为草稿</Button>
          </Col>
        </Row>
      </div>
    )
  }
}
