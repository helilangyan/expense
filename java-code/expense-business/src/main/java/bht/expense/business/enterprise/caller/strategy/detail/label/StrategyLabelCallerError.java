package bht.expense.business.enterprise.caller.strategy.detail.label;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.label.dto.StrategyLabelEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:30
 */
@Slf4j
public class StrategyLabelCallerError implements StrategyLabelCaller{
    @Override
    public ResultDto list(int page, int limit, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /strategy-label/list error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-enterprise-server: /strategy-label/findById error");
        return null;
    }

    @Override
    public ResultDto insert(StrategyLabelEntityDto strategyLabelEntityDto) {
        log.error("invoke expense-enterprise-server: /strategy-label/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /strategy-label/delete error");
        return null;
    }

    @Override
    public ResultDto deletes(Long[] id) {
        log.error("invoke expense-enterprise-server: /strategy-label/deletes error");
        return null;
    }

    @Override
    public ResultDto updateStatus(Long id, String status) {
        log.error("invoke expense-enterprise-server: /strategy-label/updateStatus error");
        return null;
    }
}
