package bht.expense.business.user.reg.controller;

import bht.expense.business.common.ResultCode;
import bht.expense.business.common.ResultDto;
import bht.expense.business.user.reg.service.RegService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 姚轶文
 * @date 2021/4/7 11:01
 */

@Api(tags = "用户注册")
@RestController
@RequestMapping("user")
public class RegController {

    @Autowired
    RegService regService;

    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @PostMapping("/reg")
    public ResultDto reg(String phone , String password)
    {
        ResultDto resultDto = regService.checkAccount(phone);
        if(resultDto.getCode() != 1)
        {
            return ResultDto.failure(ResultCode.FAILURE,"此手机号已被注册");
        }
        regService.reg(phone,password);
        return ResultDto.success();
    }

    @ApiOperation("检查注册的用户名是否重复")
    @ApiImplicitParam(name = "account", value = "用户名，手机号", required = true)
    @PostMapping("/check-account")
    public ResultDto checkAccount(@RequestParam("account") String account)
    {
        return regService.checkAccount(account);
    }
}
