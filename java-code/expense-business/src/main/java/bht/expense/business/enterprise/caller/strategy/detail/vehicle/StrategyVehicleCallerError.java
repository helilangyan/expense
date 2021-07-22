package bht.expense.business.enterprise.caller.strategy.detail.vehicle;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.vehicle.dto.StrategyVehicleEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:44
 */
@Slf4j
public class StrategyVehicleCallerError implements  StrategyVehicleCaller{
    @Override
    public ResultDto list(int page, int limit, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /strategy-vehicle/list error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-enterprise-server: /strategy-vehicle/findById error");
        return null;
    }

    @Override
    public ResultDto insert(StrategyVehicleEntityDto strategyVehicleEntityDto) {
        log.error("invoke expense-enterprise-server: /strategy-vehicle/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /strategy-vehicle/delete error");
        return null;
    }

    @Override
    public ResultDto deletes(Long[] id) {
        log.error("invoke expense-enterprise-server: /strategy-vehicle/deletes error");
        return null;
    }

    @Override
    public ResultDto updateStatus(Long id, String status) {
        log.error("invoke expense-enterprise-server: /strategy-vehicle/updateStatus error");
        return null;
    }
}
