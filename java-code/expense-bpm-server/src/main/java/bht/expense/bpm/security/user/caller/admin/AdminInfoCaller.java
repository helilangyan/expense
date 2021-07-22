package bht.expense.bpm.security.user.caller.admin;

import bht.expense.bpm.security.user.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/23 14:51
 */
@FeignClient(value = "expense-admin",fallback = AdminInfoCallerError.class , contextId = "AdminInfoCaller")
public interface AdminInfoCaller {

    @PostMapping("/user/select-username")
    UserEntity selectUserByUserName(@RequestParam("username") String username);
}
