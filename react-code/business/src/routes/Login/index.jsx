import React, { Component } from 'react'
import './style.css'
import { withRouter } from 'react-router-dom'
import { inject, observer } from 'mobx-react/index'
import LoginForm from './LoginForm'
import RegisterForm from './RegisterForm'


@withRouter @inject('appStore') @observer
export default class Login extends Component {
  componentDidMount () {
    const isLogin = this.props.appStore
    if(isLogin){
      this.props.history.go(1)     //当浏览器用后退按钮回到登录页时，判断登录页是否登录，是登录就重定向上个页面
      // this.props.appStore.toggleLogin(false) //也可以设置退出登录
    }
  }
  state = {
      showBox: 'login',   //展示当前表单
      url: '',  //背景图片
      loading:false,
      loading2:false,
  }
  //切换showbox
  switchShowBox = (box) => {
    this.setState({
      showBox: box
    })
  }

    
  render() {
    const {showBox} = this.state
    return (
     <div id="login-page">
         <div className='container'>
             <LoginForm className={showBox === 'login' ? 'box showBox' : 'box hiddenBox'} switchShowBox={this.switchShowBox}/>
             <RegisterForm
                className={showBox === 'register' ? 'box showBox' : 'box hiddenBox'}
                switchShowBox={this.switchShowBox}/>
             <div className='footer'>
              <div>By logging in, you agree to the <span>privacy policy</span>.</div>
            </div>
         </div>
     </div>
    )
  }
}



