import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import { Table, Tag, Space, Card,Pagination  } from 'antd';

const dataSource = [
    {
        key: '1',
        name: 'John Brown',
        money: '￥300,000.00',
        address: 'New York No. 1 Lake Park',
    },
    {
        key: '2',
        name: 'Jim Green',
        money: '￥1,256,000.00',
        address: 'London No. 1 Lake Park',
    },
    {
        key: '3',
        name: 'Joe Black',
        money: '￥120,000.00',
        address: 'Sidney No. 1 Lake Park',
    },
];

const columns = [
    {
        title: 'Name',
        dataIndex: 'name',
        render: text => <a>{text}</a>,
    },
    {
        title: 'Cash Assets',
        className: 'column-money',
        dataIndex: 'money',
        align: 'right',
    },
    {
        title: 'Address',
        dataIndex: 'address',
    },
    {
        title: 'operation',
        dataIndex: 'operation',
        render: (text, record) => {
            return (
                <a onClick={Delete}>Delete</a>
            )
        }
    }
];
function Delete (){
    alert('123456')
}
class company extends Component {
    state = {}
    render() {
        return (
            <div>
                <Card bordered={false} title='基本用法' style={{ marginBottom: 10 }} id='basicUsage'>
                    <Table dataSource={dataSource} columns={columns} bordered style={styles.tableStyle} />
                </Card>
                <Pagination defaultCurrent={1} total={500}  
                showSizeChanger onShowSizeChange={onShowSizeChange}/>
            </div>
        );
    }
  
}

function onShowSizeChange(current, pageSize) {
    console.log(current, pageSize);
  }

const styles = {
    tableStyle: {
        width: '100%'
    },
    affixBox: {
        position: 'absolute',
        top: 100,
        right: 50,
        with: 170
    }
}

export default company;