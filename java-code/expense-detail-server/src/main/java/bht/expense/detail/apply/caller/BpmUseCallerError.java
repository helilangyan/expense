package bht.expense.detail.apply.caller;

import bht.expense.detail.approval.dto.TaskListDto;
import bht.expense.detail.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/6/10 15:00
 */
@Slf4j
public class BpmUseCallerError implements BpmUseCaller{

    @Override
    public ResultDto startBill(String billId, String key, String title, String nextUserid) {
        log.error("invoke expense-bpm-server: /workflow/use/start error");
        return null;
    }

    @Override
    public TaskListDto myTasks(String key, String userId, int page, int limit) {
        log.error("invoke expense-bpm-server: /workflow/use/myTasks error");
        return null;
    }
}
