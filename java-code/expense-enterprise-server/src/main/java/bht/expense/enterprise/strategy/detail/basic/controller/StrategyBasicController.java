package bht.expense.enterprise.strategy.detail.basic.controller;

import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.strategy.detail.basic.entity.StrategyBasicEntity;
import bht.expense.enterprise.strategy.detail.basic.service.StrategyBasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/6/24 14:23
 */
@Api(tags = "费用策略--基本设置")
@RestController
@RequestMapping("strategy-basic")
public class StrategyBasicController {

    @Autowired
    StrategyBasicService strategyBasicService;

    @ApiOperation("根据策略ID查询")
    @ApiImplicitParam(name = "strategyId", value = "strategyId", required = true)
    @GetMapping("/{strategyId}")
    public ResultDto findByStrategyId(@PathVariable Long strategyId)
    {
        StrategyBasicEntity strategyBasicEntity = strategyBasicService.findByStrategyId(strategyId);
        return ResultDto.success(strategyBasicEntity);
    }

    @ApiOperation("修改")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody StrategyBasicEntity strategyBasicEntity) {
        return ResultDto.success(strategyBasicService.insert(strategyBasicEntity));
    }
}
