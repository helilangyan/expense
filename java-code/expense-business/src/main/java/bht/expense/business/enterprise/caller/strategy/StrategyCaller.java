package bht.expense.business.enterprise.caller.strategy;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.dto.StrategyEntityDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/6/25 15:06
 */
@FeignClient(value = "expense-enterprise-server",fallback = StrategyCallerError.class, contextId = "StrategyCaller")
public interface StrategyCaller {

    @PostMapping("/strategy/list")
    ResultDto list(@RequestParam("page") int page, @RequestParam("limit") int limit , @RequestParam("enterpriseId") Long enterpriseId);


    @GetMapping("/strategy/{id}")
    ResultDto findById(@RequestParam("id") Long id);

    @PostMapping("/strategy/insert")
    ResultDto insert(@RequestBody StrategyEntityDto strategyEntityDto);


    @DeleteMapping("/strategy/del/{id}")
    ResultDto delete(@RequestParam("id") Long id);

    @DeleteMapping("/strategy/dels/{id}")
    ResultDto deletes(@RequestParam("id") Long[] id);

    @PostMapping("/strategy/user-classify")
    ResultDto findUserClassify(@RequestParam("userId")Long userId , @RequestParam("enterpriseId")Long enterpriseId);

    @PostMapping("/strategy/user-label")
    ResultDto findUserLabel(@RequestParam("userId")Long userId , @RequestParam("enterpriseId")Long enterpriseId);

    @PostMapping("/strategy/user-vehicle")
    ResultDto findUserVehicle(@RequestParam("userId")Long userId , @RequestParam("enterpriseId")Long enterpriseId);


}
