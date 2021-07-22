package bht.expense.enterprise.strategy.detail.basic.dao;

import bht.expense.enterprise.strategy.detail.basic.entity.StrategyBasicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 姚轶文
 * @date 2021/6/24 14:00
 */
public interface StrategyBasicMapper extends BaseMapper<StrategyBasicEntity> {

    StrategyBasicEntity findByStrategyId(Long strategyId);
}
