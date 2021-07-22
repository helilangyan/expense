package bht.expense.email.send.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/9/1 10:27
 */
@Data
public class MailParam {

    private String to;
    private String subject;
    private String content;
    private List<MailFile> files;
}
