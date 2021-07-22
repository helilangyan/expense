package bht.expense.admin.sys.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2020/7/15 14:21
 */

@Data
@ApiModel
@TableName("sys_role_authority")
public class RoleAuthorityEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "权限id")
    private String authorityId;
}
