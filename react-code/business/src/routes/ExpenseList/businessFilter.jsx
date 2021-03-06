import React, { Component } from 'react'
import { message, Input, Form, Row, Col, Button, Radio, Select, DatePicker, Upload } from 'antd';
import { InboxOutlined, EnvironmentOutlined } from '@ant-design/icons';
import moment from 'moment';
// import {isAuthenticated,logout} from "@/utils/Session";
// import {basicUrl,timeout} from '@/service/url'
import PubSub from 'pubsub-js'
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'

const { Option } = Select;

@inject('appStore') @observer
export default class enterpriseSet extends Component {
  state = {
    data: toJS(this.props.appStore.curEnt),
    formData: {
      startTime:'',
      endTime:'',
      expenseType:'',
      expenseName:'',
      tags:'',
    },
    deptlist: [],
    cardlist: [],
    projlist: [],
  }
  formRef = React.createRef()

  componentDidMount() {
    this.getTagsList()
    this.getTypeList()
  }
  getTagsList = () => {
    this.post("/enterprise/strategy/user-label", {
      limit: 100,
      page: 1,
      enterpriseId: this.state.data.enterpriseId
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
      enterpriseId: this.state.data.enterpriseId
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
  onChange = (e, v) => {
    console.log(v);
    this.setState({formData: {...v},})
  }
  onCancel = () => {
    this.props.onCancel();
  }
  onSubmit = () => {
    let subData = this.state.formData;
    subData.startTime ? subData.startTime = subData.startTime.format('YYYY-MM-DD')+' 00:00:00' : ''
    subData.endTime ? subData.endTime = subData.endTime.format('YYYY-MM-DD')+' 00:00:00' : ''
    this.formRef.current.validateFields().then(() => {
      console.log(subData);
      PubSub.publish('filterData',subData)
      this.props.onCancel()
    }).catch((errorInfo) => {
      message.error("?????????????????????");
    })
    
  }
  render() {
    const { tagslist, typelist, } = this.state;
    const layout = {
      labelCol: {
        span: 6,
      },
      wrapperCol: {
        span: 17,
      },
    };
    const validateStartDate = (_, value) => {
			return new Promise((resolve, reject) => {
				if (!value) {
					reject(new Error('?????????????????????'))
				}
				else {
					if (value !== '' && this.state.formData.endTime) {
						if (value.unix() <= moment(this.state.formData.endTime).unix()) {
							resolve();
						} else {
							reject(new Error('????????????????????????????????????'))
						}
					}
					resolve();
				}
			})
		}
		const validateEndDate = (_, value) => {
			return new Promise((resolve, reject) => {
				if (!value) {
					reject(new Error('?????????????????????'))
				}
				else {
					if (value !== '' && this.state.formData.startTime) {
						if (value.unix() >= moment(this.state.formData.startTime).unix()) {
							resolve();
						} else {
							reject(new Error('????????????????????????????????????'))
						}
					}
					resolve();
				}
			})
		}
    return (
      <div>
        <Form hideRequiredMark className="filterItem" {...layout} ref={this.formRef} onValuesChange={this.onChange}>
          <Row gutter={16} >
            <Col span={24}>
              <Form.Item label="????????????" style={{width:'100%',marginBottom:'0'}}>
                <Form.Item  name="startTime" style={{display:'inline-block',width:'45%'}} rules={[{ required: true, validator: validateStartDate }]}>
                  <DatePicker/>
                </Form.Item>
                <Form.Item  name="endTime" style={{display:'inline-block',width:'45%'}} rules={[{ required: true, validator: validateEndDate }]}>
                 <DatePicker/>
                </Form.Item>
              </Form.Item>
              <Form.Item name="expenseType" label="????????????">
                <Select placeholder="???????????????">
                  {
                    !typelist ? null : typelist.map((item) => {
                      return <Option key={item.id} value={item.id}>{item.classifyName}</Option>
                    })
                  }
                </Select>
              </Form.Item>
              <Form.Item name="tags" label="????????????">
                <Select mode="tags" placeholder="???????????????">
                  {
                    !tagslist ? null : tagslist.map((item) => {
                      return <Option key={item.id} value={item.id.toString()}>{item.labelName}</Option>
                    })
                  }
                </Select>
              </Form.Item>
              {/* <Form.Item name="status" label="????????????">
                <Select>
                  <Option value="?????????">??????</Option>
                  <Option value="1">??????</Option>
                  <Option value="2">?????????</Option>
                  <Option value="3">?????????</Option>
                </Select>
              </Form.Item> */}
            </Col>
          </Row>
        </Form>
        <Row>
          <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
            <Button type="primary" onClick={this.onSubmit}>??????</Button>
                &nbsp;&nbsp;&nbsp;
            <Button onClick={this.onCancel}>??????</Button>
          </Col>
        </Row>
      </div>
    )
  }
}