package bht.expense.business.detail.apply.caller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.dto.ApplyBorrowEntityDto;
import bht.expense.business.detail.apply.dto.ApplyExpenseEntityDto;

import bht.expense.business.detail.apply.dto.ApplyExpenseInsertDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;



/**
 * @author 姚轶文
 * @date 2021/4/22 16:13
 */
@FeignClient(value = "expense-detail-server",fallback = ApplyExpenseCallerError.class, contextId = "ApplyExpenseCaller")
public interface ApplyExpenseCaller {


    @PostMapping("/apply-expense/list")
    ResultDto findByUserIdAndEnterpriseId(@RequestParam("userId") Long userId, @RequestParam("enterpriseId") Long enterpriseId , @RequestParam("page") int page, @RequestParam("limit") int limit);


    @PostMapping("/apply-expense/insert")
    ResultDto insert(@RequestBody ApplyExpenseInsertDto applyExpenseInsertDto);


    @DeleteMapping("/apply-expense/delete/{id}")
    ResultDto delete(@PathVariable Long id);


    @GetMapping("/apply-expense/{id}")
    ResultDto findById(@PathVariable Long id);

    @PostMapping("/apply-expense/start")
    ResultDto start(@RequestBody ApplyExpenseInsertDto applyExpenseInsertDto, @RequestParam("nextUserId")String nextUserId , @RequestParam("copyUserIds") String[] copyUserIds);


}
