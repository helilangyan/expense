package bht.expense.bpm.design.controller;

import bht.expense.bpm.common.ResultCode;
import bht.expense.bpm.common.ResultDto;
import bht.expense.bpm.design.entity.ActivitiStepUser;
import bht.expense.bpm.design.service.ActivitiDeploymentService;
import bht.expense.bpm.design.service.DesignService;
import bht.expense.bpm.security.user.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/26 10:09
 */
@Api(tags = "工作流 - 电子流设计")
@RestController
@RequestMapping("design")
@Slf4j
public class DesignController {

    @Autowired
    DesignService designService;

    @Autowired
    ActivitiDeploymentService activitiDeploymentService;

    @ApiOperation("字符串部署流程")
    @PostMapping("/deploy/str")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true),
            @ApiImplicitParam(name = "xmlText", value = "xml文件字符串", required = true),
            @ApiImplicitParam(name = "workflowName", value = "流程名称", required = true),
            @ApiImplicitParam(name = "type", value = "1报销申请，2出差申请，3借款申请", required = true)
    })
    public ResultDto depolyWorkflowByStr(Long enterpriseId, String xmlText, String type , String workflowName) {

        String workflowKey = enterpriseId+"_"+type;

        Deployment deployment;
        try {
            deployment = designService.depolyWorkflowByStr(xmlText, workflowName, workflowKey,enterpriseId,type);
            //workflowFormService.updateActivitiForm(id,deployment.getId(),workflowKey,null,null,null);
        }
        catch (Exception e) {
            log.error("部署流程出错:", e);
            return ResultDto.failure(ResultCode.FAILURE, "部署失败:"+e.getMessage());
        }
        return ResultDto.success(deployment);
    }

    /**
     * @Description: 根据部署id 获取流程文件
     * @Return void
     * @Author:GC
     * @Date: 2020/10/27 17:35
     */
    @ApiOperation(value = "通过部署id查询流程xml,用企业ID_type")
    @GetMapping(value = "/getDefinitionXMLDeployId/{enterpriseId}/{type}")
    public ResultDto getProcessDefineXMLByDeployId(@PathVariable("enterpriseId") Long enterpriseId , @PathVariable("type") String type) {

        String deployId = activitiDeploymentService.findByEnterpriseIdAndType(enterpriseId, type);

        String xml = null;
        try {
            InputStream inputStream = designService.getProcessDefXmlByDeployId(deployId);
            if(inputStream!=null){
                int count = inputStream.available();
                byte[] bytes = new byte[count];
                int readByteCount = inputStream.read(bytes);
                xml = new String(bytes, StandardCharsets.UTF_8);
                if(count == readByteCount){
                    xml = new String(bytes, StandardCharsets.UTF_8);
                }
                log.info("读取了:{},{}",readByteCount,count);
                //response.setContentType("text/xml");
                //OutputStream outputStream = response.getOutputStream();
                //while (inputStream.read(bytes) != -1) {
                //    outputStream.write(bytes);
                //}
                inputStream.close();
            }
        } catch (Exception e) {
            log.error("获取流程部署文件xml报错:",e);
            return ResultDto.failure(ResultCode.FAILURE,"获取流程部署文件xml失败");
        }
        return ResultDto.success(xml);
    }


    @ApiOperation("查询流程用户配置")
    @ApiImplicitParam(name = "id", value = "用户任务环节id", required = true)
    @GetMapping(value = "/usertasks/{id}")
    public ResultDto getUsertaskSet(@PathVariable String id) {
        try {
            List<ActivitiStepUser> activitiStepUsers = designService.getActivitiStepUser(id);
            return ResultDto.success(activitiStepUsers);
        }
        catch (Exception e) {
            log.error("查询用户配置报错:", e);
            return ResultDto.failure(ResultCode.FAILURE, e.getMessage());
        }
    }

    @ApiOperation("新增配置用户")
    @PostMapping("/stepuser/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stepid", value = "用户流程配置id", required = true),
            @ApiImplicitParam(name = "auditUserDtos", value = "配置用户json", required = true)
    })
    public ResultDto insertUserSet(String stepid, String auditUserDtos) {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        designService.insertActivitiStepUser(stepid, userEntity.getId(), 0, auditUserDtos);
        return ResultDto.success();
    }


    @ApiOperation("删除配置用户")
    @DeleteMapping("/stepuser/del/{stepId}")
    public ResultDto delStepUser(@PathVariable(value = "stepId") String stepId) {
        designService.delStepUser(stepId);
        return ResultDto.success();
    }

}
