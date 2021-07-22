package bht.expense.admin.sys.role.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2020/7/15 14:36
 */

@Data
@ApiModel
public class RoleAuthorityDto {

    @ApiModelProperty(value = "权限ID")
    private String authorityId;

    @ApiModelProperty(value = "权限名称")
    private String authorityName;

    @ApiModelProperty(value = "权限备注")
    private String remark;

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "角色与权限关联表ID")
    private String roleAuthorityId;
}
