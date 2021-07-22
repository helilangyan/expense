import React, { Component } from 'react';
import { Checkbox ,Button,Transfer,Switch   } from 'antd';
import {message} from "antd/lib/index";


const mockData = [];
for (let i = 0; i < 20; i++) {
    mockData.push({
        key: i.toString(),
        title: `content${i + 1}`,
        description: `description of content${i + 1}`,
        disabled: i % 3 < 1,
    });
}
const oriTargetKeys = mockData.filter(item => +item.key % 3 > 1).map(item => item.key);

class roleSet extends Component {
    componentDidMount() {
        this.getAuthData();
        let authorityList=this.props.authorityList;
        let checkData=[];
        authorityList.map((item,index)=>{
            if (item.userId && item.userRoleId) {
                checkData.push(item.roleId)
            }
        })
        this.setState({
            checkData: checkData
        });
    }
    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            authorityList: [],
            checkData: [],
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
            checked: false,
            checkedKeysData:[],
            targetKeys: oriTargetKeys,
            selectedKeys: [],
            disabled: false,
        }
    }
    getAuthData(){
        this.post(`/role/list`, {limit:100,page:1}).then(res => {
            if (res.code == 1) {
                this.setState({
                    authorityList:res.data.data
                });
            } else {
                message.error(res.msg)
            }

        })
    }
    submitClick=()=>{
        this.props.submitAdd(this.state.checkData);
    }
    onCancel=()=>{
        this.props.onCancel();
    }
    handleChange = (nextTargetKeys, direction, moveKeys) => {
        //移动
        this.setState({ checkData: nextTargetKeys });
    };
    handleSelectChange = (sourceSelectedKeys, targetSelectedKeys) => {
        //勾选
        this.setState({ selectedKeys: [...sourceSelectedKeys, ...targetSelectedKeys] });
    };
    render() {
        return (
            <div>
                <div>
                    <Transfer
                        dataSource={this.state.authorityList}
                        titles={['可选角色', '已选角色']}
                        targetKeys={this.state.checkData}
                        selectedKeys={this.state.selectedKeys}
                        onChange={this.handleChange}
                        onSelectChange={this.handleSelectChange}
                        rowKey={record => record.id}
                        render={item => item.roleName}
                        listStyle={{
                            width: 200,
                            height: 400,
                        }}
                    />
                </div>
                <div style={{marginTop:'20px'}}>
                    <Button type="primary" onClick={this.submitClick}>确定</Button>
                    &nbsp; &nbsp;
                    <Button onClick={this.onCancel}>取消</Button>
                </div>
            </div>
        );
    }

}

export default roleSet;