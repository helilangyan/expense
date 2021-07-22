
import axios from 'axios'
import qs from 'qs'
import {isAuthenticated,} from '../utils/Session'
import service from '../utils/request'


let beseUrl='http://192.168.5.100:8088'
// let rootUrl = 'http://192.168.5.12:8088/expense-business';
let rootUrl = 'http://192.168.5.100:8088/expense-business';

// get post请求封装
export function get(url, param) {
    return new Promise((resolve, reject) => {
        service.get(rootUrl + url, {params: param}).then(response => {
            resolve(response)
        }, err => {
            reject(err)
        }).catch((error) => {
            reject(error)
        })
    })
}
export function post(url, params,ContentType) {
    let data=''
    let head={};
    if (ContentType){
        head={headers: {'Content-Type':ContentType }}
        data=params;
    } else {
        head={}
        data = qs.stringify(params)
    }
    return new Promise((resolve, reject) => {
        service.post(rootUrl + url, data,head).then(response => {
            resolve(response);
        }, err => {
            reject(err);
        }).catch((error) => {
            reject(error)
        })
    })
}
export function delUrl(url, param) {
    return new Promise((resolve, reject) => {
        service.delete(rootUrl + url, {params: param}).then(response => {
            resolve(response)
        }, err => {
            reject(err)
        }).catch((error) => {
            reject(error)
        })
    })
}
export function putUrl(url, param) {
    return new Promise((resolve, reject) => {
        service.put(rootUrl + url, {params: param}).then(response => {
            resolve(response)
        }, err => {
            reject(err)
        }).catch((error) => {
            reject(error)
        })
    })
}
export {beseUrl};
