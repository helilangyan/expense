package bht.expense.business.user.caller.reg;

import bht.expense.business.common.ResultDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/7 15:28
 */

@FeignClient(value = "expense-user-server",fallback = RegCallerError.class, contextId = "RegCaller")
public interface RegCaller {

    @PostMapping("/user/reg")
    ResultDto reg(@RequestParam("phone") String phone , @RequestParam("password") String password);


}
