package bht.expense.business.enterprise.enterprise.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 姚轶文
 * @date 2021/4/13 10:34
 */
@Data
@ApiModel
public class EnterpriseEntityDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "类型，1=普通企业，2=代账企业，3=其他企业")
    private String type;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "联系电话")
    private String tel;

    @ApiModelProperty(value = "联系人")
    private String linkman;

    @ApiModelProperty(value = "创建人ID")
    private Long userId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "状态，0=未开通 ， 1=已开通")
    private String status;

    @ApiModelProperty(value = "企业LOGO文件ID")
    private String fileId;

    @ApiModelProperty(value = "邀请码")
    private String InvitationCode;

    @ApiModelProperty(value = "税号，必须唯一性")
    private String taxCode;
}
