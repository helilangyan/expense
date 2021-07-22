import React, { Component } from 'react'
import { Row, Col, Tag, Button, Modal, Image, Checkbox, Tooltip, Pagination, message ,Popconfirm,List} from 'antd';
import { ScanOutlined, ExclamationCircleFilled, DeleteOutlined } from '@ant-design/icons';
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import { utc2bj } from '../../../utils/UTCtoBJ'
import ManualSet from './manualSet'
import ClaimReport from './report'

@inject('appStore') @observer
export default class PersonalExpenses extends Component {
  state = {
    data: {},
    datalist: [
    ],
    pageSize: 9,
    pageNum: 1,
    total:0,
    manualModal: false,
    editModal: false,
    reportModal: false,
    type: 0,
    defaultData: {},








  }

  componentDidMount() {
    this.setState({data:toJS(this.props.appStore.curEnt)},()=>{
      this.getData()
    })
    this.props.personEvent(this);
  }
  getData = () => {
    const { pageSize, pageNum, data } = this.state
    let obj = {
      limit: pageSize,
      page: pageNum,
      // userId: data.userId,
      enterpriseId: data.enterpriseId,
    }
    this.post("/detail/business-expense/list", obj).then(res => {
      if (res.code === 1) {
        this.setState({total:res.data.count})
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
      if (item.giveDate) {
        item.giveDate = utc2bj(item.giveDate).slice(0, -9)
        return item
      }
    })
    // 按时间排二级列表
    datalist.map(item => {
      month.push(item.giveDate.slice(5, 7))
      month = dedupe(month)
      month.sort((a, b)=>{return a - b}); //月份排序
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
    console.log(formatlist);

    this.setState({ datalist: formatlist })
  }
  // 打开费用新增
  setExpense = (type) => {
    this.setState({
      manualModal: true,
      type: type,
    })
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
          type:type,
        })
      } else {
        message.error(res.message)
      }
    });
  }
  // 打开费用报告
  viewReport = () => {
    this.setState({
      reportModal: true,
    })
  }
  onSubmit = (data) => {
    this.post("/detail/business-expense/insert", data).then(res => {
      if (res.code === 1) {
        let text = data.id?'修改':'添加'
        message.success(text + res.msg)
        this.getData();
      } else {
        message.error(res.message)
      }
    });
  }
  onDelete = (id) => {
    this.del("/detail/business-expense/delete/"+id).then(res => {
      if (res.code === 1) {
        message.success('删除' + res.msg)
        this.getData();
      } else {
        message.error(res.message)
      }
    });
  }
  onCancel = () => {
    this.setState({
      manualModal: false,
      editModal: false,
      reportModal: false,
    })
  }
  handleCurrentChange = (page)=>{
    this.setState(
      {pageNum: page},
      () => {
        this.getData()
      }
    )
  }
  render() {
    const { manualModal, reportModal, editModal, datalist, data, type,pageNum,pageSize,total} = this.state
    const text = "违反费用策略,超过最大费用期限60天"
    const confirmText = '确定删除此项?';
    const paginationProps = {
      showQuickJumper: true,
      pageSize: pageSize,
      total: total,
      page: pageNum,
      hideOnSinglePage:true,
      onChange: page => {
        this.handleCurrentChange(page)
      },
    };

    return (
      <div className="contentBox personBox">
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
                  grid={{ gutter: 16, column:{lg:2,xl:3}}}
                  renderItem={item => <List.Item>
                    <div className="perListItem" key={item.id} >
                      <Row type="flex" justify="center" align="middle">
                        <Col span={1}>
                          <Checkbox></Checkbox>
                        </Col>
                        <Col span={9} justify="center" align="middle" onClick={() => this.viewDetail(item)}>
                          <Image
                            width={165}
                            height={110}
                            src=""
                            fallback="data:image/png;base64,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"
                          />
                          <h2 className="tit"><ScanOutlined /> {item.expenseName}</h2>
                            <p className="amount">金额：<span>{(item.moneyType=='cny'?'¥ ':'$ ')+ item.money}</span></p>
                        </Col>
                        <Col span={10} offset={2}>
                          <p className="con">费用分类：<span>{item.expenseType}</span></p>
                          <div className="tags">
                            <Tag color="blue">标签1</Tag>
                            <Tag color="blue">标签2</Tag>
                          </div>
                          <p className="con">发生日期：{item.giveDate}</p>
                        </Col>
                        <Col span={2}>
                          <div className="mark">
                            <span>支出</span>
                          </div>
                          <Popconfirm placement="left" title={confirmText} onConfirm={()=>this.onDelete(item.id)} okText="Yes" cancelText="No">
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
        <Modal
          width="40%"
          title="报销申请"
          centered
          visible={reportModal}
          footer={null}
          onCancel={() => this.setState({ reportModal: false })}
          destroyOnClose={true}
        >
          <ClaimReport onSubmit={this.onSubmit} onCancel={this.onCancel} {...this.state}></ClaimReport>
        </Modal>
      </div>
    )
  }
}
