package bht.expense.admin.sys.resource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2020/7/14 10:15
 */

@Data
@ApiModel
@TableName("sys_resource")
public class ResourceEntity {

    @ApiModelProperty(value = "资源id")
    @TableId(value = "id" , type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否按钮 0否  1是")
    private String isMenu;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源URL地址")
    private String resourceUrl;

    @ApiModelProperty(value = "上级ID")
    private String parentId;

    @ApiModelProperty(value = "资源名称英文")
    private String resourceNameEnglish;

    @ApiModelProperty(value = "资源图标")
    private String icon;
}
