package bht.expense.business.enterprise.department.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.department.dto.DepartmentEntityDto;
import bht.expense.business.enterprise.department.dto.StrategyUserEntityDto;
import bht.expense.business.enterprise.department.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/15 16:05
 */
@Api(tags = "部门设置")
@RestController
@RequestMapping("enterprise/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @ApiOperation("本企业全部部门")
    @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    @PostMapping("/findByEnterpriseId")
    public ResultDto findByEnterpriseId(Long enterpriseId)
    {
        return departmentService.findByEnterpriseId(enterpriseId);
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        return departmentService.delete(id);
    }

    @ApiOperation("新增")
    @PostMapping("/insert")
    public ResultDto insert(DepartmentEntityDto departmentEntityDto)
    {
        return departmentService.insert(departmentEntityDto);
    }

    @ApiOperation("修改部门名称")
    @PostMapping("/updateDepartmentName")
    public ResultDto updateDepartmentName(String departmentName , Long id)
    {
        return departmentService.updateDepartmentName(departmentName, id);
    }

    @ApiOperation("根据部门ID，查看该部门下员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true),
            @ApiImplicitParam(name = "departmentId", value = "部门ID")
    })
    @PostMapping("/findUsers")
    public ResultDto findUsers(Long enterpriseId , Long departmentId)
    {
        return departmentService.findUsers(enterpriseId ,departmentId);
    }

    @ApiOperation("员工与策略绑定")
    @PostMapping("/insert-strategy")
    public ResultDto inputStrategy(@RequestBody StrategyUserEntityDto strategyUserEntity)
    {
        return departmentService.inputStrategy(strategyUserEntity);
    }
}
