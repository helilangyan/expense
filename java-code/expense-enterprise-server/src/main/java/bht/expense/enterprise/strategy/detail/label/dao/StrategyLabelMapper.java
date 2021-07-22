package bht.expense.enterprise.strategy.detail.label.dao;


import bht.expense.enterprise.strategy.detail.label.entity.StrategyLabelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/6/24 15:11
 */
public interface StrategyLabelMapper extends BaseMapper<StrategyLabelEntity> {

    void del(Long id);

    void dels(Long[] id);

    List<StrategyLabelEntity> findByStrategyId(Map<String, Object> map );

    void updateStatus(Long id , String status);
}
