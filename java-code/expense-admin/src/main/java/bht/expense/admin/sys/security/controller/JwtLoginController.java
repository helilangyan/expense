package bht.expense.admin.sys.security.controller;

import bht.expense.admin.common.ResultDto;
import bht.expense.admin.sys.resource.entity.ResourceEntity;
import bht.expense.admin.sys.security.jwt.JwtAuthService;
import bht.expense.admin.sys.security.jwt.JwtTokenUtil;
import bht.expense.admin.sys.user.entity.UserEntity;
import bht.expense.admin.sys.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@Api(tags = "登录")
@RestController
public class JwtLoginController {

    @Autowired
    JwtAuthService jwtAuthService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;
    /**
     * 登录方法
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    @ApiOperation("输入账号密码，返回令牌")
    @PostMapping("/login")
    public ResultDto login(String username, String password) {
        String token = jwtAuthService.login(username, password);
        return ResultDto.success(token);
    }

    @ApiOperation("登录用户获取导航菜单")
    @GetMapping("/navigation")
    public ResultDto navigation(@RequestHeader("Authorization") String authorization)
    {
        String username = jwtTokenUtil.getUsernameFromToken(authorization);
        UserEntity userEntity = userService.selectUserByUserName(username);
        List<ResourceEntity> list = userService.findResourceByUserId(userEntity.getId());
        return ResultDto.success(list);
    }

    @ApiIgnore
    @PostMapping({"/refreshToken"})
    public ResultDto refreshToken(String token,String username) {
        UserEntity userEntity = userService.selectUserByUserName(username);
        if(userEntity!=null){
            token = jwtTokenUtil.generateToken(userEntity);
        }
        return ResultDto.success(token);
    }
}
