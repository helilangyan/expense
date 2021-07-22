import React, { Component } from 'react';
import { Row, Col, Button, Steps, Form, Input, Space, Select, Checkbox, Modal, Tooltip, message } from 'antd';
import { UserOutlined, CloseOutlined, FileTextOutlined, FormOutlined, PlusOutlined } from '@ant-design/icons';
import moment from 'moment'
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import { utc2bj } from '../../../../utils/UTCtoBJ'


import Expense from './expenselist';
import Trip from './triplist';
import Loan from './loanlist';

import '../../style.css'
import PubSub from "pubsub-js";
//报销申请
const { Step } = Steps;
const { Option } = Select;

@inject('appStore') @observer
class typeChoice extends Component {
	state = {
		data: toJS(this.props.appStore.curEnt),
		status: '',
		listModal: false,
		listType: 0,
		formData: {},
		history: [],
		promise: '',
		valiStatus: '',//自定义状态
		valiText: ' ',//自定义提示
		// 导入的列表
		cardlist: [],
		// addlist: [],
		prelist: [],
		// borrowlist: [],
		// travellist: [],
		// 报销金额
		expenseCount: 0,
		applyCount: 0,
		// 提交列表id
		detailIds: [],
		travelIds: [],
		borrowIds: [],
	}
	formRef = React.createRef()

	componentDidMount() {
		this.getCards()
		this.getHistory()
		this.setDefaultData()
		console.log(this.props.defaultData);
	}
	componentWillUnmount() {
		this.setState = (state, callback) => {
			return;
		};
	}
	onChange = (e, v) => {
		let data = { ...this.state.formData, ...v }
		this.setState({
			formData: data,
		})
	}
	openList = (e) => {
		this.setState({
			listModal: true,
			listType: e
		})
	}
	// 添加费用
	addList = (arr, data) => {
		console.log(data);
		const { listType, addlist, travellist, borrowlist } = this.state
		let list1 = addlist, list2 = travellist, list3 = borrowlist;

		// 导入并去重
		listType == 1 ?
			list1 = dedupe([...data, ...list1]) :
			listType == 2 ?
				list2 = dedupe([...data, ...list2]) :
				list3 = dedupe([...data, ...list3]);

		function dedupe(arr) {
			return arr.reduce((pre, cur) => {
				let result = pre.some(item => {
					return item.id == cur.id
				})
				return !result ? pre.concat(cur) : pre
			}, [])
		}
		let money = this.getSum(list1);
		let money2 = this.getSum(list3);
		this.setState({
			addlist: list1,
			travellist: list2,
			borrowlist: list3,
			expenseCount: money,
			applyCount: money - money2,
		})
	}

	// 计算金额
	getSum = (arr) => {
		if(arr.length!==0){
			return arr.reduce(function (prev, curr) {
				return prev + Number(curr.money);
			}, 0);
		}
	}
	// 获取Id
	getIds = (arr) => {
		let ids = [];
		arr.map(item => {
			ids.push(item.id)
		})
		return ids
	}
	// 删除导入
	delList = (arr, e, type) => {
		arr.map((item, indx) => {
			item.id == e.id ? arr.splice(indx, 1) : null
			let count = this.getSum(this.state.addlist) - this.getSum(this.state.borrowlist)
			type == 1 ?
				this.setState({ addlist: arr, expenseCount: this.getSum(arr), applyCount: count }) :
				type == 2 ?
					this.setState({ travellist: arr }) :
					this.setState({ borrowlist: arr, applyCount: count })
		})
	}
	checkPromise = (e) => {
		this.setState({ promise: e.target.checked })
	}
	setDefaultData = () => {
		if (this.props.defaultData) {
			const { applyExpenseEntity, detailList, travelList, borrowList, status } = this.props.defaultData
			let addlist = [], borrowlist = [], travellist = [];
			addlist = detailList.filter(item=>{
				if(item){
					return item.giveDate = utc2bj(item.giveDate).slice(0, -9)
				}
			})
			borrowlist = borrowList.filter(item=>{
				if(item){
					return item.createTime = utc2bj(item.createTime)
				}
			})
			travellist = travelList.filter(item=>{
				if(item){
					item.startTime = utc2bj(item.startTime).slice(0, -9)
					item.endTime = utc2bj(item.endTime).slice(0, -9)
					return item
				}
			})
			let money = this.getSum(addlist);
			let money2 = this.getSum(borrowlist);
			this.setState({
				formData: applyExpenseEntity,
				addlist,
				travellist,
				borrowlist,
				status,
				expenseCount: money,
				applyCount: money - money2,
			})
			this.formRef.current.setFieldsValue(applyExpenseEntity)
		}
	}
	// 自定义验证
	selfValidate = () => {
		let p = new Promise((resolve, reject) => {
			let status = '', text = ' ';
			if (this.state.addlist.length > 0) {
				resolve()
			} else {
				status = 'error'
				text = '请添加费用清单!'
				reject(0)
			}
			setTimeout(() => {
				this.setState({
					valiStatus: status,
					valiText: text
				})
			}, 400);//让效果出现在表单验证后面
		});
		return p
	}
	onSubmit = (e) => {
		const { data, formData, addlist, travellist, borrowlist, applyCount, promise } = this.state
		let form = {
			money: applyCount,
			isSubmit: e,
			enterpriseId: data.enterpriseId,
			id: 0
		}
		let subData = {
			applyExpenseEntity: { ...form, ...formData },
			detailIds: this.getIds(addlist),
			travelIds: this.getIds(travellist),
			borrowIds: this.getIds(borrowlist)
		}
		this.selfValidate()
		this.formRef.current.validateFields().then(() => {
			this.selfValidate().then(() => {
				if (promise) {
					if (e == '1') {
						this.props.onSubmit(subData, 1);
					} else {
						this.post(`/detail/apply-expense/insert`, subData, 'application/json').then(res => {
							if (res.code == 1) {
								message.success((data.id !== 0 ? '修改' : '添加') + res.msg)
								this.onCancel();
								PubSub.publish('refresh', { tabBtn: '1' })
							} else {
								message.error(res.msg);
							}
						})
					}

				} else {
					message.error("请勾选承诺");
				}
			})
		}).catch((errorInfo) => {
			console.log(errorInfo);
			message.error("请输入完整信息");
		})
	}
	getCards = () => {
		let obj = {
			limit: 100,
			page: 1,
			enterpriseId: this.state.data.enterpriseId
		}
		this.post("/enterprise/bank/list", obj).then(res => {
			if (res.code === 1) {
				this.setState({
					cardlist: res.data.data,
				})
			}
		});
	}
	//历史记录
	getHistory = () => {
		if (this.props.defaultData) {
			this.get("/bpm/use/history/records/" + this.props.defaultData.applyExpenseEntity.id).then(res => {
				if (res.code === 1) {
					this.setState({
						history: res.data,
					})
				}
			});
		}

	}
	onCancel = () => {
		this.props.onCancel();
	}
	onCancelChild = () => {
		this.setState({ listModal: false })
	}

	render() {
		const { listModal, formData, listType, cardlist, addlist, travellist, borrowlist, expenseCount,
			applyCount, valiText, valiStatus, history } = this.state;
		const formItemLayout = {
			labelCol: {
				xs: { span: 24 },
				sm: { span: 4 },
			},
			wrapperCol: {
				xs: { span: 24 },
				sm: { span: 19 },
			},
		}
		return (
			<div>
				<Row>
					<Form style={{ width: '100%', marginTop: '20px' }} {...formItemLayout} onValuesChange={this.onChange} ref={this.formRef}>
						<Form.Item label='申请名称' name='applyName'
							rules={[{ required: true, message: "请输入申请名称" }]}>
							<Input placeholder='请输入申请名称' readOnly={this.state.status == null || !this.state.status?false:true}/>
						</Form.Item>
						<Form.Item label='说明' name='remark'>
							<Input.TextArea placeholder='请输入说明' readOnly={this.state.status == null || !this.state.status?false:true}/>
						</Form.Item>
						<Form.Item label='添加费用' required validateStatus={valiStatus} help={valiText}>
							{(this.state.status == null || !this.state.status) ?<Button type="primary" style={{marginBottom:'10px'}} icon={<PlusOutlined />} onClick={() => this.openList(1)}>费用清单导入</Button>:''}
							<div className='costList'>
								{
									(addlist&&addlist!==null)?addlist.map(item => {
										return (
											<Row align="middle" className="costItem" key={item.id}>
												<Tooltip placement="top" title={item.expenseName.length > 14 ? item.expenseName : ''}>
													<Col span={7} className="title"><FileTextOutlined /> {item.expenseName}</Col>
												</Tooltip>
												<Col span={8}>{item.giveDate}</Col>
												<Col span={4} offset={4} className="num">{item.money}</Col>
												{
													(this.state.status == null || !this.state.status) ?
														<Col span={1} onClick={() => this.delList(addlist, item, 1)}><CloseOutlined /></Col> : ''
												}
											</Row>
										)
									}):''
								}
								{
									!expenseCount ? null :
										<Row className="costItem" justify="end">
											<Col span={6} className="count">$ {expenseCount}</Col>
										</Row>
								}
							</div>
						</Form.Item>
						<Form.Item label='前置申请'>
						{(this.state.status == null || !this.state.status) ?
							<Space style={{marginBottom:'10px'}}>
								<Button type="primary" icon={<FormOutlined />} onClick={() => this.openList(2)}>出差申请导入</Button>
								<Button type="primary" icon={<FormOutlined />} onClick={() => this.openList(3)}>借款申请导入</Button>
							</Space>:''}
							<div className='costList'>
								{
										(travellist&&travellist!==null)?travellist.map(item => {
										return (
											<Row align="middle" className="costItem" key={item.id}>
												<Tooltip placement="top" title={item.applyName.length > 14 ? item.applyName : ''}>
													<Col span={7} className="title">{item.applyName}</Col>
												</Tooltip>
												<Col span={8}>{item.createTime}</Col>
												<Col span={4}> <div className="rnd-pl">已批准</div></Col>
												{/* <Col span={4} className="num">{item.money}</Col> */}
												{
													(this.state.status == null || !this.state.status) ?
														<Col span={1} offset={4} onClick={() => this.delList(travellist, item, 2)}><CloseOutlined /></Col> : ''
												}
											</Row>
										)
									}):''
								}
								{
									(borrowlist&&borrowlist!==null)?borrowlist.map(item => {
										return (
											<Row align="middle" className="costItem" key={item.id}>
												<Tooltip placement="top" title={item.applyName.length > 14 ? item.applyName : ''}>
													<Col span={7} className="title">{item.applyName}</Col>
												</Tooltip>
												<Col span={8}>{item.createTime}</Col>
												<Col span={4}> <div className="rnd-pl">已批准</div></Col>
												<Col span={4} className="num">{item.money}</Col>
												{
													(this.state.status == null || !this.state.status) ?
														<Col span={1} onClick={() => this.delList(borrowlist, item, 3)}><CloseOutlined /></Col> : ''
												}
											</Row>
										)
									}):''
								}
								{
									!applyCount ? null :
										<Row className="costItem" justify="end">
											<Col span={6} className="count">报销金额：$ {applyCount}</Col>
										</Row>
								}
							</div>
						</Form.Item>
						<Form.Item label='收款账号' name="bankId" 
							rules={[{ required: true, message: "请选择收款账号" }]}>
							<Select disabled={this.state.status == null || !this.state.status?false:true}	>
								{
									!cardlist ? null : cardlist.map((item) => {
										return <Option key={item.id} value={item.id}>{item.bankName} - {item.bankAccount.slice(0, 4)} ******** {item.bankAccount.slice(-4)}</Option>
									})
								}
							</Select>
						</Form.Item>
						{
							history.length !== 0 ?
								<Form.Item label='历史记录'>
									{
										history.map((item, index) => {
											return <p className='recordList' key={index}>
												<span className="time">{item.arriveTime}</span>
												<span className="username">{item.nextUsername}</span>
												<span className="approve">{item.approve}</span>
												<span>{item.stepName}</span>
												<span>意见：{item.comment ? item.comment : ''}</span>
											</p>
										})
									}
								</Form.Item> :
								''}
					</Form>
				</Row>
				<div className="promise">
					<Checkbox onChange={this.checkPromise.bind(this)}>我承诺填报数据真实无误，如有错误，愿意承担相应的法律责任！</Checkbox>
				</div>
				<Row>
					<Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
						{
							formData.isSubmit == 1 ? <Button onClick={this.onCancel}>关闭</Button>
								: <Space>
									<Button type="primary" onClick={() => this.onSubmit(1)}>提交审批</Button>
									<Button onClick={() => this.onSubmit(0)}>存为草稿</Button>
								</Space>
						}
					</Col>
				</Row>
				<Modal
					width="40%"
					title="选择费用单"
					visible={listModal}
					footer={null}
					onCancel={this.onCancelChild}
					destroyOnClose={true}
				>
					{
						listType == 1 ?
							<Expense onCancel={this.onCancelChild} addList={this.addList}></Expense> :
							listType == 2 ?
								<Trip onCancel={this.onCancelChild} addList={this.addList}></Trip> :
								<Loan onCancel={this.onCancelChild} addList={this.addList}></Loan>
					}
				</Modal>
			</div>
		);
	}
}

export default typeChoice;
