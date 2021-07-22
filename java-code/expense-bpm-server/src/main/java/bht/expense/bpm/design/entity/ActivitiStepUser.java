package bht.expense.bpm.design.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value="流程环节审核人")
@Data
@TableName(value = "bus_activiti_step_user")
public class ActivitiStepUser {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键")
    private String id;

    /**
     * 关联表id
     */
    @TableField(value = "step_id")
    @ApiModelProperty(value="关联表id")
    private String stepId;

    /**
     * 处理人名称/处理组名称
     */
    @TableField(value = "operator_name")
    @ApiModelProperty(value="处理人名称/处理组名称")
    private String operatorName;

    /**
     * 处理人/处理组 id
     */
    @TableField(value = "operator_id")
    @ApiModelProperty(value="处理人/处理组 id")
    private String operatorId;

    /**
     * 类型 0 用户 1 部门
     */
    @TableField(value = "type")
    @ApiModelProperty(value="类型 0 用户 1 部门")
    private Integer type;

    /**
     * 状态 1 正常 0 删除
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态 1 正常 0 删除")
    private Integer status;

    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @TableField(value = "updator")
    @ApiModelProperty(value="更新用户")
    private String updator;

    public static final String COL_ID = "id";

    public static final String COL_STEP_ID = "step_id";

    public static final String COL_OPERATOR_NAME = "operator_name";

    public static final String COL_OPERATOR_ID = "operator_id";

    public static final String COL_TYPE = "type";

    public static final String COL_STATUS = "status";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_UPDATOR = "updator";
}