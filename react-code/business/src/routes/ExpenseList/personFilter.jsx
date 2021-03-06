import React, { Component } from 'react'
import { message, Input, Form, Row, Col, Button, Radio, Select, DatePicker, Upload } from 'antd';
import { InboxOutlined, EnvironmentOutlined } from '@ant-design/icons';
import moment from 'moment';
import { Back } from 'gsap';
import {isAuthenticated,logout} from "@/utils/Session";
import {basicUrl,timeout} from '@/service/url'


// import './index.css'

const { Option } = Select;

export default class enterpriseSet extends Component {
  state = {
    entId: 0,
    formData: {},
    deptlist: [],
    cardlist: [],
    projlist: [],
  }
  formRef = React.createRef()

  componentDidMount() {
    this.getDeptList()
    this.getCardList()
    this.getProjList()
  }
  getDeptList = () => {
    this.post(`/enterprise/department/findByEnterpriseId`, { enterpriseId: this.state.EnterpriseId, }).then(res => {
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
    this.setState({formData: v,})
  }
  onCancel = () => {
    this.props.onCancel();
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
    const layout = {
      labelCol: {
        span: 6,
      },
      wrapperCol: {
        span: 17,
      },
    };
    return (
      <div>
        <Form hideRequiredMark className="filterItem" {...layout}>
          <Row gutter={16} >
            <Col span={24}>
              <Form.Item label="????????????" style={{width:'100%',marginBottom:'0'}}>
                <Form.Item  name="time" style={{display:'inline-block',width:'45%'}}>
                  <DatePicker/>
                </Form.Item>
                <Form.Item  name="time2" style={{display:'inline-block',width:'45%'}}>
                 <DatePicker/>
                </Form.Item>
              </Form.Item>
              <Form.Item name="expenseType" label="????????????">
                <Select>
                  <Option value="?????????">?????????</Option>
                  <Option value="?????????">?????????</Option>
                  <Option value="?????????">?????????</Option>
                  <Option value="?????????">?????????</Option>
                </Select>
              </Form.Item>
              <Form.Item name="tags" label="????????????">
                <Select mode="tags" placeholder="Tags Mode">
                  <Option value="1">??????1</Option>
                </Select>
              </Form.Item>
              <Form.Item name="type" label="????????????">
                <Select>
                  <Option value="1">Create by Scan</Option>
                  <Option value="2">Create by Manual</Option>
                </Select>
              </Form.Item>
              <Form.Item name="status" label="????????????">
                <Select>
                  <Option value="?????????">??????</Option>
                  <Option value="1">??????</Option>
                  <Option value="2">??????</Option>
                </Select>
              </Form.Item>
              <Form.Item name="founder" label="?????????">
                <Select>
                  <Option value="?????????">??????</Option>
                  <Option value="1">??????</Option>
                  <Option value="2">??????</Option>
                </Select>
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