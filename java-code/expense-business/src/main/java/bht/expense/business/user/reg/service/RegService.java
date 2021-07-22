package bht.expense.business.user.reg.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.user.caller.login.LoginCaller;
import bht.expense.business.user.caller.reg.RegCaller;
import bht.expense.business.user.caller.userinfo.UserinfoCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/7 10:17
 */

@Service
public class RegService {

    @Autowired
    RegCaller regCaller;

    @Autowired
    UserinfoCaller userinfoCaller;

    public ResultDto reg(String phone, String password)
    {
        return regCaller.reg(phone,password);
    }


    public ResultDto checkAccount(String account)
    {
        return userinfoCaller.checkAccount(account);
    }
}
