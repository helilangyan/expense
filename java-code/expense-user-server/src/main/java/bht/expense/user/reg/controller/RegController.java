package bht.expense.user.reg.controller;

import bht.expense.user.common.ResultDto;
import bht.expense.user.userinfo.entity.UserEntity;
import bht.expense.user.userinfo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/6 15:30
 */
@Api(tags = "用户注册")
@RestController
@RequestMapping("user")
public class RegController {

    @Autowired
    UserService userService;

    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号"),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @PostMapping("/reg")
    public ResultDto reg(String phone , String password)
    {
        String encryptionPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(phone);
        userEntity.setPassword(encryptionPassword);

        userService.insert(userEntity);
        return ResultDto.success();
    }
}
