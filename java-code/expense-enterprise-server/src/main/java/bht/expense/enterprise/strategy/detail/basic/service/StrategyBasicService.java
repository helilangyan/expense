package bht.expense.enterprise.strategy.detail.basic.service;

import bht.expense.enterprise.strategy.detail.basic.dao.StrategyBasicMapper;
import bht.expense.enterprise.strategy.detail.basic.entity.StrategyBasicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 姚轶文
 * @date 2021/6/24 14:03
 */
@Service
public class StrategyBasicService {

    @Autowired
    StrategyBasicMapper strategyBasicMapper;

    public StrategyBasicEntity findByStrategyId(Long strategyId)
    {
        StrategyBasicEntity strategyBasicEntity = strategyBasicMapper.findByStrategyId(strategyId);

//        if(strategyBasicEntity==null)
//        {
//            strategyBasicEntity = new StrategyBasicEntity();
//            strategyBasicEntity.setStrategyId(strategyId);
//            strategyBasicEntity.setIsSubmit("1");
//            strategyBasicEntity.setCurrency("USD");
//            insert(strategyBasicEntity);
//        }
        return strategyBasicEntity;
    }



    @Transactional
    public StrategyBasicEntity update(StrategyBasicEntity strategyBasicEntity)
    {
        strategyBasicMapper.updateById(strategyBasicEntity);
        return strategyBasicEntity;
    }

    @Transactional
    public StrategyBasicEntity insert(StrategyBasicEntity strategyBasicEntity)
    {
        if(strategyBasicEntity.getId()!=null && strategyBasicEntity.getId()!=0)
        {
            strategyBasicMapper.updateById(strategyBasicEntity);
        }
        else {
            strategyBasicMapper.insert(strategyBasicEntity);
        }
        return strategyBasicEntity;
    }
}
