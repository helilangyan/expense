package bht.expense.bpm.use.service;

import bht.expense.bpm.ActivitiFactory;
import bht.expense.bpm.bill.entity.ActivitiBillEntity;
import bht.expense.bpm.bill.service.ActivitiBillService;
import bht.expense.bpm.common.TimeUtil;
import bht.expense.bpm.use.dao.UserQueryTaskMapper;
import bht.expense.bpm.use.dto.TaskListDto;
import bht.expense.bpm.use.dto.UserHistoricTaskDto;
import bht.expense.bpm.use.dto.UserWaitHandTaskDto;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @author 姚轶文
 * @date 2021/6/10 14:33  工作流任务接口
 */

@Service
@Slf4j
public class WorkflowTaskService  extends ActivitiFactory {

    @Autowired
    private UserQueryTaskMapper userQueryTaskMapper;

    @Autowired
    ActivitiBillService activitiBillService;


    /**
     * @Description: 获取当前环节下一环节
     * @param billId 业务id
     * @param taskId 任务id
     * @param key 流程部署key
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Author:GC
     * @Date: 21.2.25 11:16
     */
    public Map<String,Object> getNextStepByFlow(String billId, String taskId, String key){
        Map<String,Object> reslut = new HashMap<>();
        List<UserTask> userTasks = new ArrayList<>();
        //获取流程部署实例
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentKey(key).latest().singleResult();
        //获取文件部署文件流
        InputStream resouceStream = repositoryService.getResourceAsStream(deployment.getId(),
                deployment.getName()+".bpmn20.xml");
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in;
        XMLStreamReader xtr = null;
        in = new InputStreamReader(resouceStream, StandardCharsets.UTF_8);
        try {
            xtr = xif.createXMLStreamReader(in);
        }
        catch (XMLStreamException e) {
            log.error("获取流程定义文件报错:",e);
        }
        //获取流程部署模型
        BpmnModel model = new BpmnXMLConverter().convertToBpmnModel(Objects.requireNonNull(xtr));
        //获取所有流程节点
        Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
        StringBuilder gateWay = new StringBuilder("0");
        //遍历流程节点
        if(StringUtils.isEmpty(billId)){
            flowElements.forEach(flowElement -> {
                //开始节点
                if(flowElement instanceof StartEvent){
                    getNextNode(flowElements,flowElement,null,userTasks,gateWay);
                }
            });
        }else{
            //流程历史实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(billId).singleResult();
            if(historicProcessInstance==null){
                flowElements.forEach(flowElement -> {
                    //开始节点
                    if(flowElement instanceof StartEvent){
                        getNextNode(flowElements,flowElement,null,userTasks,gateWay);
                    }
                });
            }else{
                //查询所有参数
                //LambdaQueryChainWrapper<BillFormDetail> queryChainWrapper = new LambdaQueryChainWrapper<>(billFormDetailMapper);
                //List<BillFormDetail> dbBillFormDetails = queryChainWrapper.eq(BillFormDetail::getBillId, billId).list();
                Map<String,Object> map = new HashMap<>();
                //dbBillFormDetails.forEach(billFormDetail -> map.put(billFormDetail.getColumnId(), isNumber(billFormDetail.getColumnValue()) ? Double.valueOf(billFormDetail.getColumnValue()) : billFormDetail.getColumnValue()));
                //当前任务
                Task task = taskService.createTaskQuery().processInstanceBusinessKey(billId).active().singleResult();
                if(task==null){
                    return null;
                }
                flowElements.forEach(flowElement -> {
                    //遍历出当前任务节点
                    if(Objects.equals(flowElement.getId(),task.getTaskDefinitionKey())){
                        getNextNode(flowElements,flowElement,map,userTasks,gateWay);
                    }
                });
            }
        }
        if(!CollectionUtils.isEmpty(userTasks)){
            reslut.put("stepId",userTasks.get(0).getId());
        }
        reslut.put("approve",gateWay.toString());
        return reslut;
    }

    /**
     * @Description: 获取流程下一节点
     * @param flowElements 流程节点
     * @param flowElement 当前节点
     * @param map 流程参数
     * @param nextUser 下一步审核人
     * @param gateWay 根据值判断是否网关
     * @Return void
     * @Author:GC
     * @Date: 21.2.25 11:25
     */
    private void getNextNode(Collection<FlowElement> flowElements, FlowElement flowElement, Map<String, Object> map,List<UserTask> nextUser,StringBuilder gateWay){
        //如果是结束节点
        if(flowElement instanceof EndEvent){
            //如果是子任务的结束节点
            if(getSubProcess(flowElements,flowElement) != null){
                flowElement = getSubProcess(flowElements,flowElement);
            }
        }
        //获取Task的出线信息--可以拥有多个
        List<SequenceFlow> outGoingFlows = null;
        if(flowElement instanceof org.activiti.bpmn.model.Task){
            outGoingFlows = ((org.activiti.bpmn.model.Task) flowElement).getOutgoingFlows();
        }else if(flowElement instanceof Gateway){
            outGoingFlows = ((Gateway) flowElement).getOutgoingFlows();
        }else if(flowElement instanceof StartEvent){
            outGoingFlows = ((StartEvent) flowElement).getOutgoingFlows();
        }else if(flowElement instanceof SubProcess){
            outGoingFlows = ((SubProcess) flowElement).getOutgoingFlows();
        }
        if(outGoingFlows != null && outGoingFlows.size()>0) {
            //遍历所有的出线--找到可以正确执行的那一条
            for (SequenceFlow sequenceFlow : outGoingFlows) {
                //1.有表达式，且为true
                //2.无表达式
                String expression = sequenceFlow.getConditionExpression();
                if(StringUtils.isEmpty(expression)||evalExpression(expression,map)){
                    //出线的下一节点
                    String nextFlowElementID = sequenceFlow.getTargetRef();
                    //查询下一节点的信息
                    FlowElement nextFlowElement = getFlowElementById(nextFlowElementID, flowElements);

                    //用户任务
                    if (nextFlowElement instanceof UserTask) {
                        nextUser.add((UserTask) nextFlowElement);
                    }
                    //排他网关
                    else if (nextFlowElement instanceof ExclusiveGateway) {
                        gateWay.setLength(0);
                        gateWay.append(((ExclusiveGateway)nextFlowElement).getOutgoingFlows().size());
                        getNextNode(flowElements, nextFlowElement, map, nextUser,gateWay);
                    }
                    //并行网关
                    else if (nextFlowElement instanceof ParallelGateway) {
                        getNextNode(flowElements, nextFlowElement, map, nextUser,gateWay);
                    }
                    //接收任务
                    else if (nextFlowElement instanceof ReceiveTask) {
                        getNextNode(flowElements, nextFlowElement, map, nextUser,gateWay);
                    }
                    //子任务的起点
                    else if(nextFlowElement instanceof StartEvent){
                        getNextNode(flowElements, nextFlowElement, map, nextUser,gateWay);
                    }
                    //结束节点
                    else if(nextFlowElement instanceof EndEvent){
                        getNextNode(flowElements, nextFlowElement, map, nextUser,gateWay);
                    }
                }
            }
        }
    }


    /**
     * @Description: 获取自流程
     * @param flowElements 流程任务节点
     * @param flowElement 当前节点
     * @Return org.activiti.bpmn.model.FlowElement
     * @Author:GC
     * @Date: 21.2.25 11:23
     */
    private FlowElement getSubProcess(Collection<FlowElement> flowElements,FlowElement flowElement){
        for(FlowElement flowElement1 : flowElements){
            if(flowElement1 instanceof SubProcess){
                for(FlowElement flowElement2 : ((SubProcess) flowElement1).getFlowElements()){
                    if(flowElement.equals(flowElement2)){
                        return flowElement1;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @Description: 执行表达式获得结果
     * @param expression 表达式
     * @param variables 参数
     * @Return boolean
     * @Author:GC
     * @Date: 21.2.25 11:27
     */
    private static boolean evalExpression(String expression,Map<String,Object> variables){
        try {
            ExpressionParser parser = new SpelExpressionParser();
            if(expression.contains("{")&&expression.contains("{")){
                expression = expression.substring(expression.lastIndexOf("{")+1,expression.lastIndexOf("}"));
            }
            EvaluationContext context = new StandardEvaluationContext();
            final String[] finalExpression = {expression};
            final AtomicReference<String>[] re = new AtomicReference[] {new AtomicReference<String>()};
            if(!CollectionUtils.isEmpty(variables)){
                variables.put("approve","pass");
                variables.forEach((k, v)-> {
                    context.setVariable(k,v) ;
                    re[0].set(finalExpression[0].replaceAll(k, "#" + k));
                    finalExpression[0] = re[0].toString();
                });
                Expression exp = parser.parseExpression(re[0].toString());
                return exp.getValue(context, Boolean.class);
            }
        }catch (Exception e){
            log.error("EL表达式解析失败:",e);
        }
        return false;
    }

    /**
     * @Description: 通过id获取流程节点
     * @param Id 节点id
     * @param flowElements
     * @Return org.activiti.bpmn.model.FlowElement
     * @Author:GC
     * @Date: 21.2.25 11:23
     */
    private FlowElement getFlowElementById(String Id,Collection<FlowElement> flowElements){
        for(FlowElement flowElement : flowElements){
            if(flowElement.getId().equals(Id)){
                //如果是子任务，则查询出子任务的开始节点
                if(flowElement instanceof SubProcess){
                    return getStartFlowElement(((SubProcess) flowElement).getFlowElements());
                }
                return flowElement;
            }
            if(flowElement instanceof SubProcess){
                FlowElement flowElement1 = getFlowElementById(Id,((SubProcess) flowElement).getFlowElements());
                if(flowElement1 != null){
                    return flowElement1;
                }
            }
        }
        return null;
    }

    /**
     * @Description: 获取流程开始节点
     * @param flowElements 流程节点集合
     * @Return org.activiti.bpmn.model.FlowElement
     * @Author:GC
     * @Date: 21.2.25 11:18
     */
    private FlowElement getStartFlowElement(Collection<FlowElement> flowElements){
        for (FlowElement flowElement :flowElements){
            if(flowElement instanceof StartEvent){
                return flowElement;
            }
        }
        return null;
    }

    private static boolean isNumber(String str){
        if(!StringUtils.isEmpty(str)){
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            return isNum.matches();
        }
        return false;
    }

    /**
     * @Description: 发起流程
     * @param workflowDefinitionKey 流程key
     * @param userId 发起用户
     * @param businessKey 业务key
     * @param nextUserId 下步审核人
     * @Return void
     * @Author:GC
     * @Date: 2020/10/22 13:43
     */
    @Transactional
    public String startWorkflow(String workflowDefinitionKey, String userId,String nextUserId, String businessKey,String businessName, Map<String,Object> variables) {
        try{
            //设置当前登录用户
            Authentication.setAuthenticatedUserId(userId);
            if(CollectionUtils.isEmpty(variables)){
                variables = new HashMap<>();
            }
            /*
            //获取表单
            FormDesignDto formDesignDto = billFormDetailMapper.getBillFormDetail(businessKey);
            //表单填写内容设置为流程参数
            if (formDesignDto!=null&&!CollectionUtils.isEmpty(formDesignDto.getFormDesignDetailDtos())){
                Map<String, Object> finalVariables = new HashMap<>();
                formDesignDto.getFormDesignDetailDtos().forEach(billFormDetail -> finalVariables.put(billFormDetail.getDetailFieldId(), billFormDetail.getDefaultValue()));
                variables.putAll(finalVariables);
            }
             */
            //启动流程
            ProcessInstance processInstance = processRuntime.start(
                    ProcessPayloadBuilder.start()
                            .withProcessDefinitionKey(workflowDefinitionKey)
                            .withProcessInstanceName(businessName)
                            .withVariables(variables)
                            .withBusinessKey(businessKey)
                            .build());
            //获取流程当前任务
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            if(task!=null){
                //设置流程下一步处理用户
                taskService.setAssignee(task.getId(),nextUserId);
            }
            return processInstance.getId();
        }catch (Exception e){
            log.error("发起电子流报错:",e);
        }
        return null;

    }

    /**
     * @Description: 查询我的电子流
     * @param userId 用户id
     * @param startUserName 发起人名称
     * @param type 电子流类型
     * @param status 状态
     * @param page 当前页
     * @param limit 每页记录数
     * @Return com.github.pagehelper.PageInfo
     * @Author:GC
     * @Date: 2020/10/27 16:25
     */
    @Deprecated
    public PageInfo<UserWaitHandTaskDto> myTasks(String userId, String startUserName, String type, String status, int page, int limit){
        PageHelper.startPage(page,limit);
        List<UserWaitHandTaskDto> userWaitHandTaskDtos = userQueryTaskMapper.selectUserQueryTasks(userId, startUserName, type, status);
        return new PageInfo<>(userWaitHandTaskDtos);
    }

    public TaskListDto myTasks(String key , String userId, int page, int limit)
    {

        //List<Task> taskList3 = taskService.createTaskQuery().processDefinitionKey(key).taskAssignee(userId).list();

        TaskListDto taskListDto =  new TaskListDto();

        List<UserWaitHandTaskDto> list = new ArrayList<>();

        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(userId);
        if(!StringUtils.isEmpty(key))
        {
            taskQuery.processDefinitionKey(key);
        }

        long count = taskQuery.count();
        taskQuery.listPage(page,limit);
        List<Task> taskList = taskQuery.list();

        //遍历任务列表
        for (Task task : taskList) {
            UserWaitHandTaskDto userWaitHandTaskDto = new UserWaitHandTaskDto();
            userWaitHandTaskDto.setTaskId(task.getId());
            userWaitHandTaskDto.setTaskName(task.getName());
            userWaitHandTaskDto.setLoginUserid(userId);
            userWaitHandTaskDto.setArriveTime(TimeUtil.dateFormat(task.getCreateTime()));


            String processInstanceId = task.getProcessInstanceId();
            org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

            userWaitHandTaskDto.setBusinessKey(processInstance.getBusinessKey());
            userWaitHandTaskDto.setStartTime(TimeUtil.dateFormat(processInstance.getStartTime()));
            userWaitHandTaskDto.setInstanceId(processInstanceId);
            userWaitHandTaskDto.setBillName(processInstance.getName());
            userWaitHandTaskDto.setKey(processInstance.getProcessDefinitionKey());

            ActivitiBillEntity activitiBillEntity = activitiBillService.findByBusinessIdAndInstanceId(processInstanceId, Long.parseLong(processInstance.getBusinessKey()));

            userWaitHandTaskDto.setBillId(Long.toString(activitiBillEntity.getId()));
            list.add(userWaitHandTaskDto);
        }


        taskListDto.setCount(count);
        taskListDto.setList(list);
        return taskListDto;
    }

    public PageInfo<UserWaitHandTaskDto> myTaskDone(String userId, String startUserName, String type, String status, int page, int limit)
    {
        PageHelper.startPage(page,limit);
        List<UserWaitHandTaskDto> userWaitHandTaskDtos = userQueryTaskMapper.myTaskDone(userId, startUserName, type, status);
        return new PageInfo<>(userWaitHandTaskDtos);
    }

    /**
     * @Description: 完成任务
     * @param taskId 任务id
     * @param userId 用户id
     * @param nextUserId 下步处理人id
     * @param comment 备注
     * @param approve 审核
     * @param variables 流程变量
     * @Return boolean 流程是否结束
     * @Author:GC
     * @Date: 2020/10/27 17:48
     */
    @Transactional
    public boolean completeWorkflowTask(String taskId,String userId,String nextUserId,String comment,String approve,Map<String,Object> variables) {
        //获取流程当前任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId;
        Authentication.setAuthenticatedUserId(userId);
        if(task!=null){
            processInstanceId = task.getProcessInstanceId();
            if(CollectionUtils.isEmpty(variables)){
                variables = new HashMap<>();
            }
            variables.put("approve",approve);
            if(!StringUtils.isEmpty(comment)){
                //设置审核意见
                taskService.addComment(taskId,null,comment);
            }
            //完成任务
            taskService.complete(task.getId(),variables);


        }else{
            throw new RuntimeException("任务不存在!");
        }
        if(!StringUtils.isEmpty(nextUserId)&&!StringUtils.isEmpty(processInstanceId)){
            //获取当前任务
            task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
            if (task!=null){
                //设置下步审核人
                taskService.setAssignee(task.getId(),nextUserId);
            }
        }
        org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return processInstance == null;
    }


    /**
     * @Description: 获取流程图历史
     * @param billId 业务id
     * @param userId 用户id
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Author:GC
     * @Date: 2020/10/30 11:30
     */
    public Map<String,Object> getFlowPicByBusinessKey(String billId,String userId){
        try {
            //获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(billId).singleResult();
            InputStream resouceStream = null;
            if(historicProcessInstance!=null){
                //获取流程定义实例
                ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult();
                //获取流程部署文件流
                resouceStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),processDefinition.getResourceName());
            }
            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader in = null;
            XMLStreamReader xtr;
            if (resouceStream != null) {
                in = new InputStreamReader(resouceStream, StandardCharsets.UTF_8);
            }
            xtr = xif.createXMLStreamReader(in);
            //获取流程部署模型
            BpmnModel model = new BpmnXMLConverter().convertToBpmnModel(xtr);
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            Map<String, String> map = new HashMap<>();
            for (FlowElement flowElement : flowElements) {
                //判断是否是连线
                if (flowElement instanceof SequenceFlow) {
                    SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
                    String ref = sequenceFlow.getSourceRef();
                    String targetRef = sequenceFlow.getTargetRef();
                    map.put(ref + targetRef, sequenceFlow.getId());
                }
            }

            //获取流程实例 历史节点(全部)
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(Objects.requireNonNull(historicProcessInstance).getId())
                    .list();
            //各个历史节点   两两组合 key
            Set<String> keyList = new HashSet<>();
            for (HistoricActivityInstance i : list) {
                for (HistoricActivityInstance j : list) {
                    if (i != j) {
                        keyList.add(i.getActivityId() + j.getActivityId());
                    }
                }
            }
            //高亮连线ID
            Set<String> highLine = new HashSet<>();
            keyList.forEach(s -> highLine.add(map.get(s)));


            //获取流程实例 历史节点（已完成）
            List<HistoricActivityInstance> listFinished = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .finished()
                    .list();
            //高亮节点ID
            Set<String> highPoint = new HashSet<>();
            listFinished.forEach(s -> highPoint.add(s.getActivityId()));

            //获取流程实例 历史节点（待办节点）
            List<HistoricActivityInstance> listUnFinished = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .unfinished()
                    .list();

            //需要移除的高亮连线
            Set<String> set = new HashSet<>();
            //待办高亮节点
            Set<String> waitingToDo = new HashSet<>();
            listUnFinished.forEach(s -> {
                waitingToDo.add(s.getActivityId());
                for (FlowElement flowElement : flowElements) {
                    //判断是否是 用户节点
                    if (flowElement instanceof UserTask) {
                        UserTask userTask = (UserTask) flowElement;
                        if (userTask.getId().equals(s.getActivityId())) {
                            List<SequenceFlow> outgoingFlows = userTask.getOutgoingFlows();
                            //因为 高亮连线查询的是所有节点  两两组合 把待办 之后  往外发出的连线 也包含进去了  所以要把高亮待办节点 之后 即出的连线去掉
                            if (outgoingFlows != null && outgoingFlows.size() > 0) {
                                outgoingFlows.forEach(a -> {
                                    if (a.getSourceRef().equals(s.getActivityId())) {
                                        set.add(a.getId());
                                    }
                                });
                            }
                        }
                    }
                }
            });

            highLine.removeAll(set);
            Set<String> iDo = new HashSet<>(); //存放 高亮 我的办理节点
            //当前用户已完成的任务
            List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                    .taskAssignee(userId)
                    .finished()
                    .processInstanceId(historicProcessInstance.getId()).list();
            taskInstanceList.forEach(a -> iDo.add(a.getTaskDefinitionKey()));
            Map<String, Object> reMap = new HashMap<>();
            reMap.put("highLightPoint", highPoint);
            reMap.put("highLightLine", highLine);
            reMap.put("waitToDo", waitingToDo);
            reMap.put("iDo", iDo);
            return  reMap;
        }
        catch (XMLStreamException e) {
            log.error("未查询到流程xml:",e);
        }
        return null;
    }

    /**
     * @Description: 获取流程历史
     * @param businessId 业务id
     * @Return java.util.List<com.bht.hrms.sys.workflow.flow.dto.UserHistoricTaskDto>
     * @Author:GC
     * @Date: 2020/10/30 11:31
     */
    public List<UserHistoricTaskDto> getAllHistoryByBusinessKey(String businessId){
        return userQueryTaskMapper.selectHistoricTasks(businessId);
    }
}
