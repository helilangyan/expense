import React, { Component } from 'react'
import {Row, Col, message ,List, Button, Modal ,Radio} from 'antd';
import { CheckCircleOutlined } from '@ant-design/icons';

export default class index extends Component {
  state = {
    datalist: [],
  };
  render() {
    const {datalist} = this.state;

    return (
      <div>
        <List
          grid={{ gutter:20, md: 4,lg:4,xxl: 7,}}
          className="enterList"
          dataSource={datalist}
          itemLayout="vertical"
          renderItem={item => (
            <List.Item onClick={this.viewDetail}>
              <div className="logoBox">
                <img src={require("@/assets/userImg/enter1.png")} alt=""/>
              </div>
              <p className="enterName">{item.enterpriseName}</p>
              <div className={item.status===0?"veri":"veri veried"}>
                <CheckCircleOutlined />
                <span> {item.status===0?"未认证":"已认证"}</span>
              </div>
            </List.Item>
          )}
        />
      </div>
    )
  }
}
