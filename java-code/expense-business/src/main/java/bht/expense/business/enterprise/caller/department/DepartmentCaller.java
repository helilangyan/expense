package bht.expense.business.enterprise.caller.department;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.department.dto.DepartmentEntityDto;
import bht.expense.business.enterprise.department.dto.StrategyUserEntityDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/15 15:56
 */
@FeignClient(value = "expense-enterprise-server",fallback = DepartmentCallerError.class, contextId = "DepartmentCaller")
public interface DepartmentCaller {

    @PostMapping("/department/findByEnterpriseId")
    ResultDto findByEnterpriseId(@RequestParam("enterpriseId") Long enterpriseId);


    @DeleteMapping("/department/delete/{id}")
    ResultDto delete(@RequestParam("id") Long id);


    @PostMapping("/department/insert")
    ResultDto insert(@RequestBody DepartmentEntityDto departmentEntityDto);


    @PostMapping("/department/updateDepartmentName")
    ResultDto updateDepartmentName(@RequestParam("departmentName") String departmentName , @RequestParam("id") Long id);

    @PostMapping("/department/findUsers")
    ResultDto findUsers(@RequestParam("enterpriseId")Long enterpriseId, @RequestParam("departmentId") Long departmentId);


    @PostMapping("/department/insert-strategy")
    ResultDto inputStrategy(@RequestBody StrategyUserEntityDto strategyUserEntity);

}
