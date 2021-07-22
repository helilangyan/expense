package bht.expense.business.enterprise.role.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/14 15:32
 */
@ApiModel
@Data
public class RoleEntityDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;
}

