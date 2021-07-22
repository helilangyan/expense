package bht.expense.detail.detail.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 姚轶文
 * @date 2021/7/16 17:16
 */
@ApiModel
@Data
public class ExpenseDetailListDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "计费类型 0=个人支付 ， 1=公司支付")
    private String billType;

    @ApiModelProperty(value = "费用名称")
    private String expenseName;

    @ApiModelProperty(value = "金额")
    private Float money;

    @ApiModelProperty(value = "货币类型，usd cny")
    private String moneyType;

    @ApiModelProperty(value = "发生日期")
    private Date giveDate;

    @ApiModelProperty(value = "分类")
    private String expenseType;

    @ApiModelProperty(value = "部门ID")
    private Long departmentId;

    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty(value = "标签，多标签用,分割")
    private String tags;

    @ApiModelProperty(value = "企业支付，企业银行卡ID")
    private Long enterpriseBankId;

    @ApiModelProperty(value = "时间报销中，花费小时数")
    private Integer workTime;

    @ApiModelProperty(value = "里程报销中，起始地址")
    private String startAddress;

    @ApiModelProperty(value = "里程报销中，达到地址")
    private String stopAddress;

    @ApiModelProperty(value = "交通工具")
    private String vehicle;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "创建时间，系统自动填充")
    private Date createTime;

    @ApiModelProperty(value = "附件ID，多附件用，分割")
    private String fileId;

    @ApiModelProperty(value = "分类名称")
    private String expenseTypeName;

    @ApiModelProperty(value = "标签名称")
    private String tagsName;
}
