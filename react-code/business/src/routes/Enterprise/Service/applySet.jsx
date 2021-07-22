import React, { Component } from 'react'
import { message, Input, Form, Row, Col, Button, Radio, Select, DatePicker, Upload } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import Icon from '@ant-design/icons';
import moment from 'moment';
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import { Back } from 'gsap';

// import './index.css'

const { Option } = Select;

const layout = {
  labelCol: {
    span: 4,
  },
  wrapperCol: {
    span: 20,
  },
};
const HotSvg = () => (
  <svg t="1625220051048" className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5986" width="20" height="20">
    <path d="M360.555789 955.122526c-55.457684-114.984421-26.004211-181.086316 16.545685-243.065263 46.565053-67.826526 58.772211-135.221895 58.77221-135.221895s36.378947 47.562105 21.989053 121.882948c64.538947-72.165053 76.907789-186.421895 67.125895-230.4 146.458947 102.049684 208.842105 323.098947 124.335157 486.80421 448.296421-253.116632 111.535158-631.700211 53.032422-674.681263 19.402105 42.981053 22.986105 114.984421-16.249264 150.069895-66.667789-252.820211-231.828211-304.720842-231.82821-304.720842 19.536842 130.506105-71.006316 272.976842-157.965474 379.742316-3.287579-51.873684-6.467368-87.956211-33.899789-137.701053-6.197895 94.450526-78.632421 171.627789-98.034527 266.186105-26.300632 128.215579 19.833263 221.938526 196.176842 321.104842z" p-id="5987" fill="#D81E06"></path>
  </svg>
);
const HotIcon = props => <Icon component={HotSvg} {...props} />;

@inject('appStore') @observer
export default class enterpriseSet extends Component {
  state = {
    data: toJS(this.props.appStore.curEnt),
    formData: {},
    cardlist: [],
  }
  formRef = React.createRef()

  componentDidMount() {
    this.setValue(this.props.defaultData)
    this.getCards()
  }

  //银行卡
  getCards = () => {
    let obj = {
      limit: 100,
      page: 1,
      enterpriseId: this.state.data.enterpriseId
    }
    this.post("/enterprise/bank/list", obj).then(res => {
      if (res.code === 1) {
        this.setState({
          cardlist: res.data.data,
        })
      }
    });
  }

  onChange = (e, v) => {
    console.log(v);
    v.number = this.numLimit(v.number)
    this.setState({ formData: v, })
    this.formRef.current.setFieldsValue(v)
  }
  numLimit = (num) => {
    num = num ? num.toString().replace(/[^\d.]/g, '') : ''
    num = num <5? 5:num
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

    this.formRef.current.validateFields().then(() => {
      this.props.onSubmit(subData)
      this.onCancel()
    }).catch((errorInfo) => {
      message.error("请输入完整信息");
    })
  }

  render() {
    const { cardlist, formData, } = this.state;
    // const validateLeastNum=(_,value) => {
    //   return new Promise((resolve, reject) =>{
    //     if (!value) {
    //       reject(new Error('请输入购买用户数量'))
    //     }
    //     else{
    //       if(value !== ''){
    //           if (value>=5) {
    //             resolve();
    //           } else {
    //             reject(new Error('起购量不小于5'))
    //           }
    //       }
    //       resolve();
    //     }
    //   })
    // }
    return (
      <div>
        <Form {...layout} onValuesChange={this.onChange} ref={this.formRef}>
          <Row>
            <Col span={23}>
              <Form.Item label="购买用户数量" style={{marginBottom:'0'}}>
                <Form.Item name="number" style={{ display: 'inline-block', width: '60%' }}>
                  <Input />
                </Form.Item>
                <Form.Item style={{ display: 'inline-block', paddingLeft: '8px' }} className="form-mark">
                  (起购量：5)
                </Form.Item>
              </Form.Item>

              <Form.Item label="购买类型" >
                <Form.Item name="type" style={{marginBottom:'0'}}>
                  <Radio.Group >
                    <Radio.Button value="b">初始</Radio.Button>
                    <Radio.Button value="c">增量</Radio.Button>
                    <Radio.Button value="d">续费</Radio.Button>
                  </Radio.Group>
                </Form.Item>
                <Form.Item style={{ display: 'inline-block', paddingLeft: '8px',marginBottom:'0' }} className="form-mark">
                  (第一次购买智能选择初始，第二次及以后可以选择增量和续费)
                </Form.Item>
              </Form.Item>

              <Form.Item label="购买时长" style={{marginBottom:'0'}}>
                <Form.Item name="vehicle" style={{ display: 'inline-block', width: '60%' }}>
                  <Select>
                    <Option value="1">一个月</Option>
                    <Option value="3">三个月</Option>
                    <Option value="6">六个月</Option>
                    <Option value="12">一年</Option>
                  </Select>
                </Form.Item>
                <Form.Item style={{ display: 'inline-block', paddingLeft: '8px' }} className="form-mark">
                  (2021-03-18至2022-03-18)
                </Form.Item>
              </Form.Item>

              <Form.Item label="金额" className="sales-promotion">
                <span className="num">$ 500.00</span>
                <span className="normal">原价：$ 800.00</span>
                <span className="discount"> <HotIcon style={{ fontSize: '14px' }} />限时活动：购买1年立减$300、购买6个月立减$150</span>
              </Form.Item>

              <Form.Item name="card" label="付款方式">
                <Select style={{width:'60%'}}>
                  {
                    !cardlist ? null : cardlist.map((item) => {
                      return <Option key={item.id}
                        value={item.id}>{item.bankName} {item.bankAccount.slice(0, 4)} ******** {item.bankAccount.slice(-4)}</Option>
                    })
                  }
                </Select>
              </Form.Item>
            </Col>
          </Row>
        </Form>
        <Row>
          <Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
            <Button type="primary" onClick={this.onSubmit}>立即支付</Button>
            &nbsp;&nbsp;&nbsp;
            <Button onClick={this.props.onCancel}>取消</Button>
          </Col>
        </Row>
      </div>
    )
  }
}
