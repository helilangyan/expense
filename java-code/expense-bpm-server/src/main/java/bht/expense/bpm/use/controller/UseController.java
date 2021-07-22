package bht.expense.bpm.use.controller;

import bht.expense.bpm.bill.entity.ActivitiBillEntity;
import bht.expense.bpm.bill.service.ActivitiBillService;
import bht.expense.bpm.common.PageListDto;
import bht.expense.bpm.common.ResultCode;
import bht.expense.bpm.common.ResultDto;
import bht.expense.bpm.design.entity.ActivitiStepUser;
import bht.expense.bpm.design.service.DesignService;
import bht.expense.bpm.security.user.entity.UserEntity;
import bht.expense.bpm.use.dto.TaskListDto;
import bht.expense.bpm.use.dto.UserHistoricTaskDto;
import bht.expense.bpm.use.dto.UserWaitHandTaskDto;
import bht.expense.bpm.use.service.WorkflowTaskService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 姚轶文
 * @date 2021/6/10 14:27
 */
@Api(tags = "工作流 - 我的电子流")
@RestController
@RequestMapping("workflow/use")
@Slf4j
public class UseController {

    @Autowired
    private WorkflowTaskService workflowTaskService;

    @Autowired
    private DesignService workflowDefinitionService;

    @Autowired
    ActivitiBillService activitiBillService;

    @ApiOperation(value = "获取配置用户列表")
    @PostMapping("stepuser/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessKey", value = "业务id" , required = false),
            @ApiImplicitParam(name = "key", value = "电子流key = 企业ID_ 1报销申请，2出差申请，3借款申请", required = true),
            @ApiImplicitParam(name = "taskId", value = "任务id" , required = false)
    })
    public ResultDto getTaskAuditors(String businessKey, String key, String taskId){
        Map<String,Object> result = new HashMap<>();
        result.put("data",null);
        result.put("approve",0);
        List<ActivitiStepUser> activitiStepUsers;
        try {
            //String stepId = workflowTaskService.getNextStep(billId,taskId,key);
            Map<String,Object> step = workflowTaskService.getNextStepByFlow(businessKey,taskId,key);
            //获取流程部署id,获取第一步id,查询人
            if(!CollectionUtils.isEmpty(step) && !StringUtils.isEmpty(step.get("stepId"))){
                activitiStepUsers = workflowDefinitionService.getActivitiStepUser(step.get("stepId").toString());
                result.put("data",activitiStepUsers);
                result.put("approve",step.get("approve"));
            }
        }catch (Exception e){
            log.error("查询配置用户报错:",e);
            return ResultDto.failure(ResultCode.FAILURE,result);
        }
        return ResultDto.success(result);
    }

    @ApiOperation("新建电子流转交下一步")
    @PostMapping("/start")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "billId",value = "业务id"),
            @ApiImplicitParam(name = "key",value = "电子流key",required = true),
            @ApiImplicitParam(name = "formId",value = "表单id",required = false),
            @ApiImplicitParam(name = "title",value = "电子流名称",required = true),
            @ApiImplicitParam(name = "details",value = "表单值json",required = true),
            @ApiImplicitParam(name = "nextUserid",value = "下步处理人id",required = true),
            @ApiImplicitParam(name = "files",value = "上传文件id 多个,分割")
    })
    public ResultDto startBill(String billId, String key, String title, String nextUserid){
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //保存业务单
        //String bill = workflowBillService.addBill(billId,employeeRecordEntity.getId(),title,files,formId,details,key,nextUserid);
        //启动流程
        //String pid = workflowTaskService.startWorkflow(key,userEntity.getId(),nextUserid,bill,title,null);
        String pid = workflowTaskService.startWorkflow(key,userEntity.getId(),nextUserid,billId,title,null);
        //更新业务单
//        if(pid!=null){
//            workflowBillService.updateBill(bill,null,null,null,pid,2,null);
//        }
        ActivitiBillEntity activitiBillEntity = new ActivitiBillEntity();
        activitiBillEntity.setBpmKey(key);
        activitiBillEntity.setCreator(userEntity.getId());
        activitiBillEntity.setBusinessId(Long.parseLong(billId));
        activitiBillEntity.setBusinessType(key.substring(key.length()-1));
        activitiBillEntity.setCreateTime(new Date());
        activitiBillEntity.setInstanceId(pid);
        activitiBillEntity.setNextId(Long.parseLong(nextUserid));
        activitiBillEntity.setTitle(title);
        activitiBillEntity.setStatus("2");
        activitiBillService.insert(activitiBillEntity);
        //return ResultDto.success(bill);
        return ResultDto.success(activitiBillEntity.getId());
    }

//    @ApiOperation("查询我的电子流")
//    @GetMapping("/mytasks")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "startUserName",value = "发起人姓名"),
//            @ApiImplicitParam(name = "type",value = "电子流类型"),
//            @ApiImplicitParam(name = "status",value = "状态:1 处理中,2 挂起中,3 待提交,9 已结束"),
//            @ApiImplicitParam(name = "page",value = "当前页",required = true),
//            @ApiImplicitParam(name = "limit",value = "每页记录数",required = true)
//    })
//    public ResultDto getMyTasks(String startUserName, String type, String status,int page,int limit){
//        UserEntity userEntity = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        PageInfo<UserWaitHandTaskDto> pageInfo = workflowTaskService.myTasks(userEntity.getId(), startUserName, type, status, page, limit);
//        PageListDto<UserWaitHandTaskDto> pageListDto  = new PageListDto<>();
//        pageListDto.setData(pageInfo.getList());
//        //设置当前登录用户
//        if(pageInfo.getList()!=null){
//            List<UserWaitHandTaskDto> result = pageInfo.getList().stream().peek(userWaitHandTaskDto -> userWaitHandTaskDto.setLoginUserid(userEntity.getId())).collect(Collectors.toList());
//            pageListDto.setData(result);
//        }
//        pageListDto.setCount(pageInfo.getTotal());
//
//        return ResultDto.success(pageListDto);
//    }

    @ApiOperation("待审核列表")
    @PostMapping("/my-tasks")
    public TaskListDto myTasks(String key , String userId, int page, int limit)
    {
        TaskListDto taskListDto = workflowTaskService.myTasks(key, userId,page,limit);
        return taskListDto;
    }



    @ApiOperation("审核流程")
    @PostMapping("/approve")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "billId",value = "bill表id"),
            @ApiImplicitParam(name = "approve",value = "审核结果",required = true),
            @ApiImplicitParam(name = "comment",value = "审核意见"),
            @ApiImplicitParam(name = "nextUserId",value = "下步处理人id"),
            @ApiImplicitParam(name = "taskId",value = "电子流Id",required = true)

    })
    public ResultDto approveBill(String billId, String approve, String comment, String nextUserId,String taskId){
        UserEntity userEntity = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //审核流程
        boolean isEnd = workflowTaskService.completeWorkflowTask(taskId,userEntity.getId(),nextUserId,comment,approve,null);
        //更新业务单
        if(isEnd){
            if(approve!=null&&!"pass".equals(approve)){
                activitiBillService.updateBill(billId,null,null,"3",new Date());

            }else{
                activitiBillService.updateBill(billId,null,null,"9",new Date());
            }
        }
        return ResultDto.success();
    }

    @ApiOperation("查询电子流图历史记录")
    @ApiImplicitParam(name = "businessKey",value = "业务id")
    @PostMapping("/history/pic")
    public ResultDto getTaskHistoriesPic(String businessKey ,String userId){
        Map<String,Object> map = workflowTaskService.getFlowPicByBusinessKey(businessKey, userId);
        return ResultDto.success(map);
    }

    @ApiOperation("查询电子流历史记录")
    @ApiImplicitParam(name = "businessId",value = "业务id")
    @GetMapping("/history/records/{businessId}")
    public ResultDto getTaskHistoriesRecord(@PathVariable("businessId") String businessId){
        //UserEntity userEntity = (UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserHistoricTaskDto> list = workflowTaskService.getAllHistoryByBusinessKey(businessId);
        return ResultDto.success(list);
    }


    @ApiOperation("查看我已审核过的流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "登陆人ID"),
            @ApiImplicitParam(name = "startUserName",value = "发起人姓名"),
            @ApiImplicitParam(name = "type",value = "电子流类型"),
            @ApiImplicitParam(name = "status",value = "状态:1 处理中,2 挂起中,3 待提交,9 已结束"),
            @ApiImplicitParam(name = "page",value = "当前页",required = true),
            @ApiImplicitParam(name = "limit",value = "每页记录数",required = true)
    })
    @PostMapping("/my-tasks-done")
    public ResultDto myTaskDone(String userId, String startUserName, String type, String status, int page, int limit)
    {
        PageInfo<UserWaitHandTaskDto> pageInfo = workflowTaskService.myTaskDone(userId, startUserName, type, status, page, limit);
        PageListDto<UserWaitHandTaskDto> pageListDto  = new PageListDto<>();
        pageListDto.setData(pageInfo.getList());
        pageListDto.setCount(pageInfo.getTotal());
        return ResultDto.success(pageListDto);
    }

}
