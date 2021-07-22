import React, { Component } from 'react';
import { Checkbox ,Button  } from 'antd';
import {message} from "antd/lib/index";


class authoritySet extends Component {
    componentDidMount() {
        this.getAuthData();
        let authorityList=this.props.authorityList;
        let checkData=[];
        authorityList.map((item,index)=>{
            if (item.roleId) {
                checkData.push(item.authorityId)
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
            checkedKeysData:[]
        }
    }
    getAuthData(){
        this.post(`/authority/list`, {limit:100,page:1}).then(res => {
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
        this.props.submitAdd(this.state.checkedKeysData);
    }
    onCancel=()=>{
        this.props.onCancel();
    }
    authChange=(event)=>{
        this.setState({
            checkedKeysData:event
        });
    }
    render() {

        return (
            <div>
                <div style={{marginBottom:'30px'}}>
                    {
                        this.state.authorityList.length ?
                            <Checkbox.Group style={{ width: '100%' }} onChange={this.authChange} defaultValue={this.state.checkData}>
                                {
                                    this.state.authorityList.map((item,index)=>{
                                        return (
                                            <Checkbox key={index} value={item.id} disabled={this.state.checked}>{item.authorityName}</Checkbox>
                                        )
                                    })
                                }
                            </Checkbox.Group>
                            : null
                    }
                </div>
               <div>
                   <Button type="primary" onClick={this.submitClick}>确定</Button>
                   &nbsp; &nbsp;
                   <Button onClick={this.onCancel}>取消</Button>
               </div>
            </div>
         );
    }

}

export default authoritySet;