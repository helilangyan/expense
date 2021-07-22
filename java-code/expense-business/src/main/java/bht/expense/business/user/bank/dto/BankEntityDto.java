package bht.expense.business.user.bank.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/8 9:39
 */

@ApiModel
@Data
public class BankEntityDto {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    private String bankAccount;

    @ApiModelProperty(value = "银行账户人")
    private String bankHolder;
}
