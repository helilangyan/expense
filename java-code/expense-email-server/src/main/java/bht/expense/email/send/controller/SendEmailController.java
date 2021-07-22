package bht.expense.email.send.controller;

import bht.expense.email.common.ResultDto;
import bht.expense.email.send.dto.MailParam;
import bht.expense.email.send.service.SendEmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 姚轶文
 * @date 2021/5/10 11:14
 */

@Api(tags = "邮件发送服务")
@RestController
@RequestMapping("email")
public class SendEmailController {

    @Autowired
    private SendEmailService sendEmailService;

    @ApiOperation("发送邮件验证码")
    @ApiImplicitParam(name = "emailAddress", value = "目标邮箱地址", required = true)
    @PostMapping("/sendVerificationCode")
    public ResultDto sendVerificationCode(String emailAddress) {
        MailParam mailParam = new MailParam();
        mailParam.setSubject("Expense验证码");
        mailParam.setTo(emailAddress);
        sendEmailService.sendVerificationCode(mailParam);
        return ResultDto.success();
    }
}
