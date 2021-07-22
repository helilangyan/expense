package bht.expense.business.email.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.email.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 姚轶文
 * @date 2021/5/10 14:12
 */
@Api(tags = "邮件发送服务")
@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @ApiOperation("发送邮件验证码")
    @ApiImplicitParam(name = "emailAddress", value = "目标邮箱地址", required = true)
    @PostMapping("/email/sendVerificationCode")
    public ResultDto sendVerificationCode(@RequestParam("emailAddress") String emailAddress)
    {
        return emailService.sendVerificationCode(emailAddress);
    }
}
