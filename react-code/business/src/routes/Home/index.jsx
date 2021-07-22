import React from 'react'
import { Row, Col, Card, Divider } from 'antd'
import { Chart, Geom, Axis, Tooltip, Legend, Coord, Label } from 'bizcharts';
import './style.css'
import DataSet from "@antv/data-set";


const imgs = [
  'http://47.99.130.140/imgs/wallhaven-p8r1e9.jpg',
  'http://47.99.130.140/imgs/wallhaven-e7zyy8.jpg',
  'http://47.99.130.140/imgs/wallhaven-6k9e7q.jpg',
  'http://47.99.130.140/imgs/photo.jpg',
]
const data = [
  { month: '1月', money: 565, income: 2300 },
  { month: '2月', money: 2230, income: 667 },
  { month: '3月', money: 1563, income: 982 },
  { month: '4月', money: 350, income: 5271 },
  { month: '5月', money: 895, income: 3710 },
  { month: '6月', money: 436, income: 3710 },
  { month: '7月', money: 1598, income: 3710 },
  { month: '8月', money: 1563, income: 3710 },
  { month: '9月', money: 999, income: 3710 },
  { month: '10月', money: 431, income: 3710 },
  { month: '11月', money: 2020, income: 3710 },
  { month: '12月', money: 1596, income: 3710 }
];
const data2 = [
  { cord: '分类一', money: 300 },
  { cord: '分类二', money: 100 },
  { cord: '分类三', money: 150 },
  { cord: '分类四', money: 50 }
]

// 定义度量
const cols = {
  money: { alias: '报销金额' },
  month: { alias: '月份' }
};

const { DataView } = DataSet;

const dv = new DataView();
dv.source(data2).transform({
  type: "percent",
  field: "money",
  dimension: "cord",
  as: "percent"
});
const cols2 = {
  percent: {
    formatter: val => {
      val = val.toFixed(3) * 100 + "%";
      return val;
    }
  }
};
class Home extends React.Component {
  render() {
    return (
      <div style={styles.bg} className='home'>
        <div className="headBox">
          <Row>
            <Col span='6'>
              结算管理
            </Col>
          </Row>
          <Row style={{ marginTop: '17px' }}>
            <Col span={24}>
              <Card>
                <Row>
                  <Col span='5' className="titleStats">
                    <div className="tit">接单</div>
                    <div className="count">100单</div>
                  </Col>
                  <Col span='1' >
                    <Divider type="vertical" style={{ height: '100%' }}></Divider>
                  </Col>
                  <Col span='5' className="titleStats">
                    <div className="tit">提交</div>
                    <div className="count">100单</div>
                  </Col>
                  <Col span='1' >
                    <Divider type="vertical" style={{ height: '100%' }}></Divider>
                  </Col>
                  <Col span='5' className="titleStats">
                    <div className="tit">完成</div>
                    <div className="count">100单</div>
                  </Col>
                  <Col span='1' >
                    <Divider type="vertical" style={{ height: '100%' }}></Divider>
                  </Col>
                  <Col span='5' className="titleStats">
                    <div className="tit">平均花费时间</div>
                    <div className="count">6天5小时23分22秒</div>
                  </Col>
                </Row>
              </Card>
            </Col>
            <Col span={17}>
              <Card id="forms" title="报销金额统计" style={{ marginTop: '17px', marginRight: '17px' }}>
                <Chart width={1100} height={350} data={data} scale={cols} autoFit>
                  <Axis name="month" title />
                  <Axis name="money" title />
                  {/* <Legend position="top" dy={50}/> */}
                  <Tooltip />
                  <Geom type="line" position="month*money" color="money" shape='smooth' />
                  <Geom type="point" position="month*money" size={3} shape='circle' color='#E6965C' />
                </Chart>
              </Card>
            </Col>
            <Col span={7}>
              <Card id="forms" title="已报销费用分类统计" style={{ marginTop: '17px' }}>
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
    )
  }
}

const styles = {
  bg: {
    position: 'absolute',
    top: 0,
    left: 0,
    width: '100%',
    height: 'calc(100vh - 64px)'
  }
}

export default Home