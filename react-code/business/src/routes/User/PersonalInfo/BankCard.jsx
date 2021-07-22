import React, { Component } from 'react'
import {Pagination, message ,List, Button, Modal } from 'antd';
import BankCardSet from './bankCardSet'


export default class BankCard extends Component {
  state = {
    // initLoading: true,
    loading: true,
    data: [],
    datalist: [],
    showTotal:0,
    pageNum:0,

    visible:false,
    setModal:false,

    defaultData:{},

  };

  //获取数据
  getData = () => {
    let obj= {
      limit:10,
      page:1,
    }
    this.post("/user/bank/list", obj).then(res => {
      if (res.code === 1) {
        this.setState({
          datalist:res.data.data,
          loading: false,
        })
      } else {
          message.error(res.message)
      }
    });
  }
  
  componentDidMount(){
    this.getData();
  }

  //打开新增对话框
  addModal = ()=>{
    this.setState({
      visible:true,
    })
  }
  handleCancel = () => {
    this.setState({
        visible: false,
        setModal: false,
    });
  };
  // 打开编辑
  viewCard = (e)=>{
    this.get(`/user/bank/`+e.id).then(res => {
      if (res.code === 1) {
        console.log(res.data);
        this.setState({
            defaultData:res.data,
            setModal: true,
        });
      } else {
          message.error(res.message)
      }
    })
  }
  // 新增或修改
  submitAdd = (e)=>{
    console.log(e);
    this.post(`/user/bank/insert`,e).then(res => {
      if (res.code === 1) {
        message.success('操作'+res.msg)
        this.getData();  
        this.handleCancel();
      } else {
          message.error(res.msg)
      }
    })
  }
  // 删除
  delCard = (e)=>{
    console.log(e);
    this.del(`/user/bank/delete/`+e.id).then(res => {
        if (res.code === 1) {
            message.success('删除'+res.msg)
            this.getData();
        } else {
            message.error(res.msg)
        }
    })
  }

  render() {
    const { loading,  datalist ,showTotal, pageNum,defaultData} = this.state;
    return (
      <div>
      <List
        header={<div className="addCard" onClick={this.addModal}>添加银行卡</div>}
        loading={loading}
        itemLayout="horizontal"
        dataSource={datalist}
        className="cardList"
        renderItem={item => (
          <List.Item className="cardItem">
            <div className="list-info">
              <div className="listAccount">
                <span >{item.bankName}</span>
                {/* <span className="accountNum">**** **** **** {item.bankAccount}</span> */}
                <span className="accountNum">**** **** **** {item.bankAccount!==null?item.bankAccount.slice(-4):item.bankAccount}</span>
              </div>
              <div className="defaultCard">默认</div>
            </div>
            <ul className="tooltip">
              <Button type="text">设为默认账户</Button>
              <Button type="text" onClick={()=>{this.viewCard(item)}}>编辑</Button>
              <Button type="text" onClick={()=>{this.delCard(item)}}>删除</Button>
            </ul>
          </List.Item>
        )}
      />
      <Modal
          width="30%"
          title="添加银行卡"
          visible={this.state.visible}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
      >
        <BankCardSet submitAdd={this.submitAdd} onCancel={this.handleCancel}></BankCardSet>
      </Modal>
      <Modal
          width="30%"
          title="编辑银行卡"
          footer={null}
          visible={this.state.setModal}
          onCancel={this.handleCancel}
          destroyOnClose={true}
      >
        <BankCardSet submitAdd={this.submitAdd} onCancel={this.handleCancel} defaultData={defaultData}></BankCardSet>
      </Modal>
        {/* <Pagination
        size="small"
        defaultCurrent={pageNum}
        total={50}
        disabled
        showTotal={showTotal}
        showSizeChanger
        showQuickJumper
      /> */}
      </div>

    )
  }
}
