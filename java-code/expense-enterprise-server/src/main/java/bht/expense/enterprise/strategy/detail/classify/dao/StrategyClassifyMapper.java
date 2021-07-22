package bht.expense.enterprise.strategy.detail.classify.dao;

import bht.expense.enterprise.strategy.detail.classify.entity.StrategyClassifyEntity;
import bht.expense.enterprise.strategy.detail.label.entity.StrategyLabelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/7/14 10:45
 */
public interface StrategyClassifyMapper extends BaseMapper<StrategyClassifyEntity> {

    void del(Long id);

    void dels(Long[] id);

    List<StrategyClassifyEntity> findByStrategyId(Map<String, Object> map );

    void updateStatus(Long id , String classifyStatus);
}
