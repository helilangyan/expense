import React from 'react'
import {withRouter, Switch, Redirect} from 'react-router-dom'
import LoadableComponent from '../../utils/LoadableComponent'
import PrivateRoute from '../PrivateRoute'

const Home = LoadableComponent(() => import('../../routes/Home/index.jsx'))  //参数一定要是函数，否则不会懒加载，只会代码拆分
const ExpenseList = LoadableComponent(() => import('../../routes/ExpenseList'))
const ApplicationList = LoadableComponent(() => import('../../routes/ApplicationList'))
const PendingApprovalList = LoadableComponent(() => import('../../routes/PendingList'))

// 第三方服务
const MyApplication = LoadableComponent(() => import('../../routes/ThirdPartyServices/MyApplication'))
const ApplicationStatistics = LoadableComponent(() => import('../../routes/ThirdPartyServices/ApplicationStatistics'))
const Settlement = LoadableComponent(() => import('../../routes/ThirdPartyServices/Settlement'))
const Settlement2 = LoadableComponent(() => import('../../routes/ThirdPartyServices/Settlement2'))
const OrderReceivingWorkbench = LoadableComponent(() => import('../../routes/ThirdPartyServices/OrderReceivingWorkbench'))
const OrderReceivingStatistics = LoadableComponent(() => import('../../routes/ThirdPartyServices/OrderReceivingStatistics'))
const FinancialStatistics = LoadableComponent(() => import('../../routes/ThirdPartyServices/FinancialStatistics'))

// Reporting center
const PersonalReporting = LoadableComponent(() => import('../../routes/ReportingCenter/PersonalReporting'))
const EnterpriseReporting = LoadableComponent(() => import('../../routes/ReportingCenter/EnterpriseReporting'))

//Enterprise setup
const EnterpriseInfo = LoadableComponent(() => import('../../routes/Enterprise/EnterpriseInfo/index.jsx'))
const Approval = LoadableComponent(() => import('../../routes/Enterprise/Approval'))
const Declare = LoadableComponent(() => import('../../routes/Enterprise/Declare'))
const Declare2 = LoadableComponent(() => import('../../routes/Enterprise/Declare2'))
const Account = LoadableComponent(() => import('../../routes/Enterprise/Account'))
const Role = LoadableComponent(() => import('../../routes/Enterprise/Role'))
const Department = LoadableComponent(() => import('../../routes/Enterprise/Department'))
const Position = LoadableComponent(() => import('../../routes/Enterprise/Position'))
const Project = LoadableComponent(() => import('../../routes/Enterprise/Project'))
const Member = LoadableComponent(() => import('../../routes/Enterprise/Member'))
const Strategy = LoadableComponent(() => import('../../routes/Enterprise/Strategy'))
const Connect = LoadableComponent(() => import('../../routes/Enterprise/Connect'))
const Service = LoadableComponent(() => import('../../routes/Enterprise/Service'))
//Personal setup
const PersonalInfo = LoadableComponent(() => import('../../routes/User/PersonalInfo'))
const IncomeExpenditure = LoadableComponent(() => import('../../routes/User/IncomeExpenditure'))

@withRouter
class ContentMain extends React.Component {
    render() {
        return (
            <div className="mainWrapper">
                <Switch>
                    <PrivateRoute exact path='/home' component={Home}/>
                    <PrivateRoute exact path='/home/expenseList' component={ExpenseList}/>
                    <PrivateRoute exact path='/home/application-list' component={ApplicationList}/>
                    <PrivateRoute exact path='/home/pending-approval-list' component={PendingApprovalList}/>
                    {/*第三方服务*/}
                    <PrivateRoute exact path='/third-party-services/my-application' component={MyApplication}/>
                    <PrivateRoute exact path='/third-party-services/application-statistics' component={ApplicationStatistics}/>
                    <PrivateRoute exact path='/third-party-services/settlement' component={Settlement}/>
                    <PrivateRoute exact path='/third-party-services/order-receiving-workbench' component={OrderReceivingWorkbench}/>
                    <PrivateRoute exact path='/third-party-services/order-receiving-statistics' component={OrderReceivingStatistics}/>
                    <PrivateRoute exact path='/third-party-services/settlement2' component={Settlement2}/>
                    <PrivateRoute exact path='/third-party-services/financial-statistics' component={FinancialStatistics}/>
                    {/*Reporting center*/}
                    <PrivateRoute exact path='/reporting/personal-reporting' component={PersonalReporting}/>
                    <PrivateRoute exact path='/reporting/enterprise-reporting' component={EnterpriseReporting}/>

                    {/*Enterprise setup */}
                    <PrivateRoute exact path='/enterprise/enterprise-info' component={EnterpriseInfo}/>
                    <PrivateRoute exact path='/enterprise/account' component={Account}/>
                    <PrivateRoute exact path='/enterprise/approval' component={Approval}/>
                    <PrivateRoute exact path='/enterprise/declare' component={Declare}/>
                    <PrivateRoute exact path='/enterprise/declare2' component={Declare2}/>
                    <PrivateRoute exact path='/enterprise/member' component={Member}/>
                    <PrivateRoute exact path='/enterprise/role' component={Role}/>
                    <PrivateRoute exact path='/enterprise/department' component={Department}/>
                    <PrivateRoute exact path='/enterprise/position' component={Position}/>
                    <PrivateRoute exact path='/enterprise/project' component={Project}/>
                    <PrivateRoute exact path='/enterprise/strategy' component={Strategy}/>
                    <PrivateRoute exact path='/enterprise/connect' component={Connect}/>
                    <PrivateRoute exact path='/enterprise/service' component={Service}/>
                    {/* Personal setup */}
                    <PrivateRoute exact path='/user/personal-info' component={PersonalInfo}/>
                    <PrivateRoute exact path='/user/income-expenditure' component={IncomeExpenditure}/>

                    <Redirect exact from='/' to='/home'/>
                </Switch>
            </div>
        )
    }
}

export default ContentMain