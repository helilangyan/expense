package bht.expense.business.enterprise.strategy.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.strategy.StrategyCaller;
import bht.expense.business.enterprise.strategy.dto.StrategyEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 姚轶文
 * @date 2021/6/25 15:11
 */
@Service
public class StrategyService {

    @Autowired
    StrategyCaller strategyCaller;

    public ResultDto list(int page, int limit , Long enterpriseId)
    {
        return strategyCaller.list(page, limit, enterpriseId);
    }


    public ResultDto findById( Long id)
    {
        return strategyCaller.findById(id);
    }

    public ResultDto insert( StrategyEntityDto strategyEntityDto)
    {
        return strategyCaller.insert(strategyEntityDto);
    }


    public ResultDto delete(Long id)
    {
        return strategyCaller.delete(id);
    }

    public ResultDto deletes(Long[] id)
    {
        return strategyCaller.deletes(id);
    }

    public ResultDto findUserClassify(Long userId , Long enterpriseId)
    {
        return strategyCaller.findUserClassify(userId, enterpriseId);
    }

    public ResultDto findUserLabel(Long userId , Long enterpriseId)
    {
        return strategyCaller.findUserLabel(userId, enterpriseId);
    }

    public ResultDto findUserVehicle(Long userId , Long enterpriseId)
    {
        return strategyCaller.findUserVehicle(userId, enterpriseId);
    }
}
