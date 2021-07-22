package bht.expense.enterprise.strategy.detail.classify.service;

import bht.expense.enterprise.strategy.detail.classify.dao.StrategyClassifyMapper;
import bht.expense.enterprise.strategy.detail.classify.entity.StrategyClassifyEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/7/14 10:47
 */
@Service
public class StrategyClassifyService {

    @Autowired
    StrategyClassifyMapper strategyClassifyMapper;

    @Transactional
    public void del(Long id)
    {
        strategyClassifyMapper.del(id);
    }

    @Transactional
    public void dels(Long[] id)
    {
        strategyClassifyMapper.dels(id);
    }

    public PageInfo findByStrategyId(Map<String, Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<StrategyClassifyEntity> list = strategyClassifyMapper.findByStrategyId(map);
        PageInfo<StrategyClassifyEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public StrategyClassifyEntity insert(StrategyClassifyEntity strategyClassifyEntity)
    {
        if(strategyClassifyEntity.getId()==null || strategyClassifyEntity.getId()==0)
        {
            strategyClassifyMapper.insert(strategyClassifyEntity);
        }
        else {
            strategyClassifyMapper.updateById(strategyClassifyEntity);
        }
        return strategyClassifyEntity;
    }

    public StrategyClassifyEntity findById(Long id)
    {
        return strategyClassifyMapper.selectById(id);
    }

    @Transactional
    public void updateStatus(Long id , String classifyStatus)
    {
        strategyClassifyMapper.updateStatus(id, classifyStatus);
    }
}
