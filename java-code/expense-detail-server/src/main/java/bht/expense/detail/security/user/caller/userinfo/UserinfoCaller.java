package bht.expense.detail.security.user.caller.userinfo;

import bht.expense.detail.security.user.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/7 15:33
 */
@FeignClient(value = "expense-user-server",fallback = UserinfoCallerError.class , contextId = "UserinfoCaller")
public interface UserinfoCaller {

    @GetMapping("/userinfo/{id}")
    UserEntity findById(@RequestParam("id") Long id);
}
