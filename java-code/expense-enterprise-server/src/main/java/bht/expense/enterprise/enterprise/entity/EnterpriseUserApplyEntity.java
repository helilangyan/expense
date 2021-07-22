package bht.expense.enterprise.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 姚轶文
 * @date 2021/4/12 9:40
 */
@ApiModel
@TableName("bus_enterprise_user_apply")
@Data
public class EnterpriseUserApplyEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "申请时间")
    private Date applyTime;

    @ApiModelProperty(value = "审批消息")
    private String message;

    @ApiModelProperty(value = "状态，0未审批 1审批通过 -1审批未通过")
    private String status;

}
