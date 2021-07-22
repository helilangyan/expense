import React, { Component} from 'react'
import {Row, Col, message ,List, Button, Modal ,Radio} from 'antd';
import { CheckCircleOutlined } from '@ant-design/icons';
import PubSub from 'pubsub-js'
import axios from 'axios'
import {baseURL,timeout,basicUrl} from '@/service/url'
import {isAuthenticated,logout} from "@/utils/Session";

import EnterpriseDetail from './enterpriseDetail'
import EnterpriseSet from './enterpriseSet'
import EnterpriseJoin from './enterpriseJoin'
import ApplyList from './applyList'

// @withrouter
export default class Enterprise extends Component {
  state = {
    loading: true,
    data: [],
    datalist: [],

    showTotal:0,
    pageNum:0,

    visible:false, //详情
    selectModal:false, //选择企业类型
    setModal:false,//创建企业
    addModal:false,//加入企业
    showTable:false,//显示表格

    type:0,

    defaultData:{},

  };

  //获取数据
  getData = () => {
    this.get("/enterprise/my-enterprise").then(res => {
      if (res.code === 1) {
          this.setState({
              datalist:res.data,
              loading: false,
          })
          res.data.forEach((item,index)=>{
            if (item.fileId){
              let src=this.returnImgSrc(item.fileId);
              item.imgSrc= src;
                this.setState({
                    // datalist[index].imgSrc.:res.data,
                })
            }
          })

      } else {
          message.error(res.message)
      }
    });
  }

  componentDidMount(){
    this.getData();
    this.props.childEvevnt(this);
    this.setState({showTable: false})
  }
  // 打开详情
  viewDetail = (e)=>{
    this.get(`/enterprise/`+e.id).then(res => {
      if (res.code === 1) {
        this.setState({
            defaultData:res.data,
            visible: true,
        });
      } else {
          message.error(res.message)
      }
    })
  }
  handleCancel = () => {
    this.setState({
        visible: false,
        selectModal: false,
        setModal: false,
        addModal: false,
        showTable: false,
    });
  };

  // 父组件要触发的事件
  newEnterprise = (i) => {
    switch(i){
      case 1:
      this.setState({
        selectModal: true,
      });
      break;
      case 2:
      this.setState({
        addModal: true,
      });
      break;
      case 3:
      this.setState({
        showTable: !this.state.showTable,
      });
      break;
      default:
      break;
    }
  };
  // 选择企业类型
  onChange = (e)=>{
    // e.persist();
    this.setState({
      type:e.target.value,
    })
  }
  selectEnter = (e)=>{
    this.setState({
      selectModal:false,
      setModal: true,
    });
  }
  submitAdd = (obj)=>{
    console.log(obj);
    axios({
      headers: {
          'Content-Type': 'application/json',
          'Authorization':isAuthenticated()
      },
      method: 'post',
      url: baseURL+'/enterprise/insert',
      data:obj
    }).then((res)=>{
      message.success('操作'+res.msg)
      this.handleCancel()
      this.getData()
    })
  }
  submitJoin = (e)=>{
    this.post("/enterprise-user-apply/apply", {InvitationCode:e}).then(res => {
      if (res.code === 1) {
        message.success("申请"+res.msg)
        setTimeout(()=>{
          this.handleCancel()
        },1000)
      } else {
        message.error("申请"+res.msg + "," + res.data)
      }
    });
  }
    async returnImgSrc(fileId) {
        var res = await axios({
            url: basicUrl+'/expense-file-server/file/openFile',
            method: 'post',
            headers: {
                'Authorization': isAuthenticated()
            },
            params: {
                id: fileId
            },
            responseType: "arraybuffer"
        });
        let src =  'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((data, byte) => data + String.fromCharCode(byte), ''))
        return src
    }

  render() {
    const {datalist,defaultData,showTable,visible,selectModal,setModal,addModal} = this.state;
    return (
      <div>
        {
          showTable?(null):
          <List
            grid={{ gutter:20, md: 4,lg:4,xxl: 7,}}
            className="enterList"
            dataSource={datalist}
            itemLayout="vertical"
            renderItem={item => (
              <List.Item onClick={()=>this.viewDetail(item)}>
                <div className="logoBox">
                    {/*{item.imgSrc?<img src={item.imgSrc} alt=""/>: <img src={require("@/assets/userImg/enter1.png")} alt=""/>}*/}
                    <img src={require("@/assets/userImg/enter1.png")} alt=""/>
                </div>
                <p className="enterName">{item.enterpriseName}</p>
                <div className={item.status==0?"veri":"veri veried"}>
                  <CheckCircleOutlined />
                  <span> {item.status==0?"未认证":"已认证"}</span>
                </div>
              </List.Item>
            )}
          />
        }
      {
        !showTable?(null):
        <ApplyList handleCancel={this.handleCancel} />
      }
      {/* <Switch>
        <PrivateRoute exact path='/user/personal-info/apply-list' component={ApplyList}/>
      </Switch> */}
      <Modal
          width="38%"
          title="企业详情"
          centered
          visible={visible}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
      >
        <EnterpriseDetail submitAdd={this.submitAdd} onCancel={this.handleCancel} defaultData={defaultData}></EnterpriseDetail>
      </Modal>
      <Modal
          width="25%"
          title="选择企业类型"
          className="selectType"
          centered
          visible={selectModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
      >
        <Row>
          <Col span={20} offset={4}>
            <Radio.Group style={{padding:'20px 0'}} onChange={this.onChange}>
              <Radio value={1}>普通企业</Radio>
              <Radio value={2} style={{marginLeft:'108px'}}>第三方企业</Radio>
            </Radio.Group>
          </Col>
        </Row>
        <Row>
          <Col span={20} offset={4}>
            <p className="note">注：普通企业可以进行费用、各种申请填报；第三方企业只能帮助普通企业填写费用及申请或者统计财务数据。请慎重选择，一旦选择好，将不能修改企业类型。</p>
          </Col>
        </Row>
        <Row>
          <Col offset={9} style={{marginTop:'30px'}}>
            <Button type="primary" onClick={()=>this.selectEnter()}>确定</Button>
            &nbsp;&nbsp;&nbsp;
            <Button onClick={this.handleCancel}>关闭</Button>
          </Col>
        </Row>
      </Modal>
      <Modal
          width="26%"
          title="创建企业"
          centered
          visible={setModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
      >
        <EnterpriseSet submitAdd={this.submitAdd} onCancel={this.handleCancel} {...this.state}></EnterpriseSet>
      </Modal>
      <Modal
          width="26%"
          title="加入企业"
          centered
          visible={addModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
      >
        <EnterpriseJoin submitJoin={this.submitJoin} onCancel={this.handleCancel} datalist={datalist}></EnterpriseJoin>
      </Modal>
      </div>
    )
  }
}
