package bht.expense.bpm.design.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/26 10:18
 */

@ApiModel(value = "流程环节")
@Data
@TableName(value = "bus_activiti_step")
public class ActivitiStep {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键")
    private String id;

    /**
     * 环节id
     */
    @TableField(value = "step_id")
    @ApiModelProperty(value="环节id")
    private String stepId;

    /**
     * 流程定义id
     */
    @TableField(value = "flow_id")
    @ApiModelProperty(value="流程定义id")
    private String flowId;

    /**
     * 环节名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="环节名称")
    private String name;

    /**
     * 流程key
     */
    @TableField(value = "key_")
    @ApiModelProperty(value="流程key")
    private String key;

    public static final String COL_ID = "id";

    public static final String COL_STEP_ID = "step_id";

    public static final String COL_FLOW_ID = "flow_id";

    public static final String COL_NAME = "name";

    public static final String COL_KEY = "key_";
}
