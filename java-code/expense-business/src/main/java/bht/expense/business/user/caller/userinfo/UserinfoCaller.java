package bht.expense.business.user.caller.userinfo;

import bht.expense.business.common.ResultDto;
import bht.expense.business.security.entity.UserEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/7 15:33
 */
@FeignClient(value = "expense-user-server",fallback = UserinfoCallerError.class , contextId = "UserinfoCaller")
public interface UserinfoCaller {

    @GetMapping("/userinfo/{id}")
    UserEntity findById(@RequestParam("id") Long id);


    @PostMapping("/userinfo/check-account")
    ResultDto checkAccount(@RequestParam("account") String account);

    @PostMapping("/userinfo/update-username")
    ResultDto updateUsername(@RequestParam("firstName") String firstName , @RequestParam("lastName") String lastName);


    @PostMapping("/userinfo/update-phone")
    ResultDto updatePhone(@RequestParam("phone") String phone);



    @PostMapping("/userinfo/update-email")
    ResultDto updateEmail(@RequestParam("email") String email , @RequestParam("verificationCode") String verificationCode);



    @PostMapping("/userinfo/update-password")
    ResultDto updatePassword(@RequestParam("newPassword") String newPassword , @RequestParam("oldPassword") String oldPassword);

}
