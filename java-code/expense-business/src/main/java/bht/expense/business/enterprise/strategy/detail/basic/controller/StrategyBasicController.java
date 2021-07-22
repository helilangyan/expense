package bht.expense.business.enterprise.strategy.detail.basic.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.basic.dto.StrategyBasicEntityDto;
import bht.expense.business.enterprise.strategy.detail.basic.service.StrategyBasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:28
 */
@Api(tags = "费用策略--基本设置")
@RestController
@RequestMapping("enterprise/strategy-basic")
public class StrategyBasicController {

    @Autowired
    StrategyBasicService strategyBasicService;

    @ApiOperation("根据策略ID查询")
    @ApiImplicitParam(name = "strategyId", value = "strategyId", required = true)
    @GetMapping("/{strategyId}")
    public ResultDto findByEnterpriseId(@PathVariable Long strategyId)
    {
        return ResultDto.success(strategyBasicService.findByStrategyId(strategyId));
    }

    @ApiOperation("插入，根据有无ID判断新增或修改")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody StrategyBasicEntityDto strategyBasicEntity) {
        return ResultDto.success(strategyBasicService.insert(strategyBasicEntity));
    }
}
