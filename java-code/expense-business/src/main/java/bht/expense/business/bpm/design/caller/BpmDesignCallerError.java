package bht.expense.business.bpm.design.caller;

import bht.expense.business.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/26 10:59
 */

@Slf4j
public class BpmDesignCallerError implements BpmDesignCaller{
    @Override
    public ResultDto depolyWorkflowByStr(Long enterpriseId, String xmlText, String type, String workflowName) {
        log.error("invoke expense-bpm-server: /design/deploy/str error");
        return null;
    }

    @Override
    public ResultDto getProcessDefineXMLByDeployId(Long enterpriseId, String type) {
        log.error("invoke expense-bpm-server: /design/deploy/getProcessDefineXMLByDeployId error");
        return null;
    }

    @Override
    public ResultDto getUsertaskSet(String id) {
        log.error("invoke expense-bpm-server: /design/deploy/getUsertaskSet error");
        return null;
    }

    @Override
    public ResultDto insertUserSet(String stepid, String auditUserDtos) {
        log.error("invoke expense-bpm-server: /design/deploy/insertUserSet error");
        return null;
    }

    @Override
    public ResultDto delStepUser(String stepId) {
        log.error("invoke expense-bpm-server: /design/deploy/delStepUser error");
        return null;
    }


}
