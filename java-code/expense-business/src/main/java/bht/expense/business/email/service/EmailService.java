package bht.expense.business.email.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.email.caller.EmailCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/5/10 11:46
 */
@Service
public class EmailService {

    @Autowired
    private EmailCaller emailCaller;

    public ResultDto sendVerificationCode(String emailAddress)
    {
        return emailCaller.sendVerificationCode(emailAddress);
    }
}
