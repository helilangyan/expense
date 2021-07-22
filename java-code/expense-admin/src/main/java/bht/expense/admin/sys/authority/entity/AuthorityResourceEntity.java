package bht.expense.admin.sys.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2020/7/16 11:09
 */
@Data
@ApiModel
@TableName("sys_authority_resource")
public class AuthorityResourceEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "资源ID")
    private String resourceId;

    @ApiModelProperty(value = "权限ID")
    private String authorityId;
}
