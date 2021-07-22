package bht.expense.detail.apply.expense.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/21 14:58
 */
@ApiModel
@TableName("bus_apply_expense_prep")
@Data
public class ApplyExpensePrepEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "报销单申请ID")
    private Long applyId;

    @ApiModelProperty(value = "借款申请ID")
    private Long borrowId;

    @ApiModelProperty(value = "出差申请ID")
    private Long travelId;
}
