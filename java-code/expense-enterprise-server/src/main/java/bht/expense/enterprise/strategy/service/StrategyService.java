package bht.expense.enterprise.strategy.service;

import bht.expense.enterprise.strategy.dao.StrategyMapper;
import bht.expense.enterprise.strategy.detail.label.entity.StrategyLabelEntity;
import bht.expense.enterprise.strategy.entity.StrategyEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/6/25 15:00
 */
@Service
public class StrategyService {

    @Autowired
    StrategyMapper strategyMapper;

    @Transactional
    public void del(Long id)
    {
        strategyMapper.del(id);
    }

    @Transactional
    public void dels(Long[] id)
    {
        strategyMapper.dels(id);
    }

    public PageInfo findByEnterpriseId(Map<String, Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<StrategyEntity> list = strategyMapper.findByEnterpriseId(map);
        PageInfo<StrategyEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public StrategyEntity insert(StrategyEntity strategyEntity)
    {
        if(strategyEntity.getId()==null || strategyEntity.getId()==0)
        {
            strategyMapper.insert(strategyEntity);
        }
        else {
            strategyMapper.updateById(strategyEntity);
        }
        return strategyEntity;
    }

    public StrategyEntity findById(Long id)
    {
        return strategyMapper.selectById(id);
    }
}
