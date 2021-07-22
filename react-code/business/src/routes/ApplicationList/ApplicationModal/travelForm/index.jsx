import React, { Component } from 'react';
import { Row, Col, Button, Steps, Form, Input, Space, Select, Checkbox, DatePicker, Modal, message, Table, Tag } from 'antd';
import { UserOutlined, FormOutlined, DeleteOutlined, PlusOutlined } from '@ant-design/icons';
import moment from 'moment'
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import PubSub from 'pubsub-js'
import '../../style.css'
import TripCheck from './trip-check'
import UserSelect from '../../../Public/userSelect'
//出差申请
const { Step } = Steps;
const { Option } = Select;

@inject('appStore') @observer
export default class TravelForm extends Component {
	state = {
		data: toJS(this.props.appStore.curEnt),
		status:'',
		formData: {},
		history: [],
		limitTime: {},
		addlist: [],
		totalCount: 0,
		totalDays: 0,
		idCounter: 0,//计数器
		tripData: {},
		newItem: 0,//0为新增
		users: [],
		userlist: [],
		namelist: [],
		valiStatus: '',//自定义状态
		valiText: ' ',//自定义提示
		tripStatus: '',//自定义状态
		tripText: ' ',//自定义提示
		tripModal: false,
		userModal: false,
		promise: false,
		columns: [
			{
				title: '日期',
				dataIndex: 'startTime',
				key: 'startTime',
				width: '180px',
				render: (text, record) => <span>{moment(text).format('YYYY-MM-DD')} - {moment(record.endTime).format('YYYY-MM-DD')}</span>
			},
			{
				title: '出发/到达地',
				dataIndex: 'startAddress',
				key: 'startAddress',
				render: (text, record) => <span>{text} - {record.endAddress}</span>,
			},
			{
				title: '行程项目',
				dataIndex: 'vehicle',
				key: 'vehicle',
				render: tags => (
					<span>
						{tags.map(tag => {
							return (
								<Tag color="green" key={tag}>
									{tag.toUpperCase()}
								</Tag>
							);
						})}
					</span>
				),
			},
			{
				title: '预估费用',
				dataIndex: 'budget',
				key: 'budget',
			},
			{
				title: '操作',
				dataIndex: 'action',
				key: 'action',
				render: (text, record) => {
					return (
						(this.state.status==null||!this.state.status)?
						<Space size="middle">
							<FormOutlined onClick={this.editTrip.bind(this, record)} />
							<DeleteOutlined onClick={this.delTrip.bind(this, record)} />
						</Space>:''
					);
				},
			},
		],
	}
	formRef = React.createRef();
	componentDidMount() {
		this.setDefaultData()
		this.getUserlist()
		this.getHistory()
	}
	componentWillUnmount() {
		this.setState = (state, callback) => {
			return;
		};
	}
	typeChoice = (type) => {
		this.props.typeChoice(type);
	}
	setDefaultData = () => {
		if (this.props.defaultData) {
			const { applyTravelEntity, detailList, userList,status } = this.props.defaultData
			applyTravelEntity.startTime = moment(applyTravelEntity.startTime)
			applyTravelEntity.endTime = moment(applyTravelEntity.endTime)
			let arr = userList.map(item => item.firstName + ' ' + item.lastName)
			let detail = this.props.defaultData.detailList
			detail = detail.map(item => {
				item.vehicle = item.vehicle.split(",")
				return item
			})

			this.setState({
				formData: applyTravelEntity,
				addlist: detail,
				totalDays: this.getDays(detailList),
				totalCount: this.getSum(detailList),
				userlist: userList,
				namelist: arr,
				status
			})
			this.formRef.current.setFieldsValue(applyTravelEntity)
		}
	}
	onChange = (o, v) => {
		v = this.handleMoment(v)
		// console.log(v);
		// let time = { startTime: v.startTime, endTime: v.endTime }
		let data = { ...this.state.formData, ...v }
		console.log(data);
		this.setState({ formData: data, })
	}
	checkPromise = (e) => {
		this.setState({ promise: e.target.checked })
	}
	onSubmit = (e) => {
        console.log(e);
        let that = this;
		const { formData, addlist, userlist, promise } = this.state
		const { enterpriseId, userId } = this.state.data
		let triplist = this.formatData(JSON.parse(JSON.stringify(addlist)))
		let form = {
			enterpriseId,
			userId,
			isSubmit: e,
		}
		form = { ...formData, ...form, }
		let obj = {
			applyTravelEntity: form,
			detailList: triplist,
			userList: userlist
		}
		console.log(obj);
		this.selfValidate()
		this.formRef.current.validateFields().then(() => {
			this.selfValidate().then(() => {
				if (promise) {
					if (e == '1') {
						that.props.onSubmit(obj, 2);
					} else {
						this.post(`/detail/apply-travel/insert`, obj, 'application/json').then(res => {
							if (res.code == 1) {
								message.success('提交成功');
								PubSub.publish('refresh', { tabBtn: '2' })
								this.onCancel()
							} else {
								message.error(res.message);
							}
						})
					}

				} else {
					message.error("请勾选承诺");
				}
			})
		}).catch((errorInfo) => {
			message.error("请输入完整信息");
		})
	}
	// 自定义验证
	selfValidate = () => {
		let p = new Promise((resolve, reject) => {
			let status1 = '', status2 = '', text1 = ' ', text2 = ' '
			if (this.state.addlist.length !== 0) {
				resolve()
			} else {
				if (this.state.addlist.length == 0) {
					status1 = 'error'
					text1 = '请添加行程!'
					reject(0)
				}
				if (this.state.userlist.length == 0) {
					status2 = 'error'
					text2 = '请选择人员!'
					reject(0)
				}
			}
			setTimeout(() => {
				this.setState({
					tripStatus: status1,
					tripText: text1,
					valiStatus: status2,
					valiText: text2,
				})
			}, 400);//让效果出现在表单验证后面
		});
		return p
	}
	getUserlist = () => {
		this.post(`/enterprise-user/enterprise-list`, {
			limit: 100,
			page: 1,
			enterpriseId: this.state.data.enterpriseId,
			userId: this.state.data.userId,
		}
		).then(res => {
			if (res.code == 1) {
				this.setState({
					users: res.data.data
				});
			}
		})
	}
	//历史记录
	getHistory = () => {
		if (this.props.defaultData) {
			this.get("/bpm/use/history/records/" + this.props.defaultData.applyTravelEntity.id).then(res => {
				if (res.code === 1) {
					this.setState({
						history: res.data,
					})
				}
			});
		}

	}
	// 关闭自己
	onCancel = () => {
		this.props.onCancel();
	}
	tripAdd = () => {
		const {startTime,endTime}=this.state.formData
		this.setState({
			limitTime:{ startTime, endTime },
			newItem: 0,
			defaultpanel:startTime,
			tripModal: true
		})
	}
	userAdd = () => {
		this.setState({
			userModal: true
		})
	}
	// 关闭添加行程,人员对话框
	handleCancel = () => {
		this.setState({
			tripModal: false,
			userModal: false
		})
	}
	// 添加行程
	addTrip = (data) => {
		let list = [...this.state.addlist]
		let counter = this.state.idCounter
		// 判断是否是修改
		if (data.id !== undefined) {
			list = list.map(item => item.id === data.id ? data : item)
		} else {
			list.push(data)
			data.id = counter
			counter++
		}
		this.setState({
			addlist: list,
			totalDays: this.getDays(list),
			totalCount: this.getSum(list),
			idCounter: counter,
			tripStatus: '',
			tripText: ' '
		})
	}
	// 删除行程
	delTrip = (e) => {
		let list = [...this.state.addlist]
		list = list.filter(item => item.id !== e.id)
		this.setState({
			addlist: list,
			totalDays: this.getDays(list),
			totalCount: this.getSum(list),
		})
	}
	// 修改行程
	editTrip = (e) => {
		const {startTime,endTime}=this.state.formData
		this.setState({
			limitTime:{ startTime, endTime },
			tripData: e,
			newItem: 1,
			tripModal: true
		})
	}
	// 去重
	dedupe = (arr) => {
		return arr.reduce((pre, cur) => {
			let result = pre.some(item => {
				return item.userId == cur.userId
			})
			return !result ? pre.concat(cur) : pre
		}, [])
	}
	// 格式化时间
	handleMoment = (obj) => {
		obj.startTime ? obj.startTime = moment(obj.startTime).format('YYYY-MM-DD HH:mm:ss') : null
		obj.endTime ? obj.endTime = moment(obj.endTime).format('YYYY-MM-DD HH:mm:ss') : null
		return obj
	}
	// 批量处理时间和数组
	formatData = (arr) => {
		return arr = arr.map(item => {
			item = this.handleMoment(item)
			item.vehicle = item.vehicle.toString()
			return item
		})
	}
	// 计算天数
	getDays = (arr) => {
		return arr.reduce((prev, curr) => {
			return prev + days(curr);
		}, 1);

		function days(obj) {
			let start = moment(obj.startTime, "YYYY-MM-DD HH:mm:ss");
			let end = moment(obj.endTime, "YYYY-MM-DD HH:mm:ss");
			let dayCount = end.diff(start, 'days')
			return dayCount
		}
	}
	// 计算金额
	getSum = (arr) => {
		return arr.reduce(function (prev, curr) {
			return prev + Number(curr.budget);
		}, 0);
	}
	userDel = (value) => {
		let arr = [];
		let userlist = this.state.userlist;
		userlist = userlist.filter(item => item.firstName + ' ' + item.lastName !== value)
		arr = userlist.map(item => item.firstName + ' ' + item.lastName)
		this.setState({
			userlist,
			namelist: arr,

		})
	}
	userSubmit = (e) => {
		let arr = [];
		let userlist = this.dedupe([...this.state.userlist, ...e]);
		arr = userlist.map(item => item.firstName + ' ' + item.lastName)
		this.setState({
			userlist,
			namelist: arr,
			userModal: false,
			valiStatus: '',
			valiText: ' ',
		})
	}
	render() {
		const { data, formData, addlist, columns, totalCount, totalDays, tripData, newItem, history,
			namelist, userlist, valiText, valiStatus, tripStatus, tripText, limitTime,defaultpanel } = this.state
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
		const validateStartDate = (_, value) => {
			return new Promise((resolve, reject) => {
				if (!value) {
					reject(new Error('请输入开始时间'))
				}
				else {
					if (value !== '' && this.state.formData.endTime) {
						if (value.unix() <= moment(this.state.formData.endTime).unix()) {
							resolve();
						} else {
							reject(new Error('开始时间不能大于结束时间'))
						}
					}
					resolve();
				}
			})
		}
		const validateEndDate = (_, value) => {
			return new Promise((resolve, reject) => {
				if (!value) {
					reject(new Error('请输入结束时间'))
				}
				else {
					if (value !== '' && this.state.formData.startTime) {
						if (value.unix() >= moment(this.state.formData.startTime).unix()) {
							resolve();
						} else {
							reject(new Error('结束时间不能小于开始时间'))
						}
					}
					resolve();
				}
			})
		}
		return (
			<div>
				<Row>
					<Form style={styles.form} {...formItemLayout} ref={this.formRef} onValuesChange={this.onChange}>
						<Form.Item label='申请名称' name='applyName' rules={[{ required: true, message: '请输入申请名称!' }]}>
							<Input placeholder='请输入申请名称' name='applyName' readOnly={this.state.status==null||!this.state.status?false:true}/>
						</Form.Item>
						<Form.Item required label='时间区间' style={{ marginBottom: 0 }}>
							<Form.Item name='startTime' rules={[{ required: true, validator: validateStartDate }]} style={{ display: 'inline-block' }}>
								<DatePicker disabled={this.state.status==null||!this.state.status?false:true}/>
							</Form.Item>
							<Form.Item style={{ display: 'inline-block', margin: '0 10px' }}>
								-
							</Form.Item>
							<Form.Item name='endTime' rules={[{ required: true, validator: validateEndDate }]} style={{ display: 'inline-block' }}>
								<DatePicker disabled={this.state.status==null||!this.state.status?false:true}/>
							</Form.Item>
						</Form.Item>
						<Form.Item label='行程规划' required validateStatus={tripStatus} help={tripText}>
							{(this.state.status==null||!this.state.status)?<Button type="primary" icon={<PlusOutlined />} onClick={this.tripAdd}>添加行程</Button>:''}
							{
								!totalCount ? null :
									<div className='costList'>
										<Table columns={columns} dataSource={addlist} size="small" pagination={false} style={styles.table} rowKey="id" />
										<Row className="costItem" justify="end">
											<Col span={24} className="count">合计：{totalDays}天，预估费用：${totalCount}</Col>
										</Row>
									</div>
							}
						</Form.Item>
						<Form.Item label='出行人员' required
							validateStatus={valiStatus}
							help={valiText}
						>
							<Select
								mode="multiple"
								style={{ width: '100%' }}
								placeholder="Please select"
								onClick={(this.state.status==null||!this.state.status)?this.userAdd:null}
								open={false}
								value={namelist}
								onDeselect={this.userDel}
							>
							</Select>
						</Form.Item>
						<Form.Item label='说明' name='remark'>
							<Input.TextArea placeholder='请输入说明' readOnly={this.state.status==null||!this.state.status?false:true}/>
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
					title={newItem == 0 ? "添加行程" : "修改行程"}
					style={{ top: '150px' }}
					visible={this.state.tripModal}
					footer={null}
					onCancel={this.handleCancel}
					destroyOnClose={true}
				>
					{
						newItem == 0 ?
							<TripCheck addTrip={this.addTrip} onCancel={this.handleCancel} limitTime={limitTime} defaultpanel={defaultpanel}/> :
							<TripCheck addTrip={this.addTrip} onCancel={this.handleCancel} tripData={tripData} limitTime={limitTime} />
					}
				</Modal>
				<Modal
					width="30%"
					title="选择人员"
					style={{ top: '150px' }}
					visible={this.state.userModal}
					footer={null}
					onCancel={this.handleCancel}
					destroyOnClose={true}
				>
					<UserSelect addTrip={this.addTrip} onCancel={this.handleCancel} entData={data} userSubmit={this.userSubmit} userlist={userlist} />
				</Modal>
			</div>
		);
	}
}



const styles = {
	form: {
		width: '100%',
		marginTop: '20px'
	},
	table: {
		marginTop: '10px',
		marginBottom: '10px'
	}
}
