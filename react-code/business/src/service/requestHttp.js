// 封装一个 axios 文件
import axios from "axios";
import qs from 'qs'
import { message} from 'antd';
import {isAuthenticated,logout} from '../utils/Session'
import {createHashHistory} from 'history';

const history = createHashHistory();
let url = "http://192.168.5.100:8088"; 

const request = axios.create({
    baseURL:url,
    timeout:5000,
 })

 request.interceptors.request.use(config =>{
    if (!!isAuthenticated()) {
      config.headers.Authorization = isAuthenticated()
  }
  return config
},err =>{

})

request.interceptors.response.use(res =>{
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


function post(url, data) {
    let qsdata = qs.stringify(data)
    return request({
        url,
        method: "POST",
        data:qsdata
    });
}

function get(url, data) {
    return request({
        url,
        method: "GET",
        params: data
    });
}

function del(url, data) {
    return request({
        url,
        method: "DELETE",
        params: data
    });
}

function putUrl(url, data) {
    return request({
        url,
        method: "PUT",
        params: data
    });
}



export{
  post,get,del,putUrl
}
