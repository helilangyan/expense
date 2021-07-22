package bht.expense.business.detail.business.caller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.business.dto.ExpenseDetailEntityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/21 9:46
 */
@Slf4j
public class BusinessExpenseCallerError implements BusinessExpenseCaller{

    @Override
    public ResultDto findByUserIdAndEnterpriseId(String expenseName,String expenseType, String tags, String startTime, String endTime, Long userId, Long enterpriseId, int page, int limit) {
        log.error("invoke expense-detail-server: /expense-detail/list error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-detail-server: /expense-detail/delete error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-detail-server: /expense-detail/findById error");
        return null;
    }

    @Override
    public ResultDto insert(ExpenseDetailEntityDto expenseDetailEntityDto) {
        log.error("invoke expense-detail-server: /expense-detail/insert error");
        return null;
    }

    @Override
    public ResultDto checkInsert(ExpenseDetailEntityDto expenseDetailEntityDto) {
        log.error("invoke expense-detail-server: /expense-detail/checkInsert error");
        return null;
    }
}
