package bht.expense.business.enterprise.strategy.detail.basic.service;


import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.strategy.detail.basic.StrategyBasicCaller;
import bht.expense.business.enterprise.strategy.detail.basic.dto.StrategyBasicEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/6/24 14:03
 */
@Service
public class StrategyBasicService {

    @Autowired
    StrategyBasicCaller strategyBasicCaller;

    public ResultDto findByStrategyId(Long strategyId)
    {
        return strategyBasicCaller.findByStrategyId(strategyId);
    }

    public ResultDto insert(StrategyBasicEntityDto strategyBasicEntity)
    {
        return strategyBasicCaller.insert(strategyBasicEntity);
    }


}
