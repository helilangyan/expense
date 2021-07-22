package bht.expense.business.enterprise.caller.strategy.detail.classify;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.classify.dto.StrategyClassifyEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/7/14 11:13
 */
@FeignClient(value = "expense-enterprise-server",fallback = StrategyClassifyCallerError.class, contextId = "StrategyClassifyCaller")
public interface StrategyClassifyCaller {

    @PostMapping("/strategy-classify/list")
    ResultDto list(@RequestParam("page") int page, @RequestParam("limit") int limit , @RequestParam("strategyId") Long strategyId);


    @GetMapping("/strategy-classify/{id}")
    ResultDto findById(@RequestParam("id") Long id);

    @PostMapping("/strategy-classify/insert")
    ResultDto insert(@RequestBody StrategyClassifyEntityDto strategyClassifyEntityDto);


    @DeleteMapping("/strategy-classify/del/{id}")
    ResultDto delete(@RequestParam("id") Long id);

    @DeleteMapping("/strategy-classify/dels/{id}")
    ResultDto deletes(@RequestParam("id") Long[] id);

    @PostMapping("/strategy-classify/update-status")
    ResultDto updateStatus(@RequestParam("id")Long id , @RequestParam("classifyStatus")String classifyStatus);
}
