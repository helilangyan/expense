package bht.expense.business.user.login.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.user.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 姚轶文
 * @date 2021/4/7 11:07
 */
@Api(tags = "用户登录")
@RestController
@RequestMapping("user")
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiOperation("输入账号密码，返回令牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "手机号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @PostMapping({"/login"})
    public ResultDto login(String account, String password) {
        return loginService.login(account,password);
    }
}
