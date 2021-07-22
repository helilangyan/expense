package bht.expense.business.user.caller.login;

import bht.expense.business.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/7 10:23
 */
@Slf4j
public class LoginCallerError implements LoginCaller {

    @Override
    public ResultDto login(String account, String password) {
        log.error("invoke expense-user-server: /login error");
        return null;
    }

}
