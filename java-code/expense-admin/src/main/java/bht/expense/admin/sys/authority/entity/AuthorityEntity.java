package bht.expense.admin.sys.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2020/7/14 9:44
 */

@Data
@ApiModel
@TableName("sys_authority")
public class AuthorityEntity {

    @ApiModelProperty(value = "权限id")
    @TableId(value = "id" , type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "权限名称")
    private String authorityName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
