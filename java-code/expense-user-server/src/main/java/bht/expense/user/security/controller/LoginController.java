package bht.expense.user.security.controller;

import bht.expense.user.common.ResultDto;
import bht.expense.user.userinfo.entity.UserEntity;
import bht.expense.user.userinfo.service.UserService;
import bht.expense.user.security.jwt.JwtAuthService;
import bht.expense.user.security.jwt.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author 姚轶文
 * @date 2021/4/6 17:53
 */

@Api(tags = "登录")
@RestController
public class LoginController {

    @Autowired
    JwtAuthService jwtAuthService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;


    @ApiOperation("输入账号密码，返回令牌")
    @PostMapping({"/login"})
    public ResultDto login(String account, String password) {
        String token = jwtAuthService.login(account, password);
        return ResultDto.success(token);
    }

    @ApiIgnore
    @PostMapping({"/refreshToken"})
    public ResultDto refreshToken(String token, String id) {
        UserEntity userEntity = userService.findById(Long.parseLong(id));
        if(userEntity!=null){
            token = jwtTokenUtil.generateToken(userEntity);
        }
        return ResultDto.success(token);
    }
}
