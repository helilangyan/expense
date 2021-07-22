import React from 'react'
import { Icon, Badge, Dropdown, Menu, Modal ,Space,Avatar} from 'antd'
import screenfull from 'screenfull'
import { toJS } from "mobx"
import { inject, observer } from 'mobx-react'
import { Link, withRouter } from 'react-router-dom'
import { MenuUnfoldOutlined ,MenuFoldOutlined,FullscreenOutlined,BellOutlined,UserOutlined} from '@ant-design/icons';
import axios from 'axios'
import { baseURL, timeout, basicUrl } from '@/service/url'


import { isAuthenticated } from '../../utils/Session'
import EnterpriseList from '../EnterpriseList/index'

//withRouter一定要写在前面，不然路由变化不会反映到props中去
@withRouter @inject('appStore') @observer
class HeaderBar extends React.Component {
  state = {
    icon: 'arrows-alt',
    count: 100,
    // visible: false,
    avatar: require('./img/04.jpg'),
    toggle:false,
  }

  componentDidMount () {
    screenfull.onchange(() => {
      this.setState({
        icon: screenfull.isFullscreen ? 'shrink' : 'arrows-alt'
      })
    })
    this.getImg()
  }

  componentWillUnmount () {
    screenfull.off('change')
  }

  toggle = () => {
    this.props.onToggle()
  }
  screenfullToggle = () => {
    if (screenfull.enabled) {
      screenfull.toggle()
    }
  }
  logout = () => {
    this.props.appStore.toggleLogin(false)
    this.props.history.push(this.props.location.pathname)
  }

  // 切换企业
  toggleEnterprise = ()=>{
    this.setState({
      toggle:true,
    })
  }
  handleCancel = ()=>{
    this.setState({
      toggle: false,
    });
  }
  // 获取头像
  getImg = (e) => {
    this.post("/userinfo-img/find-userid" ).then(res => {
        if (res.code === 1) {
          if(res.data){
            this.setState({
                imgId:res.data.id
            })
            this.getImgSrc(res.data.fileId)
          }
        }
    });
}
async getImgSrc(fileId) {
  var res = await axios({
    url: basicUrl + '/expense-file-server/file/openFile',
    method: 'post',
    headers: {
      'Authorization': isAuthenticated()
    },
    params: {
      id: fileId
    },
    responseType: "arraybuffer"
  });
  this.setState({
    imageUrl: 'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((data, byte) => data + String.fromCharCode(byte), ''))
  })
}
  render () {
    const {icon, count, visible,imageUrl, avatar,toggle} = this.state
    const {appStore, collapsed, location} = this.props
    const userData = toJS(appStore.userInfo)
    const notLogin = (
      <div>
        <Link to={{pathname: '/login', state: {from: location}}} style={{color: 'rgba(0, 0, 0, 0.65)'}}>登录</Link>&nbsp;
        <img src={require('../../assets/img/defaultUser.jpg')} alt=""/>
      </div>
    )
    const menu = (
      <Menu className='menu' style={{paddingBottom:'10px'}}>
        <Menu.ItemGroup title='用户中心' className='menu-group'>
          <Menu.Item><Link to={{pathname: '/user/personal-info'}}>个人信息</Link></Menu.Item>
          <Menu.Item onClick={this.toggleEnterprise}>切换企业</Menu.Item>
          <Menu.Item><span onClick={this.logout}>退出登录</span></Menu.Item>
        </Menu.ItemGroup>
      </Menu>
    )
    const login = (
      <Dropdown overlay={menu}>
        <Space>
          {imageUrl ? <img onClick={() => this.setState({visible: true})} src={imageUrl} alt="avatar"/> : <Avatar size={36} icon={<UserOutlined />} />}
          {/* <div>
            <span>{userData.firstName+' '+userData.lastName }</span><br/>
            <span style={{color:'#999',fontSize:'12px'}}>{toJS(appStore.curEnt).enterpriseName}</span>
          </div> */}
          <span style={{color:'#333333',paddingRight:'5px'}}>{userData.firstName+' '+userData.lastName +' - '+toJS(appStore.curEnt).enterpriseName}</span>
        </Space>
      </Dropdown>
    )
    return (
      <div id='headerbar' style={{height:'50px',lineHeight:'60px',position:'relative'}}>
        {
          collapsed ? <MenuUnfoldOutlined onClick={this.toggle}/> : <MenuFoldOutlined onClick={this.toggle}/>
        }
        <div className='headTime'>2021年3月30日 14:11:24</div>
        <div style={{lineHeight: '60px', float: 'right'}}>
          <ul className='header-ul'>
            <li><FullscreenOutlined  onClick={this.screenfullToggle}/></li>
            <li onClick={() => this.setState({count: 0})}>
              <Badge count={appStore.isLogin ? count : 0} overflowCount={99} style={{marginRight: -17}}>
                <BellOutlined />
              </Badge>
            </li>
            <li>
              {appStore.isLogin ? login : notLogin}
            </li>
          </ul>
        </div>
        {/* <Modal
          footer={null} closable={false}
          visible={visible}
          wrapClassName="vertical-center-modal"
          onCancel={() => this.setState({visible: false})}>
          <img src={avatar} alt="" width='100%'/>
        </Modal> */}
        <Modal
          width="38%"
          title="切换企业"
          centered
          visible={toggle}
          footer={null}
          onCancel={() => this.setState({toggle: false})}
          destroyOnClose={true}
        >
          <EnterpriseList submitAdd={this.submitAdd} onCancel={this.handleCancel}></EnterpriseList>
        </Modal>
      </div>
    )
  }
}

export default HeaderBar
