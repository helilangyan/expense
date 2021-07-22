package bht.expense.business.enterprise.caller.strategy.detail.basic;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.basic.dto.StrategyBasicEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:20
 */
@Slf4j
public class StrategyBasicCallerError implements StrategyBasicCaller{
    @Override
    public ResultDto findByStrategyId(Long strategyId) {
        log.error("invoke expense-enterprise-server: /strategy-basic/findByEnterpriseId error");
        return null;
    }

    @Override
    public ResultDto insert(StrategyBasicEntityDto strategyBasicEntity) {
        log.error("invoke expense-enterprise-server: /strategy-basic/update error");
        return null;
    }
}
