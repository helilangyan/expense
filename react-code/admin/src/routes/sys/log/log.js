import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import {Input, Form, Button, Table, DatePicker } from 'antd';
import './style.css'


class log extends Component {
    componentDidMount() {
        this.getData(1);

    }

    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            tabData: [],
            columns: [
                {
                    title: '访问账号',
                    className: 'column-money',
                    dataIndex: 'username',
                    width: 100,
                },
                {
                    title: '模块',
                    dataIndex: 'operationUnit',
                    width: 150,
                },
                {
                    title: '操作类型',
                    dataIndex: 'operationType',
                    width: 100,
                },
                {
                    title: '响应时间',
                    dataIndex: 'runTime',
                    width: 150,
                },
                {
                    title: '描述',
                    dataIndex: 'description',
                },
                {
                    title: '访问时间',
                    dataIndex: 'createTime',
                    render: createTime => (
                        <span>
                            {createTime.split('T')[0]}&nbsp;
                            {createTime.split('T')[1].split('.')[0]}
                        </span>
                    ),
                    width: 200,
                },
            ],
            page:1,
            pageSize:10,
            total:10,
            formItemLayout: {
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 10},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 14},
                },
            },
            searchData:{
                username:'',
                startTime:'',
                endTime:'',
            }
        }
        this.addModal = this.addModal.bind(this);
    }
    getData(current){
        let obj= {
            limit: this.state.pageSize, page: current,
            ...this.state.searchData
        }
        console.log(obj);
        this.post(`/log/list`,obj).then(res => {
            console.log(this.state.tabData);
            this.setState({
                tabData: res.data.data,
                total:res.data.count
            });

        })
    }
    searchChange=(e)=>{
        let event = e.target;
        let searchData = this.state.searchData;
        searchData[event.name]=event.value;
        this.setState({
            searchData:searchData
        });
        console.log(searchData);
    }
    startTimeChange=(date, dateString)=> {
        let searchData = this.state.searchData;
        searchData.startTime=dateString;
        this.setState({
            searchData:searchData
        });
        console.log(searchData);
    }
    endTimeChange=(date, dateString)=> {
        let searchData = this.state.searchData;
        searchData.endTime=dateString;
        this.setState({
            searchData:searchData
        });
        console.log(searchData);
    }
    searchReset=()=>{
        let searchData = this.state.searchData;
        searchData={
            username:'',
            startTime:'',
            endTime:'',
        };
        this.setState({
            searchData:searchData
        });
        this.getData(1);
    }
    render() {
        const paginationProps = {
            showSizeChanger: true,//设置每页显示数据条数
            showQuickJumper: false,
            showTotal: () => `共${this.state.total}条`,
            pageSize: this.state.pageSize,
            total: this.state.total,  //数据的总的条数
            onChange: (current) => this.getData(current), //点击当前页码
            onShowSizeChange: (current, pageSize) => {//设置每页显示数据条数，current表示当前页码，pageSize表示每页展示数据条数
                console.log(pageSize);
                this.onShowSizeChange(current, pageSize)
            }
        }
        return (
            <div className='boxCard'>
                <div className="tableHead">
                    <Form layout="inline" onSubmit={this.handleSubmit}>
                        <Form.Item label="操作账号">
                            <Input
                                value={this.state.searchData.username}
                                onChange={this.searchChange}
                                name='username'
                                placeholder="请输入操作账号"
                            />
                        </Form.Item>
                        <Form.Item label="开始时间">
                            <DatePicker onChange={this.startTimeChange} />
                        </Form.Item>
                        <Form.Item label="结束时间">
                            <DatePicker onChange={this.endTimeChange} />
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" onClick={()=>{this.getData(this.state.page)}}>查询</Button>
                            &nbsp; &nbsp;
                            <Button onClick={this.searchReset}>重置</Button>
                        </Form.Item>
                    </Form>
                </div>
                <div className="tableContent">

                    <Table
                        columns={this.state.columns}
                        dataSource={this.state.tabData}
                        rowKey={record => record.id}
                        pagination={paginationProps}
                    />
                </div>
            </div>
        );
    }


    addModal() {
        this.setState({
            visible: true,
        });
    }

    handleOk = e => {
        console.log(e);
        this.setState({
            visible: false,
        });
    };

    handleCancel = e => {
        console.log(e);
        this.setState({
            visible: false,
        });
    };
}


export default log;