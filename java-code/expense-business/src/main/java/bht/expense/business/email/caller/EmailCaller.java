package bht.expense.business.email.caller;

import bht.expense.business.bpm.design.caller.BpmDesignCallerError;
import bht.expense.business.common.ResultDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/5/10 11:42
 */
@FeignClient(value = "expense-email-server",fallback = EmailCallerError.class, contextId = "EmailCaller")
public interface EmailCaller {

    @PostMapping("/email/sendVerificationCode")
    ResultDto sendVerificationCode(@RequestParam("emailAddress") String emailAddress);
}
