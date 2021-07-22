package bht.expense.detail.apply.expense.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 姚轶文
 * @date 2021/4/21 14:50
 */
@ApiModel
@TableName("bus_apply_expense")
@Data
public class ApplyExpenseEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "申请名称")
    private String applyName;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "收款银行ID")
    private Long bankId;

    @ApiModelProperty(value = "填报用户ID")
    private Long userId;

    @ApiModelProperty(value = "当前企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "是否提交 1提交 0保存")
    private String isSubmit;

    @ApiModelProperty(value = "报销金额，前端根据选择的报销清单，计算带入")
    private Float money;

    @TableField(exist = false)
    private String status;
}
