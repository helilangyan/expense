package bht.expense.detail.apply.caller;

import bht.expense.detail.approval.dto.TaskListDto;
import bht.expense.detail.common.ResultDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/6/10 14:59
 */
@FeignClient(value = "expense-bpm-server",fallback = BpmUseCallerError.class, contextId = "BpmUseCaller")
public interface BpmUseCaller {

    @PostMapping("workflow/use/start")
    ResultDto startBill(@RequestParam("billId")String billId, @RequestParam("key")String key, @RequestParam("title") String title, @RequestParam("nextUserid")String nextUserid);

    @PostMapping("workflow/use/my-tasks")
    TaskListDto myTasks(@RequestParam("key") String key , @RequestParam("userId") String userId, @RequestParam("page") int page, @RequestParam("limit") int limit);
}
