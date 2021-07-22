package bht.expense.business.enterprise.caller.strategy.detail.vehicle;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.vehicle.dto.StrategyVehicleEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:44
 */
@FeignClient(value = "expense-enterprise-server",fallback = StrategyVehicleCallerError.class, contextId = "StrategyVehicleCaller")
public interface StrategyVehicleCaller {

    @PostMapping("/strategy-vehicle/list")
    ResultDto list(@RequestParam("page") int page, @RequestParam("limit") int limit , @RequestParam("strategyId") Long strategyId);


    @GetMapping("/strategy-vehicle/{id}")
    ResultDto findById(@RequestParam("id") Long id);

    @PostMapping("/strategy-vehicle/insert")
    ResultDto insert(@RequestBody StrategyVehicleEntityDto strategyVehicleEntityDto);


    @DeleteMapping("/strategy-vehicle/del/{id}")
    ResultDto delete(@RequestParam("id") Long id);

    @DeleteMapping("/strategy-vehicle/dels/{id}")
    ResultDto deletes(@RequestParam("id") Long[] id);

    @PostMapping("/strategy-vehicle/update-status")
    ResultDto updateStatus(@RequestParam("id")Long id , @RequestParam("status")String status);
}
