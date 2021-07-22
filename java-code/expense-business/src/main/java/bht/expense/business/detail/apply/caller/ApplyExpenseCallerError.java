package bht.expense.business.detail.apply.caller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.dto.ApplyExpenseEntityDto;
import bht.expense.business.detail.apply.dto.ApplyExpenseInsertDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/22 16:14
 */
@Slf4j
public class ApplyExpenseCallerError implements ApplyExpenseCaller{
    @Override
    public ResultDto findByUserIdAndEnterpriseId(Long userId, Long enterpriseId, int page, int limit) {
        log.error("invoke expense-detail-server: /apply-expense/list error");
        return null;
    }

    @Override
    public ResultDto insert(ApplyExpenseInsertDto applyExpenseInsertDto) {
        log.error("invoke expense-detail-server: /apply-expense/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-detail-server: /apply-expense/delete error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-detail-server: /apply-expense/findById error");
        return null;
    }

    @Override
    public ResultDto start(ApplyExpenseInsertDto applyExpenseInsertDto, String nextUserId, String[] copyUserIds) {
        log.error("invoke expense-detail-server: /apply-expense/start error");
        return null;
    }
}
