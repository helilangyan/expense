import React, {Component} from 'react';
import {Route,Switch,Redirect} from 'react-router-dom'
import PrivateRoute from './components/PrivateRoute'
import Login from './routes/Login/index'
// import Login from './routes/Login2/index'

import Index from './routes/Index/index'

import './App.css'
import './assets/font/iconfont.css'

// export const myRouter = {};

class App extends Component {
  // myRouter = this.props.router;
  render() {
    return (
      <Switch>
        <Route path='/login' component={Login}/>
        <PrivateRoute path='/' component={Index}/>
        <Redirect to="/login"></Redirect>
      </Switch>
    )
  }
}

export default App;
