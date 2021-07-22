package bht.expense.business.detail.business.caller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.business.dto.ExpenseDetailEntityDto;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * @author 姚轶文
 * @date 2021/4/21 9:45
 */
@FeignClient(value = "expense-detail-server",fallback = BusinessExpenseCallerError.class, contextId = "BusinessExpenseCaller")
public interface BusinessExpenseCaller {


    @PostMapping("/expense-detail/list")
    ResultDto findByUserIdAndEnterpriseId(@RequestParam("expenseName")String expenseName , @RequestParam("expenseType")String expenseType , @RequestParam("tags")String tags , @RequestParam("startTime")String startTime , @RequestParam("endTime")String endTime, @RequestParam("userId") Long userId, @RequestParam("enterpriseId") Long enterpriseId , @RequestParam("page") int page, @RequestParam("limit") int limit);



    @DeleteMapping("/expense-detail/delete/{id}")
    ResultDto delete(@RequestParam("id") Long id);



    @GetMapping("/expense-detail/{id}")
    ResultDto findById(@RequestParam("id") Long id);


    @PostMapping("/expense-detail/insert")
    ResultDto insert(@RequestBody ExpenseDetailEntityDto expenseDetailEntityDto);


    @PostMapping("/expense-detail/check-insert")
    ResultDto checkInsert(@RequestBody ExpenseDetailEntityDto expenseDetailEntityDto);

}
