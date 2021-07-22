package bht.expense.enterprise.department.controller;

import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.department.entity.DepartmentEntity;
import bht.expense.enterprise.department.service.DepartmentService;
import bht.expense.enterprise.strategy.entity.StrategyUserEntity;
import bht.expense.enterprise.strategy.service.StrategyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/15 15:46
 */
@Api(tags = "部门设置")
@RestController
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    StrategyUserService strategyUserService;

    @ApiOperation("本企业全部部门")
    @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    @PostMapping("/findByEnterpriseId")
    public ResultDto findByEnterpriseId(Long enterpriseId)
    {
        return ResultDto.success(departmentService.findByEnterpriseId(enterpriseId));
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        departmentService.delete(id);
        return ResultDto.success();
    }

    @ApiOperation("新增")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody DepartmentEntity departmentEntity)
    {
        departmentService.insert(departmentEntity);
        return ResultDto.success();
    }

    @ApiOperation("修改部门名称")
    @PostMapping("/updateDepartmentName")
    public ResultDto updateDepartmentName(String departmentName , Long id)
    {
        departmentService.updateDepartmentName(departmentName, id);
        return ResultDto.success();
    }

    @ApiOperation("根据部门ID，查看该部门下员工")
    @PostMapping("/findUsers")
    public ResultDto findUsers(Long enterpriseId,Long departmentId)
    {
        return ResultDto.success(departmentService.findUsers(enterpriseId,departmentId));
    }

    @ApiOperation("员工与策略绑定")
    @PostMapping("/insert-strategy")
    public ResultDto inputStrategy(@RequestBody StrategyUserEntity strategyUserEntity)
    {
        strategyUserService.insert(strategyUserEntity);
        return ResultDto.success();
    }
}
