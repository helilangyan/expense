package bht.expense.detail.apply.expense.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/21 14:56
 */
@ApiModel
@TableName("bus_apply_expense_detail")
@Data
public class ApplyExpenseDetailEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "报销申请ID")
    private Long applyId;

    @ApiModelProperty(value = "费用单ID")
    private Long detailId;
}
