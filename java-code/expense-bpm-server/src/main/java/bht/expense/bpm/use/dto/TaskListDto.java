package bht.expense.bpm.use.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/6/16 17:32
 */
@Data
@ApiModel(value = "待办流程记录--列表")
public class TaskListDto {

    @ApiModelProperty(value = "当前登录用户id")
    private long count;

    @ApiModelProperty(value = "结果集")
    private List<UserWaitHandTaskDto> list;
}
