package bht.expense.enterprise.strategy.dao;

import bht.expense.enterprise.strategy.detail.label.entity.StrategyLabelEntity;
import bht.expense.enterprise.strategy.entity.StrategyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/6/25 14:58
 */
public interface StrategyMapper extends BaseMapper<StrategyEntity> {

    void del(Long id);

    void dels(Long[] id);

    List<StrategyEntity> findByEnterpriseId(Map<String, Object> map );
}
