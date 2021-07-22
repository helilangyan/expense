package bht.expense.email.send.service;

import bht.expense.email.send.dto.MailFile;
import bht.expense.email.send.dto.MailParam;
import bht.expense.email.util.RandomCode;
import bht.expense.email.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author 姚轶文
 * @date 2021/5/10 10:29
 */
@Slf4j
@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private RedisUtil redisUtil;



    @Value("${spring.mail.from}")
    private String from;

    public void sendVerificationCode(MailParam mailParam)
    {
        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        String verificationCode = RandomCode.randomCode6();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(mailParam.getTo());
            helper.setSubject(mailParam.getSubject());
            helper.setText(verificationCode, true);

            if(mailParam.getFiles()!=null && mailParam.getFiles().size()>0)
            {
                for(MailFile mailFile : mailParam.getFiles())
                {
                    FileSystemResource file = new FileSystemResource(new File(mailFile.getFilePath()));
                    String fileName = mailFile.getFileName();
                    helper.addAttachment(fileName, file);
                }
            }
            send(mimeMessage);

            redisUtil.set(mailParam.getTo() , verificationCode , 1800);
            //日志信息
            log.info("邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        }
    }

    @Async("expenseTaskExecutor")
    public void send(MimeMessage mimeMessage)
    {
        javaMailSender.send(mimeMessage);
    }
}
