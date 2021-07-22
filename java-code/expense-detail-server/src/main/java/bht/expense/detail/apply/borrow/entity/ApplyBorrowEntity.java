package bht.expense.detail.apply.borrow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 姚轶文
 * @date 2021/4/21 17:23
 */
@ApiModel
@TableName("bus_apply_borrow")
@Data
public class ApplyBorrowEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "申请人ID")
    private Long userId;

    @ApiModelProperty(value = "申请人当前企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "申请名称")
    private String applyName;

    @ApiModelProperty(value = "借款金额")
    private Float money;

    @ApiModelProperty(value = "还款日")
    private Date dueDate;

    @ApiModelProperty(value = "借款人银行卡ID")
    private Long bankId;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "是否提交 1提交 0保存")
    private String isSubmit;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(exist = false)
    private String status;
}
