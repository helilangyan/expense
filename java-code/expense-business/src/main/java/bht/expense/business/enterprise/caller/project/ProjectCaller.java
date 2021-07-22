package bht.expense.business.enterprise.caller.project;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.project.dao.ProjectEntityDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/28 14:10
 */
@FeignClient(value = "expense-enterprise-server",fallback = ProjectCallerError.class, contextId = "ProjectCaller")
public interface ProjectCaller {

    @PostMapping("/project/findByEnterpriseId")
    ResultDto findByEnterpriseId(@RequestParam("page") int page, @RequestParam("limit") int limit , @RequestParam("enterpriseId")Long enterpriseId , @RequestParam("projectName")String projectName);


    @PostMapping("/project/insert")
    ResultDto insert(@RequestBody ProjectEntityDto projectEntityDto);


    @DeleteMapping("/project/delete/{id}")
    ResultDto delete(@RequestParam("id")  Long id);


    @DeleteMapping("/project/deletes/{id}")
    ResultDto deletes(@RequestParam("id") Long[] id);

    @GetMapping("/{id}")
    ResultDto findById(@PathVariable Long id);
}
