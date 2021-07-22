package bht.expense.business.detail.approval.controller;

import bht.expense.business.bpm.use.service.BpmUseService;
import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.approval.servuce.ApprovalService;
import bht.expense.business.security.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 姚轶文
 * @date 2021/6/17 14:05
 */
@Api(tags = "流程审批")
@RestController
@RequestMapping("approval")
public class ApprovalController {

    @Autowired
    ApprovalService approvalService;

    @Autowired
    BpmUseService bpmUseService;

    @ApiOperation("待审批列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "firstResult", value = "从第几条开始查", required = true),
            @ApiImplicitParam(name = "maxResults", value = "查多少条记录", required = true),
            @ApiImplicitParam(name = "key", value = "流程KEY")
    })
    @PostMapping("/list")
    public ResultDto list(String key , int firstResult, int maxResults)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResultDto resultDto = approvalService.list(key, userEntity.getId(), firstResult, maxResults);
        return resultDto;
    }


    @ApiOperation("审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "billId",value = "业务id"),
            @ApiImplicitParam(name = "approve",value = "审核结果",required = true),
            @ApiImplicitParam(name = "comment",value = "审核意见"),
            @ApiImplicitParam(name = "nextUserId",value = "下步处理人id"),
            @ApiImplicitParam(name = "taskId",value = "电子流Id",required = true)
    })
    @PostMapping("workflow/use/approve")
    public ResultDto approveBill(String billId, String approve, String comment, String nextUserId, String taskId)
    {
        return bpmUseService.approveBill(billId, approve, comment, nextUserId, taskId);
    }

}
