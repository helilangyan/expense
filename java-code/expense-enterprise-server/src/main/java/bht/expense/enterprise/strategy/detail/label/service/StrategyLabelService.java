package bht.expense.enterprise.strategy.detail.label.service;


import bht.expense.enterprise.strategy.detail.label.dao.StrategyLabelMapper;
import bht.expense.enterprise.strategy.detail.label.entity.StrategyLabelEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/6/24 15:19
 */
@Service
public class StrategyLabelService {

    @Autowired
    StrategyLabelMapper strategyLabelMapper;

    @Transactional
    public void del(Long id)
    {
        strategyLabelMapper.del(id);
    }

    @Transactional
    public void dels(Long[] id)
    {
        strategyLabelMapper.dels(id);
    }

    public PageInfo findByStrategyId(Map<String, Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<StrategyLabelEntity> list = strategyLabelMapper.findByStrategyId(map);
        PageInfo<StrategyLabelEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public StrategyLabelEntity insert(StrategyLabelEntity strategyLabelEntity)
    {
        if(strategyLabelEntity.getId()==null || strategyLabelEntity.getId()==0)
        {
            strategyLabelMapper.insert(strategyLabelEntity);
        }
        else {
            strategyLabelMapper.updateById(strategyLabelEntity);
        }
        return strategyLabelEntity;
    }

    public StrategyLabelEntity findById(Long id)
    {
        return strategyLabelMapper.selectById(id);
    }

    @Transactional
    public void updateStatus(Long id , String status)
    {
        strategyLabelMapper.updateStatus(id, status);
    }
}
