import React, { Component } from 'react'
import CustomMenu from "../CustomMenu/index";
import { HomeOutlined,DollarCircleOutlined, FileTextOutlined,FormOutlined,TeamOutlined, LineChartOutlined,BankOutlined,UserOutlined } from '@ant-design/icons';

const menus = [
    {
        title: 'Home Page',
        icon: <HomeOutlined />,
        key: '/home'
    },
    {
        title: 'Expense List',
        icon: <DollarCircleOutlined />,
        key: '/home/expenseList',
    },
    {
        title: 'Application List',
        icon: <FileTextOutlined />,
        key: '/home/application-list',
    },
    {
        title: 'Pending Approval List',
        icon: <FormOutlined />,
        key: '/home/pending-approval-list',
    },
    {
        title: '第三方服务',
        icon: <TeamOutlined />,
        key: '/third-party-services',
        subs: [
            {key: '/third-party-services/my-application', title: '我的申请', icon: '',},
            {key: '/third-party-services/application-statistics', title: '申请统计', icon: '',},
            {key: '/third-party-services/settlement', title: '结算管理', icon: '',},
            {key: '/third-party-services/order-receiving-workbench', title: '接单工作台', icon: '',},
            {key: '/third-party-services/order-receiving-statistics', title: '接单统计', icon: '',},
            {key: '/third-party-services/settlement2', title: '结算管理', icon: '',},
            {key: '/third-party-services/financial-statistics', title: '财务统计', icon: '',},
        ]
    },
    {
        title: 'Reporting Center',
        icon: <LineChartOutlined />,
        key: '/reportin- center',
        subs: [
            {key: '/reporting/personal-reporting', title: 'Personal Reporting', icon: '',},
            {key: '/reporting/enterprise-reporting', title: 'Enterprise Reporting', icon: '',},
        ]
    },
    {
        title: 'Enterprise Setup',
        icon: <BankOutlined />,
        key: '/enterprise',
        subs: [
            {key: '/enterprise/enterprise-info', title: '企业信息设置', icon: '',},
            {key: '/enterprise/account', title: '企业账户设置', icon: '',},
            {key: '/enterprise/declare', title: '代填报设置', icon: '',},
            {key: '/enterprise/declare2', title: '代填报设置', icon: '',},
            {key: '/enterprise/member', title: '成员管理', icon: '',},
            {key: '/enterprise/project', title: '项目设置', icon: '',},
            {key: '/enterprise/role', title: '角色设置', icon: '',},
            {key: '/enterprise/position', title: '职位设置', icon: '',},
            {key: '/enterprise/approval', title: '审批流程设置', icon: '',},
            {key: '/enterprise/strategy', title: '费用策略设置', icon: '',},
            {key: '/enterprise/connect', title: '第三方连接设置', icon: '',},
            {key: '/enterprise/service', title: '服务购买', icon: '',},
        ]
    },
    {
        title: 'Personal Setup',
        icon: <UserOutlined />,
        key: '/user',
        subs: [
            {key: '/user/personal-info', title: '个人信息', icon: '',},
            {key: '/user/income-expenditure', title: '收支设置', icon: '',},
        ]
    },

]


class SiderNav extends React.Component {
    render() {
        return (
            <div style={{height: '100vh', overflowY: 'scroll'}}>
                <div style={styles.logo}><img src={require('../../assets/img/u7527.png')} alt="" style={styles.img}/></div>
                <CustomMenu menus={menus}/>
            </div>
        )
    }
}

const styles = {
    logo: {
        height: '50px',
        background: '#fff',
        margin: '10px',
        color:'#333',
        textAlign:'center',
        lineHeight:'50px',
        fontSize:'28px',
        // borderBottom:'1px solid rgb(198, 217, 234)'
    },
    img: {
        height: '100%',
    }
}

export default SiderNav
