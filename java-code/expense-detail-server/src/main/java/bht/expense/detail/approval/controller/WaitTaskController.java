package bht.expense.detail.approval.controller;

import bht.expense.detail.apply.borrow.dto.ApplyBorrowInfoDto;
import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.borrow.service.ApplyBorrowService;
import bht.expense.detail.apply.expense.dto.ApplyExpenseInfoDto;
import bht.expense.detail.apply.expense.service.ApplyExpenseService;
import bht.expense.detail.apply.travel.dto.ApplyTravelInfoDto;
import bht.expense.detail.apply.travel.service.ApplyTravelService;
import bht.expense.detail.approval.dto.TaskListDto;
import bht.expense.detail.approval.dto.UserWaitHandTaskDto;
import bht.expense.detail.approval.service.WaitTaskService;
import bht.expense.detail.common.PageListDto;
import bht.expense.detail.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/6/16 16:40
 */
@Api(tags = "审批待办")
@RestController
@RequestMapping("approval-task")
public class WaitTaskController {

    @Autowired
    WaitTaskService waitTaskService;

    @Autowired
    ApplyBorrowService applyBorrowService;

    @Autowired
    ApplyExpenseService applyExpenseService;

    @Autowired
    ApplyTravelService applyTravelService;



    @ApiOperation("待审批列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "firstResult", value = "从第几条开始查", required = true),
            @ApiImplicitParam(name = "maxResults", value = "查多少条记录", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID"),
            @ApiImplicitParam(name = "key", value = "企业ID")
    })
    @PostMapping("/list")
    public ResultDto list( String key , String userId, int firstResult, int maxResults)
    {
        TaskListDto taskListDto = waitTaskService.myTasks(key, userId, firstResult, maxResults);

        long count = taskListDto.getCount();

        for(UserWaitHandTaskDto userWaitHandTaskDto : taskListDto.getList())
        {
            key  = userWaitHandTaskDto.getKey();
            key = key.substring(key.length()-1);

            if(key.equals("3"))
            {
                ApplyBorrowInfoDto applyBorrowInfoDto = applyBorrowService.findById(Long.parseLong(userWaitHandTaskDto.getBusinessKey()));
                userWaitHandTaskDto.setApplyName(applyBorrowInfoDto.getApplyName());
            }
            if(key.equals("1"))
            {
                ApplyExpenseInfoDto applyExpenseInfoDto = applyExpenseService.findById(Long.parseLong(userWaitHandTaskDto.getBusinessKey()));
                userWaitHandTaskDto.setApplyName(applyExpenseInfoDto.getApplyExpenseEntity().getApplyName());
            }
            if(key.equals("2"))
            {
                ApplyTravelInfoDto applyTravelInfoDto = applyTravelService.findById(Long.parseLong(userWaitHandTaskDto.getBusinessKey()));
                userWaitHandTaskDto.setApplyName(applyTravelInfoDto.getApplyTravelEntity().getApplyName());
            }
        }
        PageListDto<UserWaitHandTaskDto> pageListData = new PageListDto();
        pageListData.setData(taskListDto.getList());
        pageListData.setCount(count);
        return ResultDto.success(pageListData);
    }
}
