package bht.expense.business.email.caller;

import bht.expense.business.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/5/10 11:45
 */
@Slf4j
public class EmailCallerError implements EmailCaller{

    @Override
    public ResultDto sendVerificationCode(String emailAddress) {
        log.error("invoke expense-email-server: /email/sendVerificationCode error");
        return null;
    }
}
