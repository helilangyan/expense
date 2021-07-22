import React, {Component} from 'react';
import {Tree, Spin, Button} from 'antd';

const {TreeNode} = Tree;

let treeData = [];
let stateCheck = [];

class authorityInput extends Component {
    componentDidMount() {
        this.getTreeData();
    }

    constructor(props) {
        super(props);
        this.state = {
            treeData: [],
            checkedKeysData: []
        }
    }

    submitClick = () => {
        this.props.submitAdd(this.state.checkedKeysData);
    }
    onCancel = () => {
        this.props.onCancel();
    }

    getTreeData() {
        this.post(`/resource/resource-tree`).then(res => {
            if (res.code == 1) {
                this.setState({
                    treeData: res.data
                });
            }
        })
    }

    onSelect = (selectedKeys, info) => {
        console.log('selected', selectedKeys, info);
    };
    onCheck = (checkedKeys, info) => {
        this.setState({
            checkedKeysData: checkedKeys
        })
    };
    renderTreeNodes = data =>
        data.map(item => {
            if (item.children) {
                return (
                    <TreeNode title={item.resourceName} key={item.id} dataRef={item}>
                        {this.renderTreeNodes(item.children)}
                    </TreeNode>
                );
            } else {
                return <TreeNode key={item.id} title={item.resourceName}/>;
            }

        });

    render() {

        return (
            <div>
                <Spin spinning={!this.state.treeData.length}>
                    <div style={{height: '500px',overflowY:'auto'}}>
                        {
                            this.state.treeData.length ?
                                <Tree
                                    checkable
                                    defaultCheckedKeys={this.props.resourceData}
                                    defaultExpandAll={true}
                                    onCheck={this.onCheck}
                                    onSelect={this.onSelect}
                                    height={233}
                                >
                                    {
                                        this.renderTreeNodes(this.state.treeData)
                                    }
                                </Tree>
                                : null
                        }
                    </div>
                </Spin>

                <div>
                    {/*<Row>*/}
                    {/*<Col span={24} style={{textAlign:'center',marginTop:"20px"}}>*/}
                    {/**/}
                    {/*</Col>*/}
                    {/*</Row>*/}
                    <Button type="primary" onClick={this.submitClick}>确定</Button>
                    &nbsp; &nbsp;
                    <Button onClick={this.onCancel}>取消</Button>
                </div>
            </div>
        );
    }

}

export default authorityInput;