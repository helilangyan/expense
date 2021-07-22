package bht.expense.detail.detail.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/7/22 11:13
 */
@ApiModel
@Data
public class CheckInsertDto {

    @ApiModelProperty(value = "错误代码 货币类型不匹配-0001 ， 金额超出限制-0002 , 正常0000")
    private String message;

    @ApiModelProperty(value = "1 允许提交并提示，0禁止提交并提示")
    private String isSubmit;
}
