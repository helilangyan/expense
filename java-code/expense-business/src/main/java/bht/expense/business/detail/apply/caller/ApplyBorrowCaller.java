package bht.expense.business.detail.apply.caller;

import bht.expense.business.common.ResultDto;

import bht.expense.business.detail.apply.dto.ApplyBorrowEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * @author 姚轶文
 * @date 2021/4/22 16:12
 */
@FeignClient(value = "expense-detail-server",fallback = ApplyBorrowCallerError.class, contextId = "ApplyBorrowCaller")
public interface ApplyBorrowCaller {

    @PostMapping("/apply-borrow/list")
    ResultDto findByUserIdAndEnterpriseId(@RequestParam("userId") Long userId, @RequestParam("enterpriseId") Long enterpriseId , @RequestParam("page") int page, @RequestParam("limit") int limit);


    @PostMapping("/apply-borrow/insert")
    ResultDto insert(@RequestBody ApplyBorrowEntityDto applyBorrowEntity);


    @DeleteMapping("/apply-borrow/delete/{id}")
    ResultDto delete(@PathVariable Long id);



    @GetMapping("/apply-borrow/{id}")
    ResultDto findById(@PathVariable Long id);

    @PostMapping("/apply-borrow/start")
    ResultDto start(@RequestBody ApplyBorrowEntityDto applyBorrowEntity, @RequestParam("nextUserId")String nextUserId ,  @RequestParam("copyUserIds") String[] copyUserIds);

}
