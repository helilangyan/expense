package bht.expense.email.common;

import lombok.Data;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/7/13 11:53
 */
@Data
public class PageListDto<T> {

    private String code;
    private String message;
    private long count;
    private List<T> data;

    public PageListDto()
    {
        this.setCode("0");
        this.setMessage("");
    }
}
