package bht.expense.detail.approval.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/6/16 16:38
 */
@Data
@ApiModel(value = "待办流程记录")
public class UserWaitHandTaskDto {
    @ApiModelProperty(value = "当前登录用户id")
    private String loginUserid;
    @ApiModelProperty(value = "当前处理人id")
    private String nextUserid;
    @ApiModelProperty(value = "当前处理人名称")
    private String nextUsername;
    @ApiModelProperty(value = "电子流名称")
    private String billName;
    @ApiModelProperty(value = "业务id")
    private String businessKey;
    @ApiModelProperty(value = "流程key")
    private String key;
    @ApiModelProperty(value = "实例id")
    private String instanceId;
    @ApiModelProperty(value = "任务id")
    private String taskId;
    @ApiModelProperty(value = "发起人id")
    private String initor;
    @ApiModelProperty(value = "发起人名称")
    private String initorName;
    @ApiModelProperty(value = "电子流类型")
    private String type;
    @ApiModelProperty(value = "流程开始时间")
    private String startTime;
    @ApiModelProperty(value = "当前流程到达时间")
    private String arriveTime;
    @ApiModelProperty(value = "流程历时")
    private String duration;
    @ApiModelProperty(value = "流程状态 1 处理中 2 挂起 3 待提交 9 已结束")
    private String status;
    @ApiModelProperty(value = "环节名称")
    private String taskName;

    @ApiModelProperty(value = "申请名称")
    private String applyName;

    @ApiModelProperty(value = "bill表ID")
    private String billId;
}
