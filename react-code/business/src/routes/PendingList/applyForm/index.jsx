import React, { Component } from 'react';
import { Row, Col, Button, Steps, Form, Input, Space, Select, Checkbox, Modal, Tooltip, message } from 'antd';
import { UserOutlined, CloseOutlined, FileTextOutlined, FormOutlined, PlusOutlined } from '@ant-design/icons';
import moment from 'moment'
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import { utc2bj } from '../../../utils/UTCtoBJ'


import Expense from './expenselist';
import Trip from './triplist';
import Loan from './loanlist';

import '../style.css'
//报销申请
const { Step } = Steps;
const { Option } = Select;

@inject('appStore') @observer
class typeChoice extends Component {
	state = {
		data: toJS(this.props.appStore.curEnt),
		listModal: false,
		listType: 0,
		formData: {},
		history:[],
		promise: '',
		valiStatus: '',//自定义状态
		valiText: ' ',//自定义提示
		// 导入的列表
		cardlist: [],
		addlist: [],
		prelist: [],
		borrowlist: [],
		travellist: [],
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
		return arr.reduce(function (prev, curr) {
			return prev + Number(curr.money);
		}, 0);
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
			const { applyExpenseEntity, detailList, travelList, borrowList } = this.props.defaultData
			let addlist = [], borrowlist = [], travellist = [];
			addlist = detailList.filter(item => item.giveDate = utc2bj(item.giveDate).slice(0, -9))
			borrowlist = borrowList.filter(item => {
				if (item) {
					item.dueDate = utc2bj(item.dueDate).slice(0, -9)
					return item
				}
			})
			travellist = travelList.filter(item => {
				if (item) {
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
        this.props.onSubmit();
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
		this.get("/bpm/use/history/records/" + this.props.businessKey).then(res => {
			if (res.code === 1) {
				this.setState({
					history: res.data,
				})
			}
		});
	}
	onCancel = () => {
		this.props.onCancel();
	}
	onCancelChild = () => {
		this.setState({ listModal: false })
	}

	render() {
		const { listModal, listType, cardlist, addlist, travellist, borrowlist, expenseCount,history,
			applyCount, valiText, valiStatus } = this.state;
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
							<Input placeholder='请输入申请名称' readOnly />
						</Form.Item>
						<Form.Item label='说明' name='remark'>
							<Input.TextArea placeholder='请输入说明' readOnly />
						</Form.Item>
						<Form.Item label='添加费用' required validateStatus={valiStatus} help={valiText}>
							<div className='costList'>
								{
									addlist.map(item => {
										return (
											<Row align="middle" className="costItem" key={item.id}>
												<Tooltip placement="top" title={item.expenseName.length > 14 ? item.expenseName : ''}>
													<Col span={7} className="title"><FileTextOutlined /> {item.expenseName}</Col>
												</Tooltip>
												<Col span={8}>{item.giveDate}</Col>
												<Col span={4} offset={4} className="num">{item.money}</Col>
											</Row>
										)
									})
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
							<div className='costList'>
								{
									travellist.map(item => {
										return (
											<Row align="middle" className="costItem" key={item.id}>
												<Tooltip placement="top" title={item.applyName.length > 14 ? item.applyName : ''}>
													<Col span={7} className="title">{item.applyName}</Col>
												</Tooltip>
												<Col span={8}>{item.startTime} - {item.endTime}</Col>
												<Col span={4}> <div className="rnd-pl">已批准</div></Col>
												<Col span={4} className="num">{item.money}</Col>
											</Row>
										)
									})
								}
								{
									borrowlist.map(item => {
										return (
											<Row align="middle" className="costItem" key={item.id}>
												<Tooltip placement="top" title={item.applyName.length > 14 ? item.applyName : ''}>
													<Col span={7} className="title">{item.applyName}</Col>
												</Tooltip>
												<Col span={8}>{item.dueDate}</Col>
												<Col span={4}> <div className="rnd-pl">已批准</div></Col>
												<Col span={4} className="num">{item.money}</Col>
											</Row>
										)
									})
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
							<Select disabled>
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
				<Row>
					<Col span={24} style={{ textAlign: 'center', marginTop: "20px" }}>
                        <Space>
                            {
                                this.props.seeType=='see'?'': <Button type="primary" onClick={() => this.onSubmit(1)}>提交审批</Button>
                            }
                            <Button onClick={() => this.onCancel()}>关闭</Button>
                        </Space>
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
