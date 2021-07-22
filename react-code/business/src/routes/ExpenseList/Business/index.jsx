import React, { Component } from 'react'
import { Row, Col, Tag, Button, Modal, Image, Checkbox, Tooltip, Pagination, message, Popconfirm, List } from 'antd';
import { ScanOutlined, ExclamationCircleFilled, DeleteOutlined, FileImageOutlined } from '@ant-design/icons';
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import PubSub from 'pubsub-js'
import { utc2bj } from '../../../utils/UTCtoBJ'
import ManualSet from './manualSet'
// import axios from 'axios'
// import { isAuthenticated, logout } from "@/utils/Session";
// import { baseURL, timeout, basicUrl } from '@/service/url'
// import ClaimReport from './report'

@inject('appStore') @observer
export default class BusinessExpenses extends Component {
  state = {
    data: toJS(this.props.appStore.curEnt),
    list: [],
    datalist: [],
    pageSize: 10,
    pageNum: 1,
    total: 0,
    manualModal: false,
    editModal: false,
    reportModal: false,
    type: 0,
    defaultData: {},
    searchData: {}
  }

  componentDidMount() {
    this.getData()
    this.props.childEvevnt(this);
    this.token = PubSub.subscribe('filterData', (_, data) => {
      console.log(data)
      this.setState({ searchData: data }, () => {
        this.getData()
      })
    })
  }
  getData = () => {
    const { pageSize, pageNum, data, searchData } = this.state
    this.post("/detail/business-expense/list", {
      limit: pageSize,
      page: pageNum,
      enterpriseId: data.enterpriseId,
      startTime: searchData.startTime,
      endTime: searchData.endTime,
      expenseType: searchData.expenseType,
      tags: searchData.tags,
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
  //格式化列表
  formatList = (datalist) => {
    let list = [];
    let formatlist = [];
    let childData = {};
    let month = [];
    let listObj = {}
    //utc转北京时间
    list = datalist.map(item => {
      if (item.giveDate.indexOf('+') != -1) {
        item.giveDate = utc2bj(item.giveDate).slice(0, -9)
        return item
      } else {
        return item
      }
    })
    // 按时间排二级列表
    datalist.map(item => {
      month.push(item.giveDate.slice(5, 7))
      month = dedupe(month)
      month.sort((a, b) => { return a - b }); //月份排序
    })
    month.map(i => {
      listObj[i] = [];
      list.filter(x => {
        if (i == x.giveDate.slice(5, 7)) {
          listObj[i].push(x);
        }
      })
    })
    Object.keys(listObj).forEach(function (key) {
      childData = {
        name: listObj[key][0].giveDate.slice(0, 7),
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
  // 打开费用详情
  viewDetail = (e) => {
    this.get(`/detail/business-expense/` + e.id).then(res => {
      if (res.code === 1) {
        let type = 0
        if (res.data.workTime) {
          type = 3
        } else if (res.data.startAddress) {
          type = 4
        } else {
          type = 2
        }
        this.setState({
          editModal: true,
          defaultData: res.data,
          type: type,
        })
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
    } else if (checklist.length >= 2) {
      PubSub.publish('morebtn', { moreBtn: 2 })
    } else {
      PubSub.publish('morebtn', { moreBtn: 0 })
    }
  }
  onSubmit = (data) => {
    console.log(data);
    this.post("/detail/business-expense/insert", data).then(res => {
      if (res.code === 1) {
        let text = data.id ? '修改' : '添加'
        message.success(text + res.msg)
        this.getData();
      } else {
        message.error(res.message)
      }
    });
  }
  onDelete = (id) => {
    this.del("/detail/business-expense/delete/" + id).then(res => {
      if (res.code === 1) {
        message.success('删除' + res.msg)
        this.getData();
      } else {
        message.error(res.message)
      }
    });
  }
  // 打开费用新增
  setExpense = (type) => {
    this.setState({
      manualModal: true,
      type: type,
    })
  }
  // 打开费用报告
  viewReport = () => {
    this.setState({
      reportModal: true,
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
  // async getPicture(fileId){
  //   var res = await axios({
  //     url: basicUrl + '/expense-file-server/file/openFile',
  //     method: 'post',
  //     headers: {
  //       'Authorization': isAuthenticated()
  //     },
  //     params: {
  //       id: fileId
  //     },
  //     responseType: "arraybuffer"
  //   });
  //   let imageUrl= 'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((data, byte) => data + String.fromCharCode(byte), ''))
  //   console.log(imageUrl);
  //   return imageUrl
  // }

  render() {
    const { manualModal, reportModal, editModal, datalist, data, type, pageNum, pageSize, total } = this.state
    const text = "违反费用策略,超过最大费用期限60天"
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
    let picUrl = ''
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
                  <div className="bizListItem" key={item.id} >
                    <Row type="flex" justify="center" align="middle">
                      <Col span={1} style={{ height: '65px' }}>
                        <Checkbox onChange={this.handleCheck.bind(this, item)}></Checkbox><br />
                        <Tooltip placement="top" title={text}>
                          <i className="warn"><ExclamationCircleFilled /></i>
                        </Tooltip>
                      </Col>
                      <Col span={5} style={{ height: '60px' }}>
                        <h2 className="tit">
                        {item.expenseName}
                          {/* <i style={{marginLeft:'10px'}}>
                            {
                              item.fileId !== '' ? <FileImageOutlined style={styles.icon} /> : ''
                            }
                          </i> */}
                          </h2>
                        <p className="con">发生日期：{item.giveDate}</p>
                      </Col>
                      <Col span={3} style={{ height: '76px' }}>
                        {
                          item.fileId !== '' ? <FileImageOutlined style={styles.icon} /> : ''
                        }
                      </Col>
                      <Col span={4}>
                        <p className="con">费用分类：<span>{item.expenseTypeName}</span></p>
                      </Col>
                      <Col span={4}>
                        {
                          item.tagsName ? item.tagsName.split(",").map((x, i) => {
                            return <Tag color="blue" key={i}>{x}</Tag>
                          }) : ''
                        }
                      </Col>
                      <Col span={4}>
                        <Button type="primary" onClick={() => this.viewDetail(item)}>打开</Button>
                        {/* <Button type="primary" onClick={this.viewDetail} className="btn-success color-btn">审批中</Button>
                             <Button type="primary" onClick={this.viewDetail} className="btn-danger color-btn">已拒绝</Button>
                             <Button type="primary" onClick={this.viewDetail} className="btn-purple color-btn">已报销</Button> */}
                      </Col>
                      <Col span={2}>
                        <p className="con">金额：<span>{item.money}</span></p>
                        {/* <p className="con">可报销：<span>{item.money}</span></p> */}
                      </Col>
                      <Col span={1} style={{ textAlign: 'right' }}>
                        <Popconfirm placement="left" title={confirmText} onConfirm={() => this.onDelete(item.id)} okText="Yes" cancelText="No">
                          <Button type="text" className="del" ><DeleteOutlined /></Button>
                        </Popconfirm>
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
          title="创建费用"
          centered
          visible={manualModal}
          footer={null}
          onCancel={() => this.setState({ manualModal: false })}
          destroyOnClose={true}
        >
          <ManualSet onSubmit={this.onSubmit} onCancel={this.onCancel} data={data} type={type}></ManualSet>
        </Modal>
        <Modal
          width="40%"
          title="编辑费用"
          centered
          visible={editModal}
          footer={null}
          onCancel={() => this.setState({ editModal: false })}
          destroyOnClose={true}
        >
          <ManualSet onSubmit={this.onSubmit} onCancel={this.onCancel} {...this.state}></ManualSet>
        </Modal>
        {/* <Modal
          width="40%"
          title="报销申请"
          centered
          visible={reportModal}
          footer={null}
          onCancel={() => this.setState({ reportModal: false })}
          destroyOnClose={true}
        >
          <ClaimReport onSubmit={this.onSubmit} onCancel={this.onCancel} {...this.state}></ClaimReport>
        </Modal> */}
      </div>
    )
  }
}
const styles = {
  icon: {
    position: 'relative',
    verticalAlign: '-3px',
    height:'76px',
    lineHeight:'76px',
    fontSize: '20px',
    color: '#999'
  }
}