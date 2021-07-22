package bht.expense.business.enterprise.caller.strategy.detail.basic;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.basic.dto.StrategyBasicEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:20
 */
@FeignClient(value = "expense-enterprise-server",fallback = StrategyBasicCallerError.class, contextId = "StrategyBasicCaller")
public interface StrategyBasicCaller {

    @GetMapping("/strategy-basic/{strategyId}")
    ResultDto findByStrategyId(@RequestParam("strategyId") Long strategyId);

    @PostMapping("/strategy-basic/insert")
    ResultDto insert(@RequestBody StrategyBasicEntityDto strategyBasicEntity);
}
