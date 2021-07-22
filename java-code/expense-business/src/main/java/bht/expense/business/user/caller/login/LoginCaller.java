package bht.expense.business.user.caller.login;

import bht.expense.business.common.ResultDto;
import bht.expense.business.security.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/7 10:22
 */
@FeignClient(value = "expense-user-server",fallback = LoginCallerError.class , contextId="LoginCaller")
public interface LoginCaller {

    @PostMapping("/login")
    ResultDto login(@RequestParam("account") String account,  @RequestParam("password") String password);

}
