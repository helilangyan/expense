package bht.expense.business.detail.apply.caller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.dto.ApplyBorrowEntityDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/22 16:14
 */
@Slf4j
public class ApplyBorrowCallerError implements ApplyBorrowCaller{
    @Override
    public ResultDto findByUserIdAndEnterpriseId(Long userId, Long enterpriseId, int page, int limit) {
        log.error("invoke expense-detail-server: /apply-borrow/list error");
        return null;
    }

    @Override
    public ResultDto insert(ApplyBorrowEntityDto applyBorrowEntity) {
        log.error("invoke expense-detail-server: /apply-borrow/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-detail-server: /apply-borrow/delete error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-detail-server: /apply-borrow/findById error");
        return null;
    }

    @Override
    public ResultDto start(ApplyBorrowEntityDto applyBorrowEntity, String nextUserId, String[] copyUserIds) {
        log.error("invoke expense-detail-server: /apply-borrow/start error");
        return null;
    }
}
