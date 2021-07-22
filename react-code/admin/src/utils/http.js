
import axios from 'axios'
import qs from 'qs'
import {isAuthenticated,} from './Session'
import $http from './request'

// let rootUrl = 'http://192.168.5.100:8080';
let rootUrl = 'http://192.168.5.100:8088/expense-admin';
let uploadUrl='http://192.168.5.100:8088/expense-file-server/file/upload'
// const $http = axios.create();
// //设置请求头
// $http.interceptors.request.use(config => {
//     if (!!isAuthenticated()) {
//         config.headers.Authorization = isAuthenticated()
//     }
//     return config
// })
// get post请求封装
export function get(url, param) {
    return new Promise((resolve, reject) => {
        $http.get(rootUrl + url, {params: param}).then(response => {
            resolve(response.data)
        }, err => {
            reject(err)
        }).catch((error) => {
            reject(error)
        })
    })
}

export function post(url, params) {
    let data = qs.stringify(params)
    return new Promise((resolve, reject) => {
        $http.post(rootUrl + url, data).then(response => {
            resolve(response.data);
        }, err => {
            reject(err);
        }).catch((error) => {
            reject(error)
        })
    })
}
export function delUrl(url, param) {
    return new Promise((resolve, reject) => {
        $http.delete(rootUrl + url, {params: param}).then(response => {
            resolve(response.data)
        }, err => {
            reject(err)
        }).catch((error) => {
            reject(error)
        })
    })
}
export function putUrl(url, param) {
    return new Promise((resolve, reject) => {
        $http.put(rootUrl + url, {params: param}).then(response => {
            resolve(response.data)
        }, err => {
            reject(err)
        }).catch((error) => {
            reject(error)
        })
    })
}
export const upUrl = uploadUrl;