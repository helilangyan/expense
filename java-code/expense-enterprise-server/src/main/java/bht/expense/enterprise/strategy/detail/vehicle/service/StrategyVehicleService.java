package bht.expense.enterprise.strategy.detail.vehicle.service;

import bht.expense.enterprise.strategy.detail.vehicle.dao.StrategyVehicleMapper;
import bht.expense.enterprise.strategy.detail.vehicle.entity.StrategyVehicleEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/6/24 15:53
 */
@Service
public class StrategyVehicleService {

    @Autowired
    StrategyVehicleMapper strategyVehicleMapper;


    @Transactional
    public void del(Long id)
    {
        strategyVehicleMapper.del(id);
    }

    @Transactional
    public void dels(Long[] id)
    {
        strategyVehicleMapper.dels(id);
    }

    public PageInfo findByStrategyId(Map<String, Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<StrategyVehicleEntity> list = strategyVehicleMapper.findByStrategyId(map);
        PageInfo<StrategyVehicleEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public StrategyVehicleEntity insert(StrategyVehicleEntity strategyVehicleEntity)
    {
        if(strategyVehicleEntity.getId()==null || strategyVehicleEntity.getId()==0)
        {
            strategyVehicleMapper.insert(strategyVehicleEntity);
        }
        else {
            strategyVehicleMapper.updateById(strategyVehicleEntity);
        }
        return strategyVehicleEntity;
    }

    public StrategyVehicleEntity findById(Long id)
    {
        return strategyVehicleMapper.selectById(id);
    }

    @Transactional
    public void updateStatus(Long id , String status)
    {
        strategyVehicleMapper.updateStatus(id, status);
    }
}
