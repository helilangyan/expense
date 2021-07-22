package bht.expense.business.bpm.design.caller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.caller.ApplyBorrowCallerError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/26 10:58
 */
@FeignClient(value = "expense-bpm-server",fallback = BpmDesignCallerError.class, contextId = "BpmDesignCaller")
public interface BpmDesignCaller {

    @PostMapping("/design/deploy/str")
    ResultDto depolyWorkflowByStr(@RequestParam("enterpriseId") Long enterpriseId, @RequestParam("xmlText") String xmlText, @RequestParam("type") String type , @RequestParam("workflowName") String workflowName);

    @GetMapping(value = "/design/getDefinitionXMLDeployId/{enterpriseId}/{type}")
    ResultDto getProcessDefineXMLByDeployId(@PathVariable("enterpriseId") Long enterpriseId , @PathVariable("type") String type);

    @GetMapping(value = "/design/usertasks/{id}")
    ResultDto getUsertaskSet(@PathVariable String id);

    @PostMapping("/design/stepuser/add")
    ResultDto insertUserSet(@RequestParam("stepid") String stepid, @RequestParam("auditUserDtos") String auditUserDtos);

    @DeleteMapping("/design/stepuser/del/{stepId}")
    ResultDto delStepUser(@PathVariable(value = "stepId") String stepId);
}
