import React, { Component } from 'react'
import {message,Input, Form, Row, Col, Button,Radio,Select,DatePicker,Upload} from 'antd';
import { InboxOutlined } from '@ant-design/icons';

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
  state={
    data:{},
    EnterpriseId:this.props.data.enterpriseId,
    deptlist:[],
    cardlist:[],
    projlist:[],
  }
  formRef = React.createRef()

  componentDidMount(){
    this.setState({
      type:this.props.type,
    })
    this.getDeptList()
    this.getCardList()
    this.getProjList()
  }
  getDeptList = ()=>{
    this.post(`/enterprise/department/findByEnterpriseId`, {enterpriseId:this.state.EnterpriseId,}).then(res => {
      if (res.code === 1) {
        this.setState({
          deptlist:res.data,
        })
      } else {
          // message.error(res.message)
      }
    });
  }
  getCardList = ()=>{
    let obj= {
      limit:100,
      page:1,
      enterpriseId:this.state.EnterpriseId
    }
    this.post("/enterprise/bank/list", obj).then(res => {
      if (res.code === 1) {
        this.setState({
          cardlist:res.data.data,
        })
      } else {
          // message.error(res.message)
      }
    });
  }
  getProjList = ()=>{
    let obj= {
      limit:100,
      page:1,
      enterpriseId:this.state.EnterpriseId
    }
    this.post("/enterprise/project/findByEnterpriseId", obj).then(res => {
      if (res.code === 1) {
        this.setState({
          projlist:res.data.data,
        })
      } else {
          // message.error(res.message)
      }
    });
  }

  // onChange= (event)=>{
  //   let e = event.target;
  //   let List= this.state.data;
  //   for (var item in List){
  //     if (e.name==item) {
  //       List[item]=e.value
  //     }
  //   }
  //   console.log(event);
  //   console.log(List);
  //   this.setState({ data: List});
  // }


  onChange = (e,v)=>{
    console.log(v);
    v.giveDate?v.giveDate=v.giveDate.format('YYYY-MM-DD HH:mm:ss'):''
    this.setState({
      data:v,
    })
  }
  onCancel = ()=>{
    this.props.onCancel();
  }
  onSubmit = ()=>{
    const {data} = this.state
    console.log(data);
    this.props.submitAdd(data)
  }

  render() {
    const {deptlist,cardlist,billType,projlist} = this.state;

    const normFile = (e) => {
      console.log('Upload event:', e);
    
      if (Array.isArray(e)) {
        return e;
      }
    
      return e && e.fileList;
    };
    return (
      <div>
        <Row>
          <Col span={24}>
            <Form {...layout} onValuesChange={this.onChange} ref={this.formRef}>
              <Form.Item label="????????????" name='expenseName' rules={[{ required: true ,message:'?????????????????????'}]}>
                <Input />
              </Form.Item>
              <Form.Item name='explain' label="??????">
                <Input.TextArea placeholder='???????????????'/>
              </Form.Item>
              <Form.Item name='giveDate' label="????????????" rules={[{ required: true ,message:'?????????'}]}>
               <DatePicker style={{width:'100%'}}/>
              </Form.Item>
              <Form.Item name="expenseType" label="??????" rules={[{ required: true ,message:'?????????'}]}>
                <Select>
                  <Option value="rmb">?????????</Option>
                  <Option value="stay">?????????</Option>
                  <Option value="traffic">?????????</Option>
                  <Option value="ent">?????????</Option>
                </Select>
              </Form.Item>
              <Form.Item name="departmentId" label="??????" >
                <Select>
                { 
                  !deptlist?null:deptlist.map((item) => {
                    return <Option key={item.id} value={item.id}>{item.departmentName}</Option>
                  })
                }
                </Select>
              </Form.Item>
              <Form.Item name='projectId' label="??????">
                <Select>
                  { 
                    !projlist?null:projlist.map((item) => {
                      return <Option key={item.id} value={item.id}>{item.projectName}</Option>
                    })
                  }
                </Select>
              </Form.Item>
              {
                !billType==1?null:
                <Form.Item name="enterpriseBankId" label="?????????" >
                  <Select>
                    { 
                      !cardlist?null:cardlist.map((item) => {
                        return <Option key={item.id} value={item.id}>{item.bankAccount}</Option>
                      })
                    }
                  </Select>
                </Form.Item>
              }
              <Form.Item name="tags" label="??????" >
                <Select mode="tags" style={{ width: '100%' }} placeholder="Tags Mode">
                  <Option value="1">??????1</Option>
                </Select>
              </Form.Item>
              <Form.Item name="apply" label="????????????" >
                <Select>
                  <Option value="1">??????1</Option>
                </Select>
              </Form.Item>
          </Form>
            </Col>
            </Row>
        <Row>
            <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                <Button type="primary" onClick={this.onSubmit}>??????</Button>
                &nbsp;&nbsp;&nbsp;
                <Button onClick={this.props.onCancel}>??????</Button>
            </Col>
        </Row>
      </div>
    )
  }
}
