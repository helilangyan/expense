import React from 'react'
import CustomMenu from "../CustomMenu/index";

const menus = [
    {
        title: '首页',
        icon: 'home',
        key: '/home'
    },
    {
        title: '系统设置',
        icon: 'setting',
        key: '/home/sys',
        subs: [
            {key: '/home/sys/user', title: '用户管理', icon: '',},
            {key: '/home/sys/role', title: '角色管理', icon: '',},
            {key: '/home/sys/authority', title: '权限设置', icon: '',},
            {key: '/home/sys/resource', title: '资源管理', icon: '',},
            {key: '/home/sys/log', title: '操作日志', icon: '',},
            // {key: '/home/sys/parameter', title: '参数设置', icon: '',},
        ]
    },
    {
        title: '客户管理',
        icon: 'setting',
        key: '/home/customer',
        subs: [
            {key: '/home/customer/enterprise', title: '企业管理', icon: '',},
            {key: '/home/customer/order', title: '订单管理', icon: '',},
            {key: '/home/customer/user', title: '用户管理', icon: '',},
        ]
    },

]


class SiderNav extends React.Component {
    render() {
        return (
            <div style={{height: '100vh', overflowY: 'scroll'}}>
                <div style={styles.logo}>logo</div>
                <CustomMenu menus={menus}/>
            </div>
        )
    }
}

const styles = {
    logo: {
        height: '32px',
        background: 'rgba(255, 255, 255, .2)',
        margin: '16px',
        color:'#fff',
        textAlign:'center',
        lineHeight:'32px',
        fontSize:'22px'
    }
}

export default SiderNav