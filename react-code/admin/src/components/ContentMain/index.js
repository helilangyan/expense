import React from 'react'
import {withRouter, Switch, Redirect} from 'react-router-dom'
import LoadableComponent from '../../utils/LoadableComponent'
import PrivateRoute from '../PrivateRoute'

const Home = LoadableComponent(() => import('../../routes/Home/index'))  //参数一定要是函数，否则不会懒加载，只会代码拆分

// 系统设置
const user = LoadableComponent(() => import('../../routes/sys/user/index'))
const CompanyList = LoadableComponent(() => import('../../routes/sys/company/companyList'))
const RoleList = LoadableComponent(() => import('../../routes/sys/role/roleList'))
const Log = LoadableComponent(() => import('../../routes/sys/log/log'))
const Parameter = LoadableComponent(() => import('../../routes/sys/parameter/parameter'))
const Resource = LoadableComponent(() => import('../../routes/sys/resource/resource'))
const Authority = LoadableComponent(() => import('../../routes/sys/authority/authorityList'))

//客户管理
const Enterprise = LoadableComponent(() => import('../../routes/Customer/enterprise'))
const Order = LoadableComponent(() => import('../../routes/Customer/order'))
const User = LoadableComponent(() => import('../../routes/Customer/user'))

const LoadingDemo = LoadableComponent(() => import('../../routes/Other/LoadingDemo/index'))
const ErrorPage = LoadableComponent(() => import('../../routes/Other/ErrorPage/index'))


@withRouter
class ContentMain extends React.Component {
    render() {
        return (
            <div style={{padding: 10, position: 'relative'}}>
                <Switch>
                    <PrivateRoute exact path='/home' component={Home}/>

                    <PrivateRoute exact path='/home/sys/company' component={CompanyList}/>
                    {/*系统设置*/}
                    <PrivateRoute exact path='/home/sys/user' component={user}/>
                    <PrivateRoute exact path='/home/sys/role' component={RoleList}/>
                    <PrivateRoute exact path='/home/sys/resource' component={Resource}/>
                    <PrivateRoute exact path='/home/sys/authority' component={Authority}/>
                    <PrivateRoute exact path='/home/sys/log' component={Log}/>
                    <PrivateRoute exact path='/home/sys/parameter' component={Parameter}/>
                    {/*客户管理*/}
                    <PrivateRoute exact path='/home/customer/enterprise' component={Enterprise}/>
                    <PrivateRoute exact path='/home/customer/order' component={Order}/>
                    <PrivateRoute exact path='/home/customer/user' component={User}/>

                    <PrivateRoute exact path='/home/other/loading' component={LoadingDemo}/>
                    <PrivateRoute exact path='/home/other/404' component={ErrorPage}/>

                    <Redirect exact from='/' to='/home'/>
                </Switch>
            </div>
        )
    }
}

export default ContentMain