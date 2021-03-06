import React, { Component } from 'react'
import { message, Input, Form, Row, Col, Button, Radio, Select, DatePicker, Upload } from 'antd';
import { InboxOutlined, EnvironmentOutlined } from '@ant-design/icons';
import moment from 'moment';
import { Back } from 'gsap';
import {isAuthenticated,logout} from "@/utils/Session";
import {basicUrl,timeout} from '@/service/url'


// import './index.css'

const { Option } = Select;

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
  getProjList = () => {
    this.post("/enterprise/project/findByEnterpriseId", {
      limit: 100,
      page: 1,
      enterpriseId: this.state.entId
    }).then(res => {
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
    this.setState({formData: v,})
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
    let subData = { ...defaultData,...formData };
    subData.enterpriseId = entId;
    subData.giveDate ? subData.giveDate = subData.giveDate.format('YYYY-MM-DD') : ''

    this.formRef.current.validateFields().then(() => {
      this.props.onSubmit(subData)
      setTimeout(this.onCancel, 1000)
    }).catch((errorInfo) => {
      message.error("?????????????????????");
    })
  }

  render() {
    const { deptlist, cardlist, projlist, formData, } = this.state;
    const { type } = this.props;

    const normFile = (e) => {
      console.log('Upload event:', e);

      if (Array.isArray(e)) {
        return e;
      }

      return e && e.fileList;
    };
    const uploadprops = {
      name: 'file',
      action: basicUrl+'/expense-file-server/file/upload',
      // action: 'https://www.mocky.io/v2/5cc8019d300000980a055e76',
      multiple: true,
      headers: {
        'Authorization': isAuthenticated(),
        'Content-Type': "multipart/form-data",
      },
    }
    return (
      <div>
        <Form {...layout} onValuesChange={this.onChange} ref={this.formRef}>
          <Row>
            <Col span={12}>
              <Form.Item name="billType" label="????????????" rules={[{ required: true, message: '?????????' }]}>
                <Radio.Group>
                  <Radio value='0'>??????</Radio>
                  <Radio value='1'>??????</Radio>
                </Radio.Group>
              </Form.Item>
              <Form.Item label="????????????" name='expenseName' rules={[{ required: true, message: '?????????' }]}>
                <Input placeholder="?????????????????????" />
              </Form.Item>
              <Form.Item className="multiItem" wrapperCol={{ span: 23 }}>
                <Form.Item name="money"
                  rules={[{ required: true }]} label="??????"
                  labelCol={{span: 11}}
                  wrapperCol={{span: 12}}
                  getValueFromEvent={
                    (event) => {
                      return event.target.value.replace(/\D/g,'')
                    }
                  }
                >
                  <Input placeholder="???????????????" />
                </Form.Item>
                <Form.Item name="moneyType" rules={[{ required: true, message: '?????????' }]}
                  style={{ width: '38%'}}
                >
                  <Select placeholder="????????????">
                    <Option value="usd">USD $</Option>
                    <Option value="cny">CNY ??</Option>
                  </Select>
                </Form.Item>
              </Form.Item>
              <Form.Item name='giveDate' label="????????????" rules={[{ required: true, message: '?????????' }]}>
                <DatePicker style={{ width: '100%' }} format='YYYY-MM-DD' />
              </Form.Item>
              <Form.Item name="expenseType" label="??????" rules={[{ required: true, message: '?????????' }]}>
                <Select>
                  <Option value="?????????">?????????</Option>
                  <Option value="?????????">?????????</Option>
                  <Option value="?????????">?????????</Option>
                  <Option value="?????????">?????????</Option>
                </Select>
              </Form.Item>
              <Form.Item name="tags" label="??????" >
                <Select mode="tags" style={{ width: '100%' }} placeholder="Tags Mode">
                  <Option value="1">??????1</Option>
                </Select>
              </Form.Item>
              <Form.Item name='explain' label="??????">
                <Input.TextArea placeholder='???????????????' />
              </Form.Item>
            </Col>
            <Col span={11}>
              <Form.Item name="dragger" valuePropName="fileList" getValueFromEvent={normFile} wrapperCol={{ span: 24 }} style={styles.uploadBg}>
                <Upload.Dragger {...uploadprops} style={{ border: 'none' }}>
                  <p className="ant-upload-drag-icon">
                    <InboxOutlined />
                  </p>
                  <p className="ant-upload-text">????????????</p>
                  <p className="ant-upload-hint">Support for a single or bulk upload.</p>
                </Upload.Dragger>
              </Form.Item>
            </Col>
          </Row>
        </Form>
        <Row>
          <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
            <Button type="primary" onClick={this.onSubmit}>??????</Button>
                &nbsp;&nbsp;&nbsp;
            <Button onClick={this.props.onCancel}>??????</Button>
          </Col>
        </Row>
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
    height: '94.4%',
    borderColor: '#D9D9D9',
    borderWidth: '1px',
    borderStyle: 'dashed',
    display: 'flex',
    alignItems: 'center',
  },

}