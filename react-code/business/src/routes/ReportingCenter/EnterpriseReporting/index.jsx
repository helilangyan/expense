import React, { Component } from 'react';
import { Row, Col, } from 'antd';
import {  AccountBookOutlined, PieChartOutlined } from '@ant-design/icons';
import Icon from '@ant-design/icons';

import '../style.css'

import Detail from './Detail'
import Summary from './Summary'

const CustomizedSvg = () => <svg t="1625465060508" className="icon" viewBox="0 0 1051 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="9140" width="40" height="40"><path d="M51.54291 77.514421C-13.716396 144.546094 29.153994 224.191038 72.203462 266.100544c7.503904 7.30743 18.472723 8.212032 24.498974 2.022053L243.293095 117.551276c6.025227-6.188956 5.4225-16.549931-2.676969-24.435529C198.788485 52.394301 116.803239 10.483772 51.54291 77.514421zM87.522356 112.543216c20.400632-20.955264 55.719023-29.750581 97.306186 0.415462l-94.290503 96.849792C59.271942 169.043021 67.122747 133.497457 87.522356 112.543216z" p-id="9141" fill="#515151"></path><path d="M252.846682 166.45201 145.840576 276.364305c-15.770172 16.198937-15.423271 42.11212 0.774643 57.881268l153.547095 120.907721c16.197914 15.769149 42.11212 15.423271 57.882292-0.775666l78.437443-80.56592c15.769149-16.197914 15.421224-42.11212-0.775666-57.881268L310.72795 165.677367C294.53106 149.906172 268.616854 150.254096 252.846682 166.45201zM386.311393 337.578482c4.049223 3.941775 4.136204 10.42135 0.193405 14.470573l-51.554166 52.954049c-3.942799 4.050246-10.42135 4.136204-14.470573 0.193405l-121.612779-95.532797c-4.049223-3.942799-4.136204-10.42135-0.193405-14.470573l74.40971-76.428693c3.941775-4.050246 10.42135-4.136204 14.470573-0.193405L386.311393 337.578482z" p-id="9142" fill="#515151"></path><path d="M708.034768 716.651007l-62.901609 64.608483 142.495388 75.803453 142.493342 75.804477-79.594803-140.41296-79.592756-140.41296L708.034768 716.651007zM824.468594 830.006727l-91.534739-65.355497 23.749914-24.39562L824.468594 830.006727z" p-id="9143" fill="#515151"></path><path d="M654.180684 208.911813l102.323038 99.623937-37.540386 38.557463-102.323038-99.623937 37.540386-38.557463Z" p-id="9144" fill="#515151"></path><path d="M729.264397 131.7957l102.322304 99.623223-37.5411 38.558196-102.322304-99.623223 37.5411-38.558196Z" p-id="9145"></path><path d="M579.100159 286.027207l102.323038 99.623937-37.541813 38.55893-102.323038-99.623937 37.541813-38.55893Z" p-id="9146" fill="#515151"></path><path d="M428.939449 440.257872l102.321571 99.62251-37.5411 38.558196-102.321571-99.62251 37.5411-38.558196Z" p-id="9147" fill="#515151"></path><path d="M278.779525 594.488558l102.322304 99.623223-37.541813 38.55893-102.322304-99.623223 37.541813-38.55893Z" p-id="9148" fill="#515151"></path><path d="M353.856471 517.373385l102.322304 99.623223-37.5411 38.558196-102.322304-99.623223 37.5411-38.558196Z" p-id="9149"></path><path d="M203.69912 671.60487l102.322304 99.623223-37.541813 38.55893-102.322304-99.623223 37.541813-38.55893Z" p-id="9150" fill="#515151"></path><path d="M775.772522 84.020602l-8.958022 9.200546 209.610972 204.069769-28.621874 29.398564 0.325411 0.318248-46.457078 47.717791 0.001023 0.001023-37.541011 38.559201-37.541011 38.559201-37.538965 38.560224-0.001023-0.001023-37.539988 38.558178-10.160407 10.4367-27.379581 28.121478L504.036631 363.134531l-37.542035 38.560224 209.611995 204.069769-27.025517 27.760251 0.324388 0.317225-48.052412 49.357128-37.541011 38.560224-37.539988 38.559201-37.539988 38.559201-37.539988 38.559201-8.566096 8.79941-28.972868 29.760814-0.326435-0.319272-37.539988 38.560224-9.636474 9.897418-27.904537 28.661783L128.637776 748.725516l-8.336876 8.563026c-16.000416 16.436344-15.648398 42.73122 0.786922 58.731636l188.160427 183.185112c16.435321 16.001439 42.729174 15.648398 58.729589-0.786922l655.470599-673.268964c16.002462-16.436344 15.648398-42.72815-0.787946-58.729589L834.501088 83.234703C818.065767 67.234287 791.771915 67.585281 775.772522 84.020602z" p-id="9151" fill="#515151"></path></svg>

const CustomizedIcon = props => <Icon component={CustomizedSvg} {...props} />;


class application extends Component {
  state = {
    tabBtn: 0,
  }
  componentDidMount() {
  }
  handleCancel = () => {
    this.setState({
      formModal: false,
    })
  }
  goReport = (activeKey) => {
    this.setState({ tabBtn: activeKey });
  }

  render() {
    const { formModal, tableData, tabBtn, columns, pageSize, total } = this.state;
    return (
      tabBtn == 1 ? <Detail goReport={this.goReport} /> :
        tabBtn == 2 ? <Summary goReport={this.goReport} /> :
          <div className="headBox">
            <Row>
              <Col span='6'>
                ????????????
              </Col>
            </Row>
            <Row justify="space-between">
              <Col span={24}>
                <div className="contentBox" style={{ height: '200px' }}>
                  <div className="content">
                    <Row className="reportType">
                      <Col span="2">
                        <AccountBookOutlined onClick={() => this.goReport(1)} />
                        <p>??????????????????</p>
                      </Col>
                      <Col span="2" offset="1">
                        <PieChartOutlined onClick={() => this.goReport(2)} />
                        <p>????????????????????????</p>
                      </Col>
                    </Row>
                  </div>
                </div>
              </Col>
            </Row>
            <Row className="customized">
              <Col>
                <CustomizedIcon style={{marginRight:'10px'}}/>
              </Col>
              <Col>
                <h4>??????????????????</h4>
                <p>????????????????????????????????????????????????????????????????????????</p>
              </Col>
            </Row>
          </div>
    );
  }
}
export default application;
