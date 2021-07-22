import React, { Component } from 'react'
import { Row, Col, Tag, Button, Modal, Image, Checkbox, Tooltip, Pagination, message, Popconfirm, List } from 'antd';
import { ScanOutlined, ExclamationCircleFilled, DeleteOutlined, AccountBookOutlined } from '@ant-design/icons';
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import PubSub from 'pubsub-js'
import { utc2bj } from '../../utils/UTCtoBJ'

import LoanForm from './ApplicationModal/loanForm/index'


@inject('appStore') @observer
export default class LoanList extends Component {
  state = {
    data: toJS(this.props.appStore.curEnt),
    list: [],
    datalist: [],
    pageSize: 10,
    pageNum: 1,
    total: 0,
    editModal: false,
    defaultData: {},
  }

  componentDidMount() {
    this.getData()
    this.props.childEvevnt3(this);
  }
  getData = (searchData) => {
    const { pageSize, pageNum, data } = this.state
    this.post("/detail/apply-borrow/list", {
      limit: pageSize,
      page: pageNum,
      // userId: data.userId,
      enterpriseId: data.enterpriseId,
    }).then(res => {
      if (res.code === 1) {
        this.setState({
          total: res.data.count,
          list: res.data.data
        })
        this.formatList(res.data.data);
      } else {
        // message.error(res.message)
      }
    });
  }
  // 打开费用详情
  viewDetail = (e) => {
    this.get(`/detail/apply-borrow/` + e.id).then(res => {
      if (res.code === 1) {
        let obj = res.data
        obj.status= e.status
        this.setState({
          defaultData: obj,
          editModal: true,
        })
      } else {
        message.error(res.message)
      }
    });
  }
  onSubmit = (subData, type) => {
    this.props.onSubmit(subData, type);
    this.onCancel();
  }
  onDelete = (id) => {
    this.del("/detail/apply-borrow/delete/" + id).then(res => {
      if (res.code === 1) {
        message.success('删除' + res.msg)
        this.getData();
      } else {
        message.error(res.message)
      }
    });
  }
  // 选中列表
  handleCheck = (i, e) => {
    const { list } = this.state
    let check = list
    check.map(item => {
      if (item.id == i.id) {
        item.checked = e.target.checked
      }
    })
    this.formatList(check)
    let checklist = check.filter(item => {
      return item.checked == true
    })
    // console.log(i);
    console.log(checklist);
    if (checklist.length == 1) {
      PubSub.publish('morebtn', { moreBtn: 1 })
    } else if (checklist.length == 2) {
      PubSub.publish('morebtn', { moreBtn: 2 })
    }
  }

  // 打开新增
  applyAdd = (type) => {
    this.setState({
      editModal: true,
      type: type,
    })
  }
  onCancel = () => {
    this.setState({
      manualModal: false,
      editModal: false,
      reportModal: false,
    })
  }
  handleCurrentChange = (page) => {
    this.setState(
      { pageNum: page },
      () => {
        this.getData()
      }
    )
  }
  //格式化列表
  formatList = (datalist) => {
    let list = [];
    let formatlist = [];
    let childData = {};
    let month = [];
    let listObj = {}
    //utc转北京时间
    list = datalist.map(item => {
      if (item.dueDate.indexOf('+') != -1) {
        item.dueDate = utc2bj(item.dueDate).slice(0, -9)
        return item
      } else {
        return item
      }
    })
    // 按时间排二级列表
    datalist.map(item => {
      month.push(item.dueDate.slice(5, 7))
      month = dedupe(month)
      month.sort((a, b) => { return a - b }); //月份排序
    })
    month.map(i => {
      listObj[i] = [];
      list.filter(x => {
        if (i == x.dueDate.slice(5, 7)) {
          listObj[i].push(x);
        }
      })
    })
    Object.keys(listObj).forEach(function (key) {
      childData = {
        name: listObj[key][0].dueDate.slice(0, 7),
        child: listObj[key]
      }
      formatlist.push(childData)
    });
    // 去重
    function dedupe(array) {
      return Array.from(new Set(array));
    }
    this.setState({ datalist: formatlist })
  }
  render() {
    const { editModal, datalist, pageNum, pageSize, total, defaultData } = this.state
    const confirmText = '确定删除此项?';
    const paginationProps = {
      showQuickJumper: true,
      pageSize: pageSize,
      total: total,
      page: pageNum,
      hideOnSinglePage: true,
      onChange: page => {
        this.handleCurrentChange(page)
      },
    };
    return (
      <div className="contentBox">
        <List
          size="large"
          dataSource={datalist}
          pagination={paginationProps}
          renderItem={list => <List.Item>
            <div className="contentList">
              <div className="timeBox">
                {list.name}
              </div>
              <List
                size="large"
                dataSource={list.child}
                renderItem={item => <List.Item>
                  <div className="applyItem" key={item.id} style={styles.applyItem}>
                    <Row type="flex" justify="center" align="middle">
                      <Col span={1}>
                        <Checkbox onChange={this.handleCheck.bind(this, item)}></Checkbox><br />
                      </Col>
                      <Col span={11}>
                        <h2 className="tit">{item.applyName}</h2>
                      </Col>
                      <Col span={5}>
                        <p className="con">日期：<span>{item.dueDate}</span></p>
                      </Col>
                      <Col span={4}>
                        {
                          item.status == 2 ?
                            <Button type="primary" onClick={() => this.viewDetail(item)} className="btn-success color-btn">已启动</Button> :
                            item.status == 1 ?
                              <Button type="primary" onClick={() => this.viewDetail(item)} className="btn-success color-btn">审批中</Button> :
                              item.status == 3 ?
                                <Button type="primary" onClick={() => this.viewDetail(item)} className="btn-danger color-btn">已拒绝</Button> :
                                item.status == 9 ?
                                  <Button type="primary" onClick={() => this.viewDetail(item)} className="btn-purple color-btn">已通过</Button>:
                                  <Button type="primary" onClick={() => this.viewDetail(item)}>打开</Button>
                        }
                      </Col>
                      <Col span={2}>
                        <p className="con money">金额：<span>{item.money}</span></p>
                      </Col>
                      <Col span={1} style={{ textAlign: 'right' }}>
                        {
                          item.status == null?
                        <Popconfirm placement="left" title={confirmText} onConfirm={() => this.onDelete(item.id)} okText="Yes" cancelText="No">
                          <Button type="text" className="del" ><DeleteOutlined /></Button>
                        </Popconfirm>:''
                        }
                      </Col>
                    </Row>
                  </div>
                </List.Item>}
              />
            </div>
          </List.Item>}
        />
        <Modal
          width="40%"
          title="借款申请"
          centered
          visible={editModal}
          footer={null}
          onCancel={() => this.setState({ editModal: false })}
          destroyOnClose={true}
        >
          <LoanForm onSubmit={this.onSubmit} onCancel={this.onCancel} defaultData={defaultData} />
        </Modal>
      </div>
    )
  }
}
const styles = {
  applyItem: {
    padding: '25px 15px 0',
  }
}
