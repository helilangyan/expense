package bht.expense.bpm.use.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @AUTHOR: GC
 * @DATE: 2020-10-22
 * @VERSION: 1.0
 * @DESCRIPTION: 用户历史任务
 */
@Data
@ApiModel(value = "历史流程记录")
public class UserHistoricTaskDto {
    @ApiModelProperty(value = "当前处理人id")
    private String nextUserid;
    @ApiModelProperty(value = "当前处理人名称")
    private String nextUsername;
    @ApiModelProperty(value = "电子流名称")
    private String billName;
    @ApiModelProperty(value = "环节名称")
    private String stepName;
    @ApiModelProperty(value = "业务id")
    private String businessKey;
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
    @ApiModelProperty(value = "当前流程结束时间")
    private String endTime;
    @ApiModelProperty(value = "流程历时")
    private String duration;
    @ApiModelProperty(value = "审核意见")
    private String comment;
    @ApiModelProperty(value = "流程流向")
    private String approve;
    @ApiModelProperty(value = "流程状态 1 处理中 9 已完成")
    private String status;
    @ApiModelProperty(value = "流程环节关联文件")
    private List<String> files;

}
