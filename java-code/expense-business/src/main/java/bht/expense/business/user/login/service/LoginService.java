package bht.expense.business.user.login.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.user.caller.login.LoginCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/7 11:06
 */
@Service
public class LoginService {

    @Autowired
    LoginCaller userServerCaller;

    public ResultDto login(String account, String password)
    {
        return userServerCaller.login(account,password);
    }
}
