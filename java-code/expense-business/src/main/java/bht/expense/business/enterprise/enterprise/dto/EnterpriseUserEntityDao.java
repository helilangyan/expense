package bht.expense.business.enterprise.enterprise.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/7/22 15:30
 */
@ApiModel
@Data
public class EnterpriseUserEntityDao {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "默认作为当前企业用户登录")
    private Integer isDefault;

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
}
