package bht.expense.business.enterprise.strategy.detail.vehicle.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.strategy.detail.vehicle.StrategyVehicleCaller;
import bht.expense.business.enterprise.strategy.detail.vehicle.dto.StrategyVehicleEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:48
 */
@Service
public class StrategyVehicleService {

    @Autowired
    StrategyVehicleCaller strategyVehicleCaller;

    public ResultDto list(int page, int limit , Long enterpriseId)
    {
        return strategyVehicleCaller.list(page, limit, enterpriseId);
    }


    public ResultDto findById( Long id)
    {
        return strategyVehicleCaller.findById(id);
    }

    public ResultDto insert( StrategyVehicleEntityDto strategyVehicleEntityDto)
    {
        return strategyVehicleCaller.insert(strategyVehicleEntityDto);
    }


    public ResultDto delete(Long id)
    {
        return strategyVehicleCaller.delete(id);
    }

    public ResultDto deletes(Long[] id)
    {
        return strategyVehicleCaller.deletes(id);
    }

    public ResultDto updateStatus(Long id , String status)
    {
        return strategyVehicleCaller.updateStatus(id, status);
    }
}
