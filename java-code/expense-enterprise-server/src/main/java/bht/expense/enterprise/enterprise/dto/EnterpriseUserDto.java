package bht.expense.enterprise.enterprise.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/12 11:36
 */
@ApiModel
@Data
public class EnterpriseUserDto {

    @ApiModelProperty(value = "用户入驻企业ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户手机号码")
    private String phone;

    @ApiModelProperty(value = "用户email")
    private String email;

    @ApiModelProperty(value = "用户姓")
    private String firstName;

    @ApiModelProperty(value = "用户名")
    private String lastName;

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "企业LOGO文件ID")
    private String fileId;

    @ApiModelProperty(value = "是否默认")
    private Integer isDefault;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;
}
