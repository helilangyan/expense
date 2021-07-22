
import axios from 'axios'
import {isAuthenticated,logout} from "./Session";
import toggleLogin from '../store/appStore'
import {createHashHistory} from 'history';
const history = createHashHistory();

const $http = axios.create();
//设置请求头
$http.interceptors.request.use(config => {
    if (!!isAuthenticated()) {
        config.headers.Authorization = isAuthenticated()
    }
    return config
})
// 对响应进行拦截
// 返回后拦截
$http.interceptors.response.use(
    data => {
        return data;
    },
    err => {
        console.log(err.response);
        if (err.response.status === 504 || err.response.status === 404) {
            console.log("服务器被吃了");
        } else if (err.response.status === 401) {
            console.log("登录信息失效");
        } else if (err.response.status === 500) {
            console.log("服务器开小差了");
        } else if (err.response.status === 403) {
            console.log("登录超时");
            logout();
            history.push('/login');
        }
        return Promise.reject(err);
    }
);
// $http.interceptors.response.use((response)=>{
//     if(response.data.code==="200") {
//         console.log('请求成功！');
//     }else{
//         console.log('请求失败！');
//
//     }
//     if(response.status!==200) {
//         alert("请求失败！")
//     }
//     return response
// },(err)=>{
//
//     console.log(err);
// })

export default $http;