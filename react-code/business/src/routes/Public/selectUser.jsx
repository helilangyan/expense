import React, {Component} from 'react';
import {Card, Row, Col, Tree, Radio, Space, Button, Checkbox} from 'antd';

const {TreeNode} = Tree;

class selectUser extends Component {
    componentDidMount() {
        this.getData(1);
        this.getMemberData();
    }

    constructor(props) {
        super(props);
        this.state = {
            tabData: [],
            memberData: [],
            checkUser: []
        }
    }

    getData() {
        this.post(`/enterprise/department/findByEnterpriseId`, {enterpriseId: '3',}).then(res => {
            if (res.code == 1) {
                this.setChildren(res.data);
                let dataObj = this.setChildren(res.data);
                this.setState({
                    tabData: dataObj
                });
            }
        })
    }

    getMemberData(id) {
        this.post(`/enterprise/department/findUsers`, {
            departmentId: id,
            enterpriseId: 3
        }).then(res => {
            if (res.code == 1) {
                this.setState({
                    memberData: res.data
                })
            }
        })
    }

    setChildren(list, parentId = 0) {
        list.forEach(item => {
            item.title = item.departmentName;
            item.key = item.id
        })
        let parentObj = {};
        list.forEach(o => {
            parentObj[o.id] = o;
        })
        if (!parentId) {
            return list.filter(o => !parentObj[o.parentId]).map(o => (o.children = this.setChildren(list, o.id), o));
        } else {
            return list.filter(o => o.parentId == parentId).map(o => (o.children = this.setChildren(list, o.id), o));
        }
    }

    renderTreeNodes = data =>
        data.map(item => {
            if (item.children) {
                return (
                    <TreeNode title={item.departmentName} key={item.id} dataRef={item}>
                        {this.renderTreeNodes(item.children)}
                    </TreeNode>
                );
            } else {
                return <TreeNode key={item.id} title={item.departmentName}/>;
            }

        });

    userChange = (checkedValues) => {
        // console.log(checkedValues);
        this.setState({
            checkUser: checkedValues
        })
    }
    submitClick = () => {
        this.props.submitAdd(this.state.checkUser);
    }
    onCancel = () => {
        this.props.onCancel();
    }

    render() {
        const onSelect = (selectedKeys, info) => {
            console.log('selected', selectedKeys, info);
            this.getMemberData(selectedKeys[0]);
        };
        return (
            <div>
                <Row gutter={16}>
                    <Col span={12}>
                        <Card style={styles.userCard}>
                            <Tree
                                blockNode={true}
                                showLine={true}
                                defaultExpandAll={true}
                                onSelect={onSelect}
                                treeData={this.state.tabData}
                            >
                                {/*{*/}
                                {/*this.renderTreeNodes(this.state.tabData)*/}
                                {/*}*/}

                            </Tree>
                        </Card>
                    </Col>
                    <Col span={12}>
                        <Card style={styles.userCard}>
                            <p>可选人员</p>
                            {
                                this.state.memberData.length ? <Checkbox.Group onChange={this.userChange}>
                                    <Space direction="vertical" value={this.state.checkUser}>
                                        {
                                            this.state.memberData.map((item, index) => {
                                                return (
                                                    <Checkbox key={index}
                                                              value={item}>{item.firstName} {item.lastName}</Checkbox>
                                                )
                                            })
                                        }
                                    </Space>
                                </Checkbox.Group> : ''
                            }
                        </Card>
                    </Col>
                </Row>
                <Row>
                    <Col span={24} style={{textAlign: 'center', marginTop: "20px"}}>
                        <Button type="primary" onClick={this.submitClick}>确定</Button>
                        &nbsp; &nbsp;
                        <Button onClick={this.onCancel}>取消</Button>
                    </Col>
                </Row>
            </div>
        );
    }
}

const styles = {
    userCard: {
        height: '300px',
        width: '100%',
        overflow: 'auto'
    }
}
export default selectUser;
