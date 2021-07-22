import '@babel/polyfill'
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import { HashRouter } from 'react-router-dom'
import { Provider } from 'mobx-react'
import { ConfigProvider  } from 'antd'
import zh_CN from 'antd/lib/locale-provider/zh_CN'
import store from './store'
import {post,get,putUrl,delUrl} from './service/http'

Component.prototype.get = get;
Component.prototype.post = post;
Component.prototype.del = delUrl;
Component.prototype.putUrl = putUrl;

//打包时，用的HashRouter并加上了basename，因为放在服务器的二级目录下
ReactDOM.render(
  <HashRouter>
    <ConfigProvider locale={zh_CN}>
      <Provider {...store}>
        <App />
      </Provider>
    </ConfigProvider>
  </HashRouter>,
  document.getElementById('root'));
registerServiceWorker();
