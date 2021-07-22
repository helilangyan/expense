import React, { Component } from 'react';
import { Row, Col, Tabs, Button, Modal, List, Card, Avatar } from 'antd';
import { PlusOutlined, FilterFilled, CopyOutlined, UploadOutlined, DeleteOutlined, AccountBookOutlined } from '@ant-design/icons';
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import '../index.css'

import ApplySet from './applySet'


const { TabPane } = Tabs;
const { Meta } = Card;

@inject('appStore') @observer
class application extends Component {

  state = {
    data: toJS(this.props.appStore.curEnt),
    formModal: false,
    tabBtn: '1',
    datalist: [
      {
        title: 'SAP',
        description: '连接到SAP概括介绍概括介绍',
        img: 'https://img1.baidu.com/it/u=2304421232,1097459153&fm=26&fmt=auto&gp=0.jpg',
        status: 0
      },
      {
        title: 'QuickBook',
        description: '连接到Quickbood概括介绍概括',
        img: 'https://img1.baidu.com/it/u=2304421232,1097459153&fm=26&fmt=auto&gp=0.jpg',
        status: 1
      },
      {
        title: 'Intuit',
        description: '连接到Intuit概括介绍概括介绍',
        img: 'https://img1.baidu.com/it/u=2304421232,1097459153&fm=26&fmt=auto&gp=0.jpg',
        status: 1
      },
      {
        title: 'Timesheet',
        description: '连接到Timesheet概括介绍概括',
        img: 'https://img1.baidu.com/it/u=2304421232,1097459153&fm=26&fmt=auto&gp=0.jpg',
        status: 1
      },
    ]
    // defaultData: {},
  }
  componentDidMount() {
  }
  handleCancel = () => {
    this.setState({
      formModal: false,
    })
  }
  // 开关设置
  handleOpen = () => {
    this.setState({
      formModal: true
    })
  }
  handleTabClick = (activeKey) => {
    this.setState({ tabBtn: activeKey });
    // this.$child2.getData()
  }
  // 此事件接收子对象
  childEvevnt = (childComp) => {
    this.$child = childComp
  };

  getData = () => {
    if (this.props.defaultData) {
      this.get("/bpm/use/history/records/" + this.props.defaultData.applyTravelEntity.id).then(res => {
        if (res.code === 1) {
          this.setState({
            datalist: res.data,
          })
        }
      });
    }
  }
  getData2 = () => {
    if (this.props.defaultData) {
      this.get("/bpm/use/history/records/" + this.props.defaultData.applyTravelEntity.id).then(res => {
        if (res.code === 1) {
          this.setState({
            datalist: res.data,
          })
        }
      });
    }
  }
  onSubmit = (data) => {
    console.log(data);
  }

  render() {
    const { formModal, tableData, tabBtn, columns, pageSize, total } = this.state;
    return (
      <div>
        <div className="headBox">
          <Row>
            <Col span={24}>
              <Tabs onTabClick={(key) => this.handleTabClick(key)} activeKey={tabBtn}>
                <TabPane tab="已开启连接" key="1">
                  <div className="contentBox">
                    <List
                      grid={{ gutter: 16, column: 4 }}
                      dataSource={this.state.datalist}
                      renderItem={item => (
                        <List.Item>
                          <Card className="card-item">
                            <Meta
                              avatar={<Avatar src={item.img} />}
                              title={item.title}
                              description={item.description}
                            />
                            <Button className="card-btn" onClick={this.handleOpen}>关闭</Button>
                            {
                              item.status == 1 ?
                                <span className="connect-status">已连接</span> :
                                <span className="connect-status danger">连接失败</span>
                            }
                          </Card>
                        </List.Item>
                      )}
                    />
                  </div>
                </TabPane>
                <TabPane tab="未开启连接" key="2">
                <div className="contentBox">
                    <List
                      grid={{ gutter: 16, column: 4 }}
                      dataSource={this.state.datalist}
                      renderItem={item => (
                        <List.Item>
                          <Card className="card-item">
                            <Meta
                              avatar={<Avatar src={item.img} />}
                              title={item.title}
                              description={item.description}
                            />
                            <Button className="card-btn turnedOn" onClick={this.handleOpen}>关闭</Button>
                          </Card>
                        </List.Item>
                      )}
                    />
                  </div>
                </TabPane>
              </Tabs>
            </Col>
          </Row>
        </div>
        <Modal
          width="25%"
          title="设置"
          visible={formModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
        >
          <ApplySet onCancel={this.handleCancel} onSubmit={this.onSubmit} />
        </Modal>
      </div>
    );
  }
}
export default application;
