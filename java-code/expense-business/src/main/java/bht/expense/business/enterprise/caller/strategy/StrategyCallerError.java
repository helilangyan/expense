package bht.expense.business.enterprise.caller.strategy;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.dto.StrategyEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/6/25 15:07
 */
@Slf4j
public class StrategyCallerError implements StrategyCaller{
    @Override
    public ResultDto list(int page, int limit, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /strategy/list error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-enterprise-server: /strategy/findById error");
        return null;
    }

    @Override
    public ResultDto insert(StrategyEntityDto strategyEntityDto) {
        log.error("invoke expense-enterprise-server: /strategy/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /strategy/delete error");
        return null;
    }

    @Override
    public ResultDto deletes(Long[] id) {
        log.error("invoke expense-enterprise-server: /strategy/deletes error");
        return null;
    }

    @Override
    public ResultDto findUserClassify(Long userId, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /strategy/findUserClassify error");
        return null;
    }

    @Override
    public ResultDto findUserLabel(Long userId, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /strategy/findUserLabel error");
        return null;
    }

    @Override
    public ResultDto findUserVehicle(Long userId, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /strategy/findUserVehicle error");
        return null;
    }
}
