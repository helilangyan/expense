package bht.expense.detail.strategy.caller;

import bht.expense.detail.common.ResultDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/7/22 10:37
 */
@FeignClient(value = "expense-enterprise-server",fallback = StrategyCallerError.class, contextId = "StrategyCaller")
public interface StrategyCaller {

    @PostMapping("/strategy/user-basic")
    ResultDto findByUserIdAndEnterpriseIdToBasic(@RequestParam("userId")Long userId , @RequestParam("enterpriseId")Long enterpriseId);

    @GetMapping("/strategy-classify/{id}")
    ResultDto classifyFindById(@RequestParam("id") Long id);

}
