import React, { Component } from 'react';
import {Row, Col,Button} from 'antd';
import {ScanOutlined,FileOutlined, FieldTimeOutlined,DashboardOutlined} from '@ant-design/icons';
import './style.css'

class typeChoice extends Component {
    state = {  }
    typeChoice=(type)=>{
        this.props.typeChoice(type);
    }
    onCancel=()=>{
        this.props.onCancel();
    }
    render() {
        return (
            <div>
                <Row  style={{padding:'50px 0'}}>
                    <Col span={6} className='choiceItem' onClick={()=>{this.typeChoice(1)}}>
                        <Button type="primary" shape="circle" icon={<ScanOutlined />} />
                        <p>Create by Scan</p>
                    </Col>
                    <Col span={6} className='choiceItem' onClick={()=>{this.typeChoice(2)}}>
                        <Button type="primary" shape="circle" icon={<FileOutlined />} />
                        <p>Create by Manual</p>
                    </Col>
                    <Col span={6} className='choiceItem' onClick={()=>{this.typeChoice(3)}}>
                        <Button type="primary" shape="circle" icon={<FieldTimeOutlined />} />
                        <p>Create Time Expnese</p>
                    </Col>
                    <Col span={6} className='choiceItem' onClick={()=>{this.typeChoice(4)}}>
                        <Button type="primary" shape="circle" icon={<DashboardOutlined />} />
                        <p>Create Mileage Expnese</p>
                    </Col>
                </Row>
                <Row>
                    <Col span={24} style={{textAlign:'center',marginTop:"20px"}}>
                        <Button onClick={this.onCancel}>取消</Button>
                    </Col>
                </Row>
            </div>
         );
    }
}

export default typeChoice;