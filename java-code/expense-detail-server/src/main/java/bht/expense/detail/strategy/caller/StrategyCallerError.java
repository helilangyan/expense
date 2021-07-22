package bht.expense.detail.strategy.caller;


import bht.expense.detail.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/6/25 15:07
 */
@Slf4j
public class StrategyCallerError implements StrategyCaller{

    @Override
    public ResultDto findByUserIdAndEnterpriseIdToBasic(Long userId, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /strategy/findByUserIdAndEnterpriseIdToBasic error");
        return null;
    }

    @Override
    public ResultDto classifyFindById(Long id) {
        log.error("invoke expense-enterprise-server: /strategy-classify/findById error");
        return null;
    }
}
