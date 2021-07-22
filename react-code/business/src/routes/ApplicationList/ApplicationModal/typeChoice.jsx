import React, { Component } from 'react';
import {Row, Col,Button} from 'antd';
import {FileDoneOutlined,CarOutlined, AccountBookOutlined} from '@ant-design/icons';
import '../style.css'



class typeChoice extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    typeChoice=(type)=>{
        this.props.typeChoice(type);
    }
    onCancel=()=>{
        this.props.onCancel();
    }
    render() {
        return (
            <div>
                <Row >
                    <Col span={8} className='choiceItem' onClick={()=>{this.typeChoice(1)}}>
                        <Button type="primary" shape="circle" icon={<FileDoneOutlined />} />
                        <p>报销申请</p>
                    </Col>
                    <Col span={8} className='choiceItem' onClick={()=>{this.typeChoice(2)}}>
                        <Button type="primary" shape="circle" icon={<CarOutlined />} />
                        <p>出差申请</p>
                    </Col>
                    <Col span={8} className='choiceItem' onClick={()=>{this.typeChoice(3)}}>
                        <Button type="primary" shape="circle" icon={<AccountBookOutlined />} />
                        <p>借款申请</p>
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