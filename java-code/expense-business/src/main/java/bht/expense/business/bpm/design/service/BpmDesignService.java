package bht.expense.business.bpm.design.service;

import bht.expense.business.bpm.design.caller.BpmDesignCaller;
import bht.expense.business.common.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


/**
 * @author 姚轶文
 * @date 2021/4/26 11:03
 */
@Service
public class BpmDesignService {

    @Autowired
    BpmDesignCaller bpmDesignCaller;


    public ResultDto depolyWorkflowByStr(Long enterpriseId, String xmlText, String type , String workflowName)
    {
        return bpmDesignCaller.depolyWorkflowByStr(enterpriseId, xmlText, type, workflowName);
    }

    public ResultDto getProcessDefineXMLByDeployId(Long enterpriseId , String type)
    {
        return bpmDesignCaller.getProcessDefineXMLByDeployId(enterpriseId,type);
    }

    public ResultDto getUsertaskSet(String id)
    {
        return bpmDesignCaller.getUsertaskSet(id);
    }

    public ResultDto insertUserSet(String stepid, String auditUserDtos)
    {
        return bpmDesignCaller.insertUserSet(stepid, auditUserDtos);
    }

    public ResultDto delStepUser(String stepId)
    {
        return bpmDesignCaller.delStepUser(stepId);
    }
}
