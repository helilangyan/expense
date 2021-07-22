package bht.expense.bpm.design.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/27 17:02
 */
@ApiModel(value = "流程部署ID")
@Data
@TableName(value = "bus_activiti_deployment")
public class ActivitiDeploymentEntity {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    @ApiModelProperty(value="企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value="类型，1报销申请，2出差申请，3借款申请")
    private String type;

    @ApiModelProperty(value="部署时返回的ID")
    private String deploymentId;
}
