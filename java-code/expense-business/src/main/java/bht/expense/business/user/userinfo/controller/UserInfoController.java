package bht.expense.business.user.userinfo.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.security.entity.UserEntity;
import bht.expense.business.user.userinfo.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 姚轶文
 * @date 2021/5/10 15:58
 */
@Api(tags = "用户信息")
@RestController
@RequestMapping("userinfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @ApiOperation("修改姓名")
    @PostMapping("/update-username")
    public ResultDto updateUsername(String firstName , String lastName)
    {
       return userInfoService.updateUsername(firstName, lastName);
    }

    @ApiOperation("修改手机号")
    @PostMapping("/update-phone")
    public ResultDto updatePhone(String phone)
    {
        return userInfoService.updatePhone(phone);
    }

    @ApiOperation("修改邮箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "目标邮箱", required = true),
            @ApiImplicitParam(name = "verificationCode", value = "验证码", required = true)
    })
    @PostMapping("/update-email")
    public ResultDto updateEmail(String email , String verificationCode)
    {
       return userInfoService.updateEmail(email, verificationCode);
    }

    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true),
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true)
    })
    @PostMapping("/update-password")
    public ResultDto updatePassword(String newPassword , String oldPassword)
    {
        return userInfoService.updatePassword(newPassword, oldPassword);
    }

    @ApiOperation("查看登陆用户信息")
    @PostMapping("/findById")
    public ResultDto findById()
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userEntity = userInfoService.findById(Long.parseLong(userEntity.getId()));
        return ResultDto.success(userEntity);
    }
}
