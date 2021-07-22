import React, { Component } from 'react';
import { Row, Col, Tabs, Button, Modal, Checkbox, Input, Space, List, Popconfirm } from 'antd';
import './style.css'
import { PlusOutlined, FilterFilled, CopyOutlined, UploadOutlined, DeleteOutlined, AccountBookOutlined } from '@ant-design/icons';

import ApplyList from './applyList'
import LoanList from './loanList'
import TravelList from './travelList'

import TypeChoice from './ApplicationModal/typeChoice'

import ApplyForm from './ApplicationModal/applyForm'
import TravelForm from './ApplicationModal/travelForm/index'
import LoanForm from './ApplicationModal/loanForm/index'

import SendApproval from './ApplicationModal/sendApproval'
import PubSub from "pubsub-js";


const { TabPane } = Tabs;

class application extends Component {

	state = {
		typeModal: false,
		choiceType: 0,
		formModal: false,
		sendModal: false,
		tabBtn: '1',
		moreBtn: 0,
		applyType: '',
		applyData: '',
		// pageSize: 5,
		// pageNum: 1,
		// total: 0,
		// type: 0,
		// defaultData: {},
	}
	componentDidMount() {
		this.token = PubSub.subscribe('refresh', (_, data) => {
			this.setState({ tabBtn:data.tabBtn }, () => {
				this.handleTabClick(data.tabBtn)
				this['$child' + data.tabBtn].getData()
			})
		})
	}
	handleCancel = () => {
		this.setState({
			typeModal: false,
			formModal: false,
			sendModal: false,
		})
	}
	// 打开审批人
	openSend = (data, type) => {
		console.log(data);
		console.log(type);
		this.setState({
			formModal: false,
			sendModal: true,
			applyData: data,
			applyType: type
		})

	}
	//审批人确认
	submitAdd = (data) => {
		console.log(data);
		console.log(this.state.applyData);
	}
	// 添加申请
	addItem = () => {
		this.setState({
			typeModal: true
		})
	}
	// 选择申请类型
	typeChoice = (type) => {
		this.setState({
			typeModal: false,
			formModal: true,
			choiceType: type
		})
	}
	handleTabClick = (activeKey) => {
		this.setState({ tabBtn: activeKey });
	}
	// 此事件接收子对象
	childEvevnt = (childComp) => {
		this.$child1 = childComp
	};
	childEvevnt2 = childComp => {
		this.$child2 = childComp;
	};
	childEvevnt3 = childComp => {
		this.$child3 = childComp;
	};

	// onSubmit = (data) => {
	// 	console.log(data);
	// }

	render() {
		const { typeModal, formModal, sendModal, choiceType, tabBtn, moreBtn, } = this.state;
		const operations =
			<div>
				{
					moreBtn == 1 ?
						<Space>
							<Button danger type="primary" icon={<DeleteOutlined />} onClick={this.showDrawer}>删除</Button>
							<Button type="primary" icon={<UploadOutlined />} onClick={this.addApply}>导出</Button>
							<Button type="primary" icon={<CopyOutlined />} onClick={this.addApply}>复制</Button>
						</Space> : null
				}
				&nbsp;&nbsp;
				<Space>
					<Button type="primary" icon={<FilterFilled />} onClick={this.showDrawer}>Filter</Button>
					<Button type="primary" icon={<PlusOutlined />} onClick={this.addItem}>添加</Button>
				</Space>
			</div>
		return (
			<div>
				<div className="headBox">
					<Row>
						<Col span={24}>
							<Tabs onTabClick={(key) => this.handleTabClick(key)} tabBarExtraContent={operations} activeKey={tabBtn}>
								<TabPane tab="报销申请" key="1">
									{tabBtn=='1'?<ApplyList childEvevnt={this.childEvevnt} onSubmit={this.openSend}  />:''}
								</TabPane>
								<TabPane tab="出差申请" key="2">
									{tabBtn=='2'?<TravelList childEvevnt2={this.childEvevnt2} onSubmit={this.openSend}  />:''}
								</TabPane>
								<TabPane tab="借款申请" key="3">
									{tabBtn=='3'?<LoanList childEvevnt3={this.childEvevnt3} onSubmit={this.openSend} />:''}
								</TabPane>
							</Tabs>
						</Col>
					</Row>
				</div>
				<Modal
					width="30%"
					title="申请类型"
					visible={typeModal}
					footer={null}
					onCancel={this.handleCancel}
					destroyOnClose={true}
				>
					<TypeChoice typeChoice={this.typeChoice} onCancel={this.handleCancel}></TypeChoice>
				</Modal>
				<Modal
					width="40%"
					title={choiceType == 1 ? "报销申请" : choiceType == 2 ? "出差申请" : "借款申请"}
					visible={formModal}
					footer={null}
					onCancel={this.handleCancel}
					destroyOnClose={true}
				>
					{
						choiceType == '1' ? <ApplyForm onCancel={this.handleCancel} onSubmit={this.openSend} /> :
							choiceType == '2' ? <TravelForm onCancel={this.handleCancel} onSubmit={this.openSend} /> : <LoanForm onCancel={this.handleCancel} onSubmit={this.openSend} />
					}
				</Modal>
				<Modal
					width="30%"
					title="提交审批-审批节点名称"
					visible={sendModal}
					footer={null}
					onCancel={this.handleCancel}
					destroyOnClose={true}
				>
					<SendApproval applyType={this.state.applyType} applyData={this.state.applyData}
						submitAdd={this.submitAdd}
						typeChoice={this.typeChoice} onCancel={this.handleCancel}></SendApproval>
				</Modal>
			</div>
		);
	}
}
export default application;
const styles = {
	icon: {
		fontSize: '20px',
		color: '#777',
		margin: '10px 10px 0 0'
	},
	num: {
		verticalAlign: '2px'
	},
}
