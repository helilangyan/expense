package bht.expense.business.detail.apply.dto;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @date 2021/4/22 16:18
 */
@ApiModel
@Data
public class ApplyBorrowEntityDto {

    @ApiModelProperty(value = "id")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
}
