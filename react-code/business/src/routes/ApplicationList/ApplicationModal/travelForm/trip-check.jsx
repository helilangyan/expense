import React, { Component } from 'react';
import { Row, Col, Button, DatePicker, Form, Input, Space, message, Checkbox } from 'antd';
import moment from 'moment';

const CheckboxGroup = Checkbox.Group;

class tripCheck extends Component {

  state = {
    plainOptions: ['火车', '飞机', '轮船', '出租车', '自驾', '住宿费'],
    tripData: {}

  }
  formRef = React.createRef();

  componentDidMount() {
    this.props.tripData ? this.setDefault() : null
    console.log(this.props.limitTime);
  }
  onSubmit = () => {
    this.formRef.current.validateFields().then(() => {
      // let subdata =this.state.tripData
      // this.props.tripData ? subdata.id = this.props.tripData.id : null
      this.props.addTrip(this.state.tripData);
      this.onCancel()
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }
  setDefault = () => {
    const { tripData } = this.props
    tripData.startTime = moment(tripData.startTime)
    tripData.endTime = moment(tripData.endTime)
    this.setState({ tripData })
    this.formRef.current.setFieldsValue(tripData)
  }
  onChange = (o, v) => {
    v.budget = this.numLimit(v.budget)
    console.log(v);
    this.setState({ tripData: v })
    this.formRef.current.setFieldsValue(v)
  }
  // 限制输入数字和小数点
  numLimit = (num) => {
    return num = num ? num.toString().replace(/[^\d.]/g, '') : ''
  }
  // 格式化时间
  // handleMoment = (obj, prop) => {
  //   let format = obj[prop] ? obj[prop].format('YYYY-MM-DD') : ''
  //   return format
  // }
  onCancel = () => {
    this.props.onCancel();
  }
  render() {
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 },
      },
    }
    const validateStartDate = (_, value) => {
      return new Promise((resolve, reject) => {
        if (!value) {
          reject(new Error('请输入出发日期'))
        }
        else {
          if (value !== '' && this.state.tripData.endTime) {
            if (value.unix() <= moment(this.state.tripData.endTime).unix()) {
              resolve();
            } else {
              reject(new Error('出发日期不能大于到达日期'))
            }
          }
          resolve();
        }
      })
    }
    const validateEndDate = (_, value) => {
      return new Promise((resolve, reject) => {
        if (!value) {
          reject(new Error('请输入到达日期'))
        }
        else {
          if (value !== '' && this.state.tripData.startTime) {
            if (value.unix() >= moment(this.state.tripData.startTime).unix()) {
              resolve();
            } else {
              reject(new Error('到达日期不能小于出发日期'))
            }
          }
          resolve();
        }
      })
    }
    const disabledDate = (current) => {
      let { limitTime } = this.props
      if (limitTime.startTime == null||limitTime.endTime == null) {
        delete limitTime.startTime
        delete limitTime.endTime
      }
      return current < moment(limitTime.startTime) || current > moment(limitTime.endTime)
    }
    const dateMessage =(open)=>{
      if(open){
        let { limitTime } = this.props
        if (limitTime.startTime == null||limitTime.endTime == null) {
          setTimeout(()=>{
            message.error("请先选择时间区间!")
          },200)
        }
      }
    }
    return (
      <div>
        <Form style={{ width: '100%' }} {...formItemLayout} ref={this.formRef} onValuesChange={this.onChange}>
          <Row>
            <Col span={12}>
              <Form.Item label='出发日期' name='startTime' rules={[{ required: true, validator: validateStartDate }]}>
                <DatePicker placeholder='请输入出发日期' style={{ width: '100%' }} disabledDate={disabledDate} onOpenChange={dateMessage} defaultPickerValue={this.props.defaultpanel}/>
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item label='到达日期' name='endTime' rules={[{ required: true, validator: validateEndDate }]}>
                <DatePicker placeholder='请输入到达日期' style={{ width: '100%' }} disabledDate={disabledDate} onOpenChange={dateMessage} defaultPickerValue={this.props.defaultpanel}/>
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item label='出发地' name='startAddress' rules={[{ required: true, message: '请输入出发地!' }]}>
                <Input placeholder='请输入出发地' name='startAddress' />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item label='到达地' name='endAddress' rules={[{ required: true, message: '请输入到达地!' }]}>
                <Input placeholder='请输入到达地' name='endAddress' />
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Col span={24} >
              <Form.Item label='行程项目' name='vehicle' labelCol={{ span: 3 }} wrapperCol={{ span: 21 }}
                rules={[{ required: true, message: '请选择行程项目!' }]}
              >
                <CheckboxGroup options={this.state.plainOptions} />
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Col span={24} >
              <Form.Item label='预估费用' name='budget' labelCol={{ span: 3 }} wrapperCol={{ span: 7 }}
                rules={[{ required: true, message: '请输入预估费用!' }]}
              >
                <Input prefix="$" placeholder='请输入预估费用' />
              </Form.Item>
            </Col>
          </Row>
          <Row>
            <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
              <Space>
                <Button type="primary" onClick={this.onSubmit}>确定</Button>
                <Button onClick={this.onCancel}>取消</Button>
              </Space>
            </Col>
          </Row>
        </Form>
      </div>
    );
  }
}

export default tripCheck;
