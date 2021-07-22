package bht.expense.business.detail.approval.caller;

import bht.expense.business.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/6/17 14:00
 */
@Slf4j
public class ApprovalCallerError implements ApprovalCaller{
    @Override
    public ResultDto list(String key, String userId, int firstResult, int maxResults) {
        log.error("invoke expense-detail-server: /approval-task/list error");
        return null;
    }
}
