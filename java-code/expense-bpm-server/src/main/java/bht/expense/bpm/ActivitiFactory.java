package bht.expense.bpm;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @AUTHOR: GC
 * @DATE: 2020-10-19
 * @VERSION: 1.0
 * @DESCRIPTION: activiti工厂类
 */
@Component
public class ActivitiFactory {
    @Autowired
    protected ProcessRuntime processRuntime;// activiti7 流程实例控制服务
    @Autowired
    protected RuntimeService runtimeService;//activiti6 流程运行控制服务
    @Autowired
    protected RepositoryService repositoryService;//activiti6 流程存储服务
    @Autowired
    protected TaskRuntime taskRuntime;//activiti7 与taskService 类似
    @Autowired
    protected TaskService taskService;//activiti6 运行中流程管理服务
    @Autowired
    protected HistoryService historyService;//activiti6 运行结束流程查询服务
}
