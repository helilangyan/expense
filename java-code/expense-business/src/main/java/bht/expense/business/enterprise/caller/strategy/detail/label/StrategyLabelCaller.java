package bht.expense.business.enterprise.caller.strategy.detail.label;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.label.dto.StrategyLabelEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:30
 */
@FeignClient(value = "expense-enterprise-server",fallback = StrategyLabelCallerError.class, contextId = "StrategyLabelCaller")
public interface StrategyLabelCaller {

    @PostMapping("/strategy-label/list")
    ResultDto list(@RequestParam("page") int page, @RequestParam("limit") int limit , @RequestParam("strategyId") Long strategyId);


    @GetMapping("/strategy-label/{id}")
    ResultDto findById(@RequestParam("id") Long id);

    @PostMapping("/strategy-label/insert")
    ResultDto insert(@RequestBody StrategyLabelEntityDto strategyLabelEntityDto);


    @DeleteMapping("/strategy-label/del/{id}")
    ResultDto delete(@RequestParam("id") Long id);

    @DeleteMapping("/strategy-label/dels/{id}")
    ResultDto deletes(@RequestParam("id") Long[] id);

    @PostMapping("/strategy-label/update-status")
    ResultDto updateStatus(@RequestParam("id")Long id , @RequestParam("status")String status);

}
