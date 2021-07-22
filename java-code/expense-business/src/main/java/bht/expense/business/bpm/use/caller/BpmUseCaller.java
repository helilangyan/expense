package bht.expense.business.bpm.use.caller;

import bht.expense.business.bpm.use.dto.TaskListDto;
import bht.expense.business.common.ResultDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/6/10 14:59
 */
@FeignClient(value = "expense-bpm-server",fallback = BpmUseCallerError.class, contextId = "BpmUseCaller")
public interface BpmUseCaller {

    @PostMapping("workflow/use/stepuser/list")
    ResultDto getTaskAuditors(@RequestParam("businessKey")String businessKey, @RequestParam("key")String key, @RequestParam("taskId")String taskId);

    @PostMapping("workflow/use/my-tasks")
    TaskListDto myTasks(@RequestParam("key") String key , @RequestParam("userId") String userId, @RequestParam("page") int page, @RequestParam("limit") int limit);

    @PostMapping("workflow/use/approve")
    ResultDto approveBill(@RequestParam("billId")String billId, @RequestParam("approve")String approve, @RequestParam("comment")String comment, @RequestParam("nextUserId")String nextUserId,@RequestParam("taskId")String taskId);

    @PostMapping("workflow/use/history/pic")
    ResultDto getTaskHistoriesPic(@RequestParam("businessKey")String businessKey ,@RequestParam("userId")String userId);

    @PostMapping("workflow/use/my-tasks-done")
    ResultDto myTaskDone(@RequestParam("userId")String userId, @RequestParam("startUserName")String startUserName, @RequestParam("type")String type, @RequestParam("status")String status, @RequestParam("page")int page, @RequestParam("limit")int limit);

    @GetMapping("workflow/use/history/records/{businessId}")
    ResultDto getTaskHistoriesRecord(@PathVariable("businessId") String businessId);
}
