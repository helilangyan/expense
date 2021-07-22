package bht.expense.business.bpm.use.caller;

import bht.expense.business.bpm.use.dto.TaskListDto;
import bht.expense.business.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/6/10 15:00
 */
@Slf4j
public class BpmUseCallerError implements BpmUseCaller{
    @Override
    public ResultDto getTaskAuditors(String businessKey, String key, String taskId) {
        log.error("invoke expense-bpm-server: /workflow/use/stepuser/list error");
        return null;
    }

    @Override
    public TaskListDto myTasks(String key, String userId, int page, int limit) {
        log.error("invoke expense-bpm-server: /workflow/use/myTasks error");
        return null;
    }

    @Override
    public ResultDto approveBill(String billId, String approve, String comment, String nextUserId, String taskId) {
        log.error("invoke expense-bpm-server: workflow/use/approve error");
        return null;
    }

    @Override
    public ResultDto getTaskHistoriesPic(String businessKey, String userId) {
        log.error("invoke expense-bpm-server: workflow/use/history/pic error");
        return null;
    }

    @Override
    public ResultDto myTaskDone(String userId, String startUserName, String type, String status, int page, int limit) {
        log.error("invoke expense-bpm-server: workflow/use/myTaskDone error");
        return null;
    }

    @Override
    public ResultDto getTaskHistoriesRecord(String businessId) {

        log.error("invoke expense-bpm-server: workflow/use/history/records error");
        return null;
    }


}
