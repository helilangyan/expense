package bht.expense.enterprise.strategy.detail.vehicle.controller;

import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.strategy.detail.vehicle.entity.StrategyVehicleEntity;
import bht.expense.enterprise.strategy.detail.vehicle.service.StrategyVehicleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/6/24 15:55
 */

@Api(tags = "费用策略--交通工具")
@RestController
@RequestMapping("strategy-vehicle")
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
    public ResultDto findByStrategyId(int page, int limit , Long strategyId)
    {
        Map<String,Object> map = new HashMap();
        map.put("strategyId",strategyId);

        PageListDto<StrategyVehicleEntity> pageListData = new PageListDto();
        PageInfo pageInfo = strategyVehicleService.findByStrategyId(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        StrategyVehicleEntity strategyVehicleEntity = strategyVehicleService.findById(id);
        return ResultDto.success(strategyVehicleEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody StrategyVehicleEntity strategyVehicleEntity) {
        strategyVehicleEntity = strategyVehicleService.insert(strategyVehicleEntity);
        return ResultDto.success(strategyVehicleEntity);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        strategyVehicleService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        strategyVehicleService.dels(id);
        return ResultDto.success();
    }

    @ApiOperation("修改状态， 0禁用  1启用，默认启用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true),
            @ApiImplicitParam(name = "status", value = "状态 0禁用  1启用", required = true)
    })
    @PostMapping("/update-status")
    public ResultDto updateStatus(Long id , String status)
    {
        strategyVehicleService.updateStatus(id, status);
        return ResultDto.success();
    }
}
