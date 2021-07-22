package bht.expense.bpm.design.service;

import bht.expense.bpm.ActivitiFactory;
import bht.expense.bpm.design.dao.ActivitiDeploymentMapper;
import bht.expense.bpm.design.dao.ActivitiStepMapper;
import bht.expense.bpm.design.dao.ActivitiStepUserMapper;
import bht.expense.bpm.design.entity.ActivitiDeploymentEntity;
import bht.expense.bpm.design.entity.ActivitiStep;
import bht.expense.bpm.design.entity.ActivitiStepUser;
import bht.expense.bpm.design.entity.AuditUserDto;
import com.alibaba.fastjson.JSONObject;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/26 10:16
 */
@Service
public class DesignService extends ActivitiFactory {

    @Autowired
    ActivitiStepMapper activitiStepMapper;

    @Autowired
    ActivitiDeploymentMapper activitiDeploymentMapper;

    @Autowired
    ActivitiStepUserMapper activitiStepUserMapper;
    /**
     * @Description: 通过字符串部署流程
     * @param xml 流程xml String
     * @param flowName 流程名称
     * @param flowKey 流程key
     * @Return org.activiti.engine.repository.Deployment
     * @Author: GC
     * @Date: 2020/10/26 17:39
     */
    @Transactional
    public Deployment depolyWorkflowByStr(String xml, String flowName, String flowKey , Long enterpriseId , String type) throws Exception {
        Deployment deployment;
        //初始化流程
        deployment = repositoryService.createDeployment()
                .addString(flowName+".bpmn20.xml",xml)
                //.addInputStream(multipartFile.getOriginalFilename(), multipartFile.getInputStream())
                .name(flowName)
                .key(flowKey)
                .deploy();
        //保存流程环节
        if (deployment != null) {
            //获取流程部署文件流
            InputStream resouceStream = repositoryService.getResourceAsStream(deployment.getId(),
                    flowName+".bpmn20.xml");
            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader in;
            XMLStreamReader xtr;
            in = new InputStreamReader(resouceStream, StandardCharsets.UTF_8);
            xtr = xif.createXMLStreamReader(in);
            //文件流转换成流程部署模型
            BpmnModel model = new BpmnXMLConverter().convertToBpmnModel(xtr);
            //获取所有流程节点
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            ActivitiStep step;
            //用户节保存入库
            for (FlowElement e : flowElements) {
                if (e instanceof UserTask) {
                    UserTask userTask = ((UserTask)e);
                    step = new ActivitiStep();
                    step.setFlowId(deployment.getId());
                    step.setKey(flowKey);
                    step.setStepId(userTask.getId());
                    step.setName(userTask.getName());
                    activitiStepMapper.insert(step);
                }
            }
        }

        activitiDeploymentMapper.deleteByEnterpriseIdAndType(enterpriseId,type);
        ActivitiDeploymentEntity activitiDeploymentEntity = new ActivitiDeploymentEntity();
        activitiDeploymentEntity.setDeploymentId(deployment.getId());
        activitiDeploymentEntity.setEnterpriseId(enterpriseId);
        activitiDeploymentEntity.setType(type);
        activitiDeploymentMapper.insert(activitiDeploymentEntity);

        return deployment;
    }

    /**
     * @Description: 通过部署id 查询流程xml
     * @param deployId 部署id
     * @Return java.io.InputStream
     * @Author:GC
     * @Date: 2020/11/4 17:49
     */
    public InputStream getProcessDefXmlByDeployId(String deployId){
        //查询流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
    }


    /**
     * @param stepId 环节配置表id
     * @Description: 获取用户环节配置审核用户
     * @Return java.util.List<com.bht.hrms.sys.workflow.entity.ActivitiStepUser>
     * @Author:GC
     * @Date: 2020/10/22 10:49
     */
    public List<ActivitiStepUser> getActivitiStepUser(String stepId) {
        return activitiStepUserMapper.selectByStepid(stepId);
    }

    /**
     * @param stepId 环节表id
     * @param operator 操作用户
     * @param type 类型
     * @param auditUserDtos 审核用户集合
     * @Description: 添加用户环节审核用户
     * @Return void
     * @Author:GC
     * @Date: 2020/10/22 10:49
     */
    @Transactional
    public void insertActivitiStepUser(String stepId, String operator, Integer type, String auditUserDtos) {
        List<AuditUserDto> auditUserDtoList = JSONObject.parseArray(auditUserDtos,AuditUserDto.class);
        activitiStepUserMapper.logicDel(stepId);
        if(!CollectionUtils.isEmpty(auditUserDtoList)){
            auditUserDtoList.forEach(auditUserDto -> {
                ActivitiStepUser activitiStepUser = new ActivitiStepUser();
                activitiStepUser.setOperatorId(auditUserDto.getUserid());
                activitiStepUser.setOperatorName(auditUserDto.getUsername());
                activitiStepUser.setStatus(1);
                activitiStepUser.setStepId(stepId);
                activitiStepUser.setType(type);
                activitiStepUser.setUpdateTime(new Date());
                activitiStepUser.setUpdator(operator);
                activitiStepUserMapper.insert(activitiStepUser);
            });
        }

    }

    /**
     * @Description: 删除流程环节
     * @param stepId 流程用户环节id
     * @Return void
     * @Author:GC
     * @Date: 21.2.25 10:54
     */
    @Transactional
    public void delStepUser(String stepId) {
        activitiStepUserMapper.logicDel(stepId);
    }
}
