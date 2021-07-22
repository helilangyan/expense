package bht.expense.bpm.bill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 姚轶文
 * @date 2021/6/17 10:22
 */
@ApiModel(value="业务流程数据")
@Data
@TableName(value = "bus_activiti_bill")
public class ActivitiBillEntity {

    @TableId(value = "id")
    @ApiModelProperty(value="主键")
    private Long id;

    @ApiModelProperty(value="标题名称")
    private String title;

    @ApiModelProperty(value="电子流实例id")
    private String instanceId;

    @ApiModelProperty(value="发起人")
    private String creator;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="结束时间")
    private Date endTime;

    @ApiModelProperty(value="业务ID")
    private Long businessId;

    @ApiModelProperty(value="业务类型，1报销申请，2出差申请，3借款申请")
    private String businessType;

    @ApiModelProperty(value="状态 0 删除 1 保存 2 发起  3 驳回归档 9 正常归档 ")
    private String status;

    @ApiModelProperty(value="下步处理人id")
    private Long nextId;

    @ApiModelProperty(value="电子流key")
    private String bpmKey;
}
