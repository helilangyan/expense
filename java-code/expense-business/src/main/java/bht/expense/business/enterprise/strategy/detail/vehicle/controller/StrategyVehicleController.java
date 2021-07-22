package bht.expense.business.enterprise.strategy.detail.vehicle.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.vehicle.dto.StrategyVehicleEntityDto;
import bht.expense.business.enterprise.strategy.detail.vehicle.service.StrategyVehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:49
 */
@Api(tags = "费用策略--交通工具")
@RestController
@RequestMapping("enterprise/strategy-vehicle")
public class StrategyVehicleController {

    @Autowired
    StrategyVehicleService strategyVehicleService;


    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "strategyId", value = "策略ID", required = true)
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit , Long strategyId)
    {
        return strategyVehicleService.list(page, limit, strategyId);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return strategyVehicleService.findById(id);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody StrategyVehicleEntityDto strategyVehicleEntityDto) {
        return strategyVehicleService.insert(strategyVehicleEntityDto);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        return strategyVehicleService.delete(id);
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        return strategyVehicleService.deletes(id);
    }

    @ApiOperation("修改状态， 0禁用  1启用，默认启用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true),
            @ApiImplicitParam(name = "status", value = "状态 0禁用  1启用", required = true)
    })
    @PostMapping("/update-status")
    public ResultDto updateStatus(Long id , String status)
    {
        return strategyVehicleService.updateStatus(id, status);
    }
}
