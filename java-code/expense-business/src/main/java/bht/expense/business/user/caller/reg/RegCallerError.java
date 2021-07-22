package bht.expense.business.user.caller.reg;

import bht.expense.business.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/7 15:29
 */

@Slf4j
public class RegCallerError  implements RegCaller {

    @Override
    public ResultDto reg(String phone, String password) {
        log.error("invoke expense-user-server: /user/reg error");
        return null;
    }
}
