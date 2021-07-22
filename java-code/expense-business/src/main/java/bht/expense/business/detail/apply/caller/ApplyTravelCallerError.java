package bht.expense.business.detail.apply.caller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.dto.ApplyTravelDetailEntityDto;
import bht.expense.business.detail.apply.dto.ApplyTravelEntityDto;
import bht.expense.business.detail.apply.dto.ApplyTravelInsertDto;
import bht.expense.business.detail.apply.dto.ApplyTravelUserEntityDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/22 16:14
 */
@Slf4j
public class ApplyTravelCallerError implements ApplyTravelCaller{
    @Override
    public ResultDto findByUserIdAndEnterpriseId(Long userId, Long enterpriseId, int page, int limit) {
        log.error("invoke expense-detail-server: /apply-travel/list error");
        return null;
    }

    @Override
    public ResultDto insert(ApplyTravelInsertDto applyTravelInsertDto) {
        log.error("invoke expense-detail-server: /apply-travel/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-detail-server: /apply-travel/delete error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-detail-server: /apply-travel/findById error");
        return null;
    }

    @Override
    public ResultDto start(ApplyTravelInsertDto applyTravelInsertDto, String nextUserId, String[] copyUserIds) {
        log.error("invoke expense-detail-server: /apply-travel/start error");
        return null;
    }
}
