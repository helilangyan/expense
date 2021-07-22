package bht.expense.business.enterprise.caller.strategy.detail.classify;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.classify.dto.StrategyClassifyEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/7/14 11:13
 */
@Slf4j
public class StrategyClassifyCallerError implements StrategyClassifyCaller{

    @Override
    public ResultDto list(int page, int limit, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /strategy-classify/list error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-enterprise-server: /strategy-classify/findById error");
        return null;
    }

    @Override
    public ResultDto insert(StrategyClassifyEntityDto strategyClassifyEntityDto) {
        log.error("invoke expense-enterprise-server: /strategy-classify/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /strategy-classify/delete error");
        return null;
    }

    @Override
    public ResultDto deletes(Long[] id) {
        log.error("invoke expense-enterprise-server: /strategy-classify/deletes error");
        return null;
    }

    @Override
    public ResultDto updateStatus(Long id, String status) {
        log.error("invoke expense-enterprise-server: /strategy-classify/updateStatus error");
        return null;
    }
}
