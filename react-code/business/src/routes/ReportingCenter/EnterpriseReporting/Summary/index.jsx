import React, { Component } from 'react';
import { Row, Col, Tabs, Button, Modal, Form, Input, Space, Card, DatePicker, Select, } from 'antd';
import { QuestionCircleFilled, LeftOutlined } from '@ant-design/icons';
import { Chart, Geom, Axis, Tooltip, Legend, Coord, Label } from 'bizcharts';
import DataSet from "@antv/data-set";
import '../../style.css'

import ApplySet from './applySet'
const { DataView } = DataSet;


const data = [
  { month: '分类1', money: 6100, income: 2300 },
  { month: '分类2', money: 2230, income: 667 },
  { month: '分类3', money: 8000, income: 982 },
  { month: '分类4', money: 350, income: 5271 },
  { month: '分类5', money: 4230, income: 3710 },
  { month: '分类6', money: 3988, income: 3710 },
  { month: '分类7', money: 2980, income: 3710 },
  { month: '分类8', money: 8300, income: 3710 },
  { month: '分类9', money: 9793, income: 3710 },
  { month: '分类10', money: 5696, income: 3710 },
  { month: '分类11', money: 3824, income: 15000 },
  { month: '分类12', money: 5218, income: 3710 },
  { month: '分类13', money: 3696, income: 3710 },
  { month: '分类14', money: 5013, income: 10000 },
  { month: '分类15', money: 1596, income: 12500 }
];
const data2 = [
  { cord: '分类一', money: 300 },
  { cord: '分类二', money: 100 },
  { cord: '分类三', money: 150 },
  { cord: '分类四', money: 50 }
]
const dv = new DataView();
dv.source(data2).transform({
  type: "percent",
  field: "money",
  dimension: "cord",
  as: "percent"
});
const cols = {
  money: { alias: '报销金额' },
  month: { alias: '月份' }
};
const cols2 = {
  percent: {
    formatter: val => {
      val = val.toFixed(3) * 100 + "%";
      return val;
    }
  }
};
class application extends Component {

  state = {
    searchData: {
      roleName: ''
    },
    // defaultData: {},
  }
  componentDidMount() {
  }
  handleCancel = () => {
    this.setState({
      formModal: false,
    })
  }
  // 添加申请
  addItem = () => {
    this.setState({
      formModal: true
    })
  }
  goBack = () => {
    this.props.goReport(0)
  }


  // onSubmit = (data) => {
  // 	console.log(data);
  // }

  render() {
    const { formModal, tableData, tabBtn, columns, pageSize, total } = this.state;
    return (
      <div>
        <div className="headBox">
          <Row>
            <Col span='6'>
              <LeftOutlined className="backBtn" onClick={this.goBack} />费用分类汇总报表
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <div className="contentBox">
                <div className="contentTitle">
                  <Row>
                    <Col span='16'>
                      <Form layout="inline" onSubmit={this.handleSubmit}>
                        <Form.Item label="年度">
                          <DatePicker picker="year" />
                        </Form.Item>
                        <Form.Item label="月份">
                          <DatePicker picker="month" />
                        </Form.Item>
                        <Form.Item>
                          <Button type="primary" onClick={this.searchClick}>查询</Button>
                          &nbsp; &nbsp;
                          <Button onClick={this.searchReset}>重置</Button>
                        </Form.Item>
                      </Form>
                    </Col>
                  </Row>
                </div>
                <div className="content">
                  <Row>
                    <Col span="24">
                      <Card id="forms" title="费用分类" style={{ marginTop: '17px', marginRight: '17px' }}>
                        <Chart width={1500} height={400} data={data} scale={cols} autoFit>
                          <Axis name="month" title />
                          <Axis name="money" title />
                          {/* <Legend position="top" dy={50}/> */}
                          <Tooltip />
                          <Geom type="interval" position="month*money" color="money" shape='smooth' />
                        </Chart>
                      </Card>
                    </Col>
                    <Col span={7}>
                      <Card id="forms" title="按部门统计" style={{ marginTop: '17px' }}>
                        <Chart height={350} data={dv} scale={cols2} forceFit>
                          <Coord type={"theta"} radius={0.75} innerRadius={0.6} />
                          <Axis name="cord" />
                          <Legend position="bottom" />
                          <Tooltip showTitle={false} />
                          <Geom
                            type="intervalStack"
                            position="percent"
                            color="cord"
                            tooltip={[
                              "cord*money*percent",
                              (cord, money, percent) => {
                                percent = money + "&nbsp;&nbsp;&nbsp;&nbsp;" + percent.toFixed(3) * 100 + "%";
                                return {
                                  name: cord,
                                  value: percent
                                };
                              }
                            ]}
                            style={{
                              lineWidth: 1,
                              stroke: "#fff"
                            }}
                          >
                            <Label
                              content="percent"
                              formatter={(val, item) => {
                                return item.point.money + "   " + val;
                              }}
                            />
                          </Geom>
                        </Chart>
                      </Card>
                    </Col>
                    <Col span={7} offset={1}>
                      <Card id="forms" title="按项目统计" style={{ marginTop: '17px' }}>
                        <Chart height={350} data={dv} scale={cols2} forceFit>
                          <Coord type={"theta"} radius={0.75} innerRadius={0.6} />
                          <Axis name="cord" />
                          <Legend position="bottom" />
                          <Tooltip showTitle={false} />
                          <Geom
                            type="intervalStack"
                            position="percent"
                            color="cord"
                            tooltip={[
                              "cord*money*percent",
                              (cord, money, percent) => {
                                percent = money + "&nbsp;&nbsp;&nbsp;&nbsp;" + percent.toFixed(3) * 100 + "%";
                                return {
                                  name: cord,
                                  value: percent
                                };
                              }
                            ]}
                            style={{
                              lineWidth: 1,
                              stroke: "#fff"
                            }}
                          >
                            <Label
                              content="percent"
                              formatter={(val, item) => {
                                return item.point.money + "   " + val;
                              }}
                            />
                          </Geom>
                        </Chart>
                      </Card>
                    </Col>
                    <Col span={7} offset={1}>
                      <Card id="forms" title="按标签统计" style={{ marginTop: '17px' }}>
                        <Chart height={350} data={dv} scale={cols2} forceFit>
                          <Coord type={"theta"} radius={0.75} innerRadius={0.6} />
                          <Axis name="cord" />
                          <Legend position="bottom" />
                          <Tooltip showTitle={false} />
                          <Geom
                            type="intervalStack"
                            position="percent"
                            color="cord"
                            tooltip={[
                              "cord*money*percent",
                              (cord, money, percent) => {
                                percent = money + "&nbsp;&nbsp;&nbsp;&nbsp;" + percent.toFixed(3) * 100 + "%";
                                return {
                                  name: cord,
                                  value: percent
                                };
                              }
                            ]}
                            style={{
                              lineWidth: 1,
                              stroke: "#fff"
                            }}
                          >
                            <Label
                              content="percent"
                              formatter={(val, item) => {
                                return item.point.money + "   " + val;
                              }}
                            />
                          </Geom>
                        </Chart>
                      </Card>
                    </Col>
                  </Row>
                </div>
              </div>
            </Col>
          </Row>
        </div>
        {/* <Modal
          width="35%"
          title="申请"
          visible={formModal}
          footer={null}
          onCancel={this.handleCancel}
          destroyOnClose={true}
        >
          <ApplySet onCancel={this.handleCancel} />
        </Modal> */}
      </div>
    );
  }
}
export default application;
