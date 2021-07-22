package bht.expense.business.bpm.use.controller;

import bht.expense.business.bpm.use.dto.UserWaitHandTaskDto;
import bht.expense.business.bpm.use.service.BpmUseService;
import bht.expense.business.common.PageListDto;
import bht.expense.business.common.ResultDto;
import bht.expense.business.security.entity.UserEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/6/10 15:06
 */
@Api(tags = "电子流使用")
@RestController
@RequestMapping("bpm/use")
@Slf4j
public class BpmUseController {

    @Autowired
    BpmUseService bpmUseService;

    @ApiOperation(value = "获取配置用户列表")
    @PostMapping("stepuser/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessKey", value = "业务id" , required = false),
            @ApiImplicitParam(name = "key", value = "电子流key = 企业ID_ 1报销申请，2出差申请，3借款申请", required = true),
            @ApiImplicitParam(name = "taskId", value = "任务id" , required = false)
    })
    public ResultDto getTaskAuditors(String businessKey, String key,String taskId)
    {
        return bpmUseService.getTaskAuditors(businessKey, key, taskId);
    }

    @ApiOperation("查询电子流图历史记录")
    @ApiImplicitParam(name = "businessKey",value = "业务id")
    @PostMapping("/history/pic")
    public ResultDto getTaskHistoriesPic(String businessKey)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bpmUseService.getTaskHistoriesPic(businessKey, userEntity.getId());
    }

    @ApiOperation("查看我已审核过的流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startUserName",value = "发起人姓名"),
            @ApiImplicitParam(name = "type",value = "电子流类型"),
            @ApiImplicitParam(name = "status",value = "状态:1 处理中,2 挂起中,3 待提交,9 已结束"),
            @ApiImplicitParam(name = "page",value = "当前页",required = true),
            @ApiImplicitParam(name = "limit",value = "每页记录数",required = true)
    })
    @PostMapping("/my-tasks-done")
    public ResultDto myTaskDone(String startUserName, String type, String status, int page, int limit)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bpmUseService.myTaskDone(userEntity.getId(), startUserName, type, status, page, limit);
    }

    @ApiOperation("查询电子流历史记录")
    @ApiImplicitParam(name = "businessId",value = "业务id")
    @GetMapping("/history/records/{businessId}")
    public ResultDto getTaskHistoriesRecord(@PathVariable("businessId") String businessId){

        return bpmUseService.getTaskHistoriesRecord(businessId);
    }

}
