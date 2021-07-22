package bht.expense.business.user.userinfo.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.security.entity.UserEntity;
import bht.expense.business.user.caller.userinfo.UserinfoCaller;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/5/10 15:55
 */
@Service
public class UserInfoService {

    @Autowired
    UserinfoCaller userinfoCaller;


    public ResultDto updateUsername(String firstName , String lastName)
    {
        return userinfoCaller.updateUsername(firstName, lastName);
    }

    public ResultDto updatePhone(String phone)
    {
        return userinfoCaller.updatePhone(phone);
    }


    public ResultDto updateEmail(String email , String verificationCode)
    {
        return userinfoCaller.updateEmail(email, verificationCode);
    }


    public ResultDto updatePassword(String newPassword , String oldPassword)
    {
        return userinfoCaller.updatePassword(newPassword, oldPassword);
    }

    public UserEntity findById(Long id)
    {
        return userinfoCaller.findById(id);
    }
}
