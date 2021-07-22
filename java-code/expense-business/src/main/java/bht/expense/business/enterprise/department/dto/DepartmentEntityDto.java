package bht.expense.business.enterprise.department.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/15 16:00
 */
@ApiModel
@Data
public class DepartmentEntityDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "企业id")
    private Long enterpriseId;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "上级部门ID")
    private Long parentId;
}
