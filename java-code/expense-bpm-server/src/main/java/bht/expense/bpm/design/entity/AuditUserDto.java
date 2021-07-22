package bht.expense.bpm.design.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @AUTHOR: GC
 * @DATE: 2020-10-22
 * @VERSION: 1.0
 * @DESCRIPTION: 审核用户
 */
@Data
@ApiModel
public class AuditUserDto {
    @ApiModelProperty(value = "用户id")
    private String userid;
    @ApiModelProperty(value = "用户名")
    private String username;
}
