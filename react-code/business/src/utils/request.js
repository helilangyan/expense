import axios from 'axios'
// import qs from 'qs'
import { message} from 'antd';
import {isAuthenticated,logout} from "./Session";
import {baseURL,timeout} from '../service/url'
import {createHashHistory} from 'history';
const history = createHashHistory();



const service = axios.create({
   baseURL:baseURL,
   timeout:timeout,
})

service.interceptors.request.use(config =>{
    if (!!isAuthenticated()) {
      config.headers.Authorization = isAuthenticated()
  }
  return config
},err =>{

})

service.interceptors.response.use(res =>{

  return res.data
},err =>{
  if(err && err.response){
    switch (err.response.status){
      case 400:
        console.log('请求错误')
        break;
      case 401:
        console.log('未授权访问')
        break;
      case 403:
        console.log('禁止访问')
        logout ()
        message.error('登录信息过期,请重新登录',2)
        history.push('/login');
        break;
      case 404:
        console.log('没找到')
        break;
      default:
        console.log('其他错误信息')    
    }
  }
  return err
})

export default service
