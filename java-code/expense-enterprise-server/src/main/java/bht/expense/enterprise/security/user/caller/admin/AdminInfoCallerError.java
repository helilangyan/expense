package bht.expense.enterprise.security.user.caller.admin;

import bht.expense.enterprise.security.user.entity.UserEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 姚轶文
 * @date 2021/4/23 14:52
 */
public class AdminInfoCallerError implements AdminInfoCaller{


    @Override
    public UserEntity selectUserByUserName(String username) {
        return null;
    }
}
