package bht.expense.business.detail.apply.caller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.dto.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/22 16:13
 */
@FeignClient(value = "expense-detail-server",fallback = ApplyTravelCallerError.class, contextId = "ApplyTravelCaller")
public interface ApplyTravelCaller {

    @PostMapping("/apply-travel/list")
    ResultDto findByUserIdAndEnterpriseId(@RequestParam("userId") Long userId, @RequestParam("enterpriseId") Long enterpriseId , @RequestParam("page") int page, @RequestParam("limit") int limit);


    @PostMapping("/apply-travel/insert")
    ResultDto insert(@RequestBody ApplyTravelInsertDto applyTravelInsertDto);


    @DeleteMapping("/apply-travel/delete/{id}")
    ResultDto delete(@PathVariable Long id);


    @GetMapping("/apply-travel/{id}")
    ResultDto findById(@PathVariable Long id);

    @PostMapping("/apply-travel/start")
    ResultDto start(@RequestBody ApplyTravelInsertDto applyTravelInsertDto, @RequestParam("nextUserId")String nextUserId , @RequestParam("copyUserIds") String[] copyUserIds);


}
