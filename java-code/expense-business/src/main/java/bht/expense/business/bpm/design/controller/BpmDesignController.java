package bht.expense.business.bpm.design.controller;

import bht.expense.business.bpm.design.service.BpmDesignService;
import bht.expense.business.common.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/26 11:24
 */
@Api(tags = "电子流设计")
@RestController
@RequestMapping("bpm/design")
@Slf4j
public class BpmDesignController {

    @Autowired
    BpmDesignService bpmDesignService;

    @ApiOperation("字符串部署流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true),
            @ApiImplicitParam(name = "xmlText", value = "xml文件字符串", required = true),
            @ApiImplicitParam(name = "workflowName", value = "流程名称", required = true),
            @ApiImplicitParam(name = "type", value = "1报销申请，2出差申请，3借款申请", required = true)
    })
    @PostMapping("/design/deploy/str")
    public ResultDto depolyWorkflowByStr(@RequestParam("enterpriseId") Long enterpriseId, @RequestParam("xmlText") String xmlText, @RequestParam("type") String type , @RequestParam("workflowName") String workflowName)
    {
        return bpmDesignService.depolyWorkflowByStr(enterpriseId, xmlText, type, workflowName);
    }

    @ApiOperation(value = "企业ID , type= 1报销申请，2出差申请，3借款申请")
    @GetMapping(value = "/design/getDefinitionXMLDeployId/{enterpriseId}/{type}")
    public ResultDto getProcessDefineXMLByDeployId(@PathVariable("enterpriseId") Long enterpriseId , @PathVariable("type") String type)
    {
        return bpmDesignService.getProcessDefineXMLByDeployId(enterpriseId, type);
    }


    @ApiOperation("查询流程用户配置")
    @ApiImplicitParam(name = "id", value = "用户任务环节id", required = true)
    @GetMapping(value = "/design/usertasks/{id}")
    public ResultDto getUsertaskSet(@PathVariable String id)
    {
        return bpmDesignService.getUsertaskSet(id);
    }

    @ApiOperation("新增配置用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stepid", value = "用户流程配置id", required = true),
            @ApiImplicitParam(name = "auditUserDtos", value = "配置用户json", required = true)
    })
    @PostMapping("/design/stepuser/add")
    public ResultDto insertUserSet(@RequestParam("stepid") String stepid, @RequestParam("auditUserDtos") String auditUserDtos)
    {
        return bpmDesignService.insertUserSet(stepid, auditUserDtos);
    }

    @ApiOperation("删除配置用户")
    @DeleteMapping("/design/stepuser/del/{stepId}")
    public ResultDto delStepUser(@PathVariable(value = "stepId") String stepId)
    {
        return bpmDesignService.delStepUser(stepId);
    }
}
