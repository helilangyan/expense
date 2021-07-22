import React, { Component } from 'react'
import {Row, Col, message ,List, Button, Modal ,Radio} from 'antd';
import { inject, observer } from 'mobx-react'
import { CheckOutlined } from '@ant-design/icons';

// let defaultItem={}

@inject('appStore') @observer
export default class EnterpriseList extends Component {
  state = {
    datalist: [],
    defaultItem:this.props.appStore.curEnt,
    // checkId:0,
  };
  getData =()=>{
    this.post("/enterprise-user/user-list").then(res => {
      if (res.code === 1) {
        this.setState({
          datalist:res.data,
        })
      } else {
          message.error(res.message)
      }
    });
  }
  setDefault =(e)=>{
    // console.log(e);
    this.state.datalist.map((i)=>{
      i.isDefault=0;
      if(i.id==e.id){
        i.isDefault=1;
        this.setState({
          defaultItem:i,
        })
      }
    })
    this.post("/enterprise-user/enterprise-user/set-default" ,{id:e.id}).then(res => {
      if (res.code === 1) {
        this.getData();
        this.props.appStore.changeCurrent(this.state.defaultItem)
        message.success('设置'+res.msg)
        location.reload()
      } else {
        message.error(res.msg)
      }
    });
  }
  // onSubmit = ()=>{
    
  // }
  componentDidMount(){
    this.getData();
  }
  render() {
    const {datalist} = this.state;

    return (
      <div>
        <List
          grid={{ gutter:20,column:4}}
          className="myEnterList"
          dataSource={datalist}
          renderItem={item => (
            <List.Item onClick={()=>this.setDefault(item)} className={item.isDefault==1?"checked":""}>
              <div className="logo">
                <img src={require("@/assets/userImg/enter1.png")} alt=""/>
                <i className="check"><CheckOutlined /></i>
              </div>
              <p className="name">{item.enterpriseName}</p>
            </List.Item>
          )}
        />
        <Row>
            <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                {/* <Button type="primary" onClick={this.onSubmit}>确定</Button>
                &nbsp;&nbsp;&nbsp; */}
                <Button onClick={this.props.onCancel}>关闭</Button>
            </Col>
        </Row>
      </div>
    )
  }
}
// export function defaultEnter(){
//   return defaultItem
// }
// function setDefaultEnter(item){
//   defaultItem = item
// }