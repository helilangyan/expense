package bht.expense.enterprise.enterprise.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 姚轶文
 * @date 2021/4/12 10:18
 */
@ApiModel
@Data
public class EnterpriseUserApplyDto {

    @ApiModelProperty(value = "用户入驻企业申请ID")
    private Long applyId;

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "申请时间")
    private Date applyTime;

    @ApiModelProperty(value = "审批信息")
    private String message;

    @ApiModelProperty(value = "状态，0未审批 1审批通过 -1审批未通过")
    private String status;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "企业LOGO文件ID")
    private String fileId;

    @ApiModelProperty(value = "用户手机号码")
    private String phone;

    @ApiModelProperty(value = "用户姓")
    private String firstName;

    @ApiModelProperty(value = "用户名")
    private String lastName;

    @ApiModelProperty(value = "企业类型 1=普通企业，2=代账企业，3=其他企业")
    private String type;

}
