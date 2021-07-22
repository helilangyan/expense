package bht.expense.enterprise.role.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/15 11:29
 */
@ApiModel
@Data
public class RoleMenuDto {

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单上级ID")
    private Long parentId;

    @ApiModelProperty(value = "角色选择菜单的ID")
    private Long roleMenuId;
}
