package bht.expense.admin.sys.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/7/15 17:12
 */

@Data
@ApiModel
public class ResourceTreeDto {

    @ApiModelProperty(value = "资源id")
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

    @ApiModelProperty(value = "下级资源集合")
    private List<ResourceTreeDto> children;
}
