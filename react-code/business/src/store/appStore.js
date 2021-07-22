import {observable, action} from 'mobx'
import {isAuthenticated,authenticateSuccess,logout} from '../utils/Session'
import {curEnt,isCurEnt,clearEnt,userInfo, isUser} from '../utils/auth'

class AppStore {
  @observable isLogin = !!isAuthenticated()  //利用cookie来判断用户是否登录，避免刷新页面后登录状态丢失
  @observable users = []  //模拟用户数据库
  @observable loginUser = {}  //当前登录用户信息
  @observable curEnt = curEnt('entInfo') //当前企业信息
  @observable userInfo = curEnt('userInfo') //当前用户信息

  @action changeCurrent(ele){
    isCurEnt('entInfo',ele)
  }
  @action saveUser(ele){
    isCurEnt('userInfo',ele)
  }
  @action toggleLogin(flag,info={}) {
    this.loginUser = info  //设置登录用户信息
    if (flag) {
      authenticateSuccess(info.token)
      this.isLogin = true
    } else {
      logout()
      clearEnt('entInfo')
      clearEnt('userInfo')
      this.isLogin = false
    }
  }
  @action initUsers() {
    const localUsers = localStorage['users']?JSON.parse(localStorage['users']):[]
    this.users = [{username: 'admin', password: 'admin'},...localUsers]
  }
}

export default new AppStore()