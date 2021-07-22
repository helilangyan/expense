package bht.expense.business.enterprise.enterprise.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/19 10:37
 */
@ApiModel
@Data
public class ApproveDto {

    @ApiModelProperty(value = "申请ID")
    private Long applyId;

    @ApiModelProperty(value = "0=未开通 ， 1=已开通")
    private String status;

    @ApiModelProperty(value = "审批内容")
    private String message;

    @ApiModelProperty(value = "员工编号")
    private String employeeCode;

    @ApiModelProperty(value = "所属部门ID")
    private Long departmentId;

    @ApiModelProperty(value = "职位ID")
    private Long positionId;

    @ApiModelProperty(value = "上级，用户ID")
    private Long superior;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "本企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "申请用户ID")
    private Long userId;
}
