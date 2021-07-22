import React, { Component } from 'react'
import { Switch, Redirect, Route } from 'react-router'
import { Row, Col, Tabs, Button, Modal, Image, Checkbox, Space, Drawer } from 'antd';
import { PlusOutlined, FilterFilled, SwapOutlined, UploadOutlined, DeleteOutlined, FileAddOutlined, CopyOutlined } from '@ant-design/icons';
import PubSub from 'pubsub-js'
import './style.css'
import TypeChoice from './typeChoice'
import InOutChoice from './inoutChoice'
import BusinessFilter from './businessFilter'
import PersonFilter from './personFilter'
import Business from './Business'
import Personal from './Personal'
import ApplyList from './Business/reportList'

const { TabPane } = Tabs;

export default class ExpenseList extends Component {
	state = {
		typeModal: false,
		inOutModal: false,
		drawer: false,
		reportModal: false,
		tabBtn: '1',
		moreBtn:0,
	}
	componentDidMount() {
		this.token = PubSub.subscribe('morebtn',(_,data)=>{
			this.showMoreBtn(data)
		})
	}
	componentWillUnmount(){
		PubSub.unsubscribe(this.token)
	}
	// tab切换右上角按钮
	changeTab = (activeKey) => {
    this.setState({
			tabBtn: activeKey,
			moreBtn:0,
		});
  }
	// 打开企业或个人新增
	addItem = () => {
		this.setState({
			typeModal: true
		})
	}
	// 打开过滤
	showDrawer = () => {
		this.setState({
			drawer: true
		})
	}
	// 打开加入申请列表
	addApply = () => {
		this.setState({
			reportModal: true
		})
	}
	// 显示更多按钮,1为单选,2为多选
	showMoreBtn = (n) => {
		this.setState(n)
	}

	handleCancel = () => {
		this.setState({
			typeModal: false,
			inOutModal: false,
			reportModal: false,
			drawer: false,
		})
	}
	// 此事件接收子对象
	childEvevnt = childComp => {
		this.$child = childComp;
	};
	typeChoice = (type) => {
		this.handleCancel()
		this.$child.setExpense(type);
	}
	personEvent = childComp => {
		this.$child = childComp;
	}

	// 过滤列表
	handleFilter = (searchData) => {
		this.handleCancel()
		this.$child.getData(searchData)
	}

	render() {
		const { tabBtn,moreBtn,typeModal,reportModal } = this.state;
		const operations =
			<div>
				{
					moreBtn==1?
					<Space>
						<Button danger type="primary" icon={<DeleteOutlined />} onClick={this.showDrawer}>删除</Button>
						<Button type="primary" icon={<SwapOutlined />} onClick={this.showDrawer}>转换</Button>
						<Button type="primary" icon={<UploadOutlined />} onClick={this.addApply}>导出</Button>
						<Button type="primary" icon={<FileAddOutlined />} onClick={this.addApply}>加入申请</Button>
						<Button type="primary" icon={<CopyOutlined />} onClick={this.addApply}>复制</Button>
					</Space>:
					moreBtn>1?
					<Space>
						<Button danger type="primary" icon={<DeleteOutlined />} onClick={this.showDrawer}>删除</Button>
						<Button type="primary" icon={<UploadOutlined />} onClick={this.addApply}>导出</Button>
						<Button type="primary" icon={<FileAddOutlined />} onClick={this.addApply}>加入申请</Button>
					</Space>:null
				}
				&nbsp;&nbsp;
				<Space>
					<Button type="primary" icon={<FilterFilled />} onClick={this.showDrawer}>Filter</Button>
					<Button type="primary" icon={<PlusOutlined />} onClick={this.addItem}>添加</Button>
				</Space>
			</div>
		return (
			<div className="headBox">
				<Row>
					<Col span={24}>
						<Tabs activeKey={tabBtn} onTabClick={(key) => this.changeTab(key)} tabBarExtraContent={operations}>
							<TabPane tab="Business Expenses" key="1">
								{tabBtn=='1'?<Business childEvevnt={this.childEvevnt} />:''}
							</TabPane>
							<TabPane tab="Personal income & expenditure" key="2">
								{tabBtn=='2'?<Personal personEvent={this.personEvent} />:''}
							</TabPane>
						</Tabs>
					</Col>
				</Row>
				<Modal
					width="30%"
					title="加入申请"
					visible={reportModal}
					footer={null}
					onCancel={this.handleCancel}
					destroyOnClose={true}
				>
					<ApplyList onCancel={this.handleCancel} /> :
				</Modal>
				<Modal
					width={tabBtn == 1 ? "50%" : "30%"}
					title={tabBtn == 1 ? "申请类型" : "选择收支"}
					visible={typeModal}
					footer={null}
					onCancel={this.handleCancel}
					destroyOnClose={true}
				>
					{
						tabBtn == 1 ?
							<TypeChoice typeChoice={this.typeChoice} onCancel={this.handleCancel} /> :
							<InOutChoice typeChoice={this.typeChoice} onCancel={this.handleCancel} />
					}
				</Modal>
				<Drawer
					title="筛选"
					width={550}
					onClose={this.handleCancel}
					visible={this.state.drawer}
					bodyStyle={{ paddingBottom: 80 }}
				>
					{
						tabBtn == 1 ? <BusinessFilter handleFilter={this.handleFilter} onCancel={this.handleCancel}/> : <PersonFilter />
					}
				</Drawer>
			</div>

		)
	}
}
