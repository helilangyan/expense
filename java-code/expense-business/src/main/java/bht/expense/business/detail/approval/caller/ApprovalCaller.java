package bht.expense.business.detail.approval.caller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.caller.ApplyBorrowCallerError;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/6/17 14:00
 */
@FeignClient(value = "expense-detail-server",fallback = ApplyBorrowCallerError.class, contextId = "ApprovalCaller")
public interface ApprovalCaller {


    @PostMapping("/approval-task/list")
    ResultDto list(@RequestParam("key")String key , @RequestParam("userId")String userId, @RequestParam("firstResult")int firstResult, @RequestParam("maxResults")int maxResults);
}
