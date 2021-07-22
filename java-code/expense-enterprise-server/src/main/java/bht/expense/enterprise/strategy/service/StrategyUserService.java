package bht.expense.enterprise.strategy.service;

import bht.expense.enterprise.strategy.dao.StrategyUserMapper;
import bht.expense.enterprise.strategy.detail.basic.entity.StrategyBasicEntity;
import bht.expense.enterprise.strategy.detail.classify.entity.StrategyClassifyEntity;
import bht.expense.enterprise.strategy.detail.label.entity.StrategyLabelEntity;
import bht.expense.enterprise.strategy.detail.vehicle.entity.StrategyVehicleEntity;
import bht.expense.enterprise.strategy.entity.StrategyEntity;
import bht.expense.enterprise.strategy.entity.StrategyUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/7/14 16:36
 */
@Service
public class StrategyUserService {

    @Autowired
    StrategyUserMapper strategyUserMapper;

    @Transactional
    public void del(Long id)
    {
        strategyUserMapper.deleteById(id);
    }

    @Transactional
    public StrategyUserEntity insert(StrategyUserEntity strategyUserEntity)
    {
        if(strategyUserEntity.getId()==null || strategyUserEntity.getId()==0)
        {
            strategyUserMapper.insert(strategyUserEntity);
        }
        else {
            strategyUserMapper.updateById(strategyUserEntity);
        }
        return strategyUserEntity;
    }

    @Transactional
    public StrategyUserEntity update(StrategyUserEntity strategyUserEntity)
    {
        strategyUserMapper.updateById(strategyUserEntity);
        return strategyUserEntity;
    }

    public StrategyUserEntity findById(Long id)
    {
        return strategyUserMapper.selectById(id);
    }

    public List<StrategyClassifyEntity> findByUserIdAndEnterpriseIdToClassify(Long userId , Long enterpriseId)
    {
        return strategyUserMapper.findByUserIdAndEnterpriseIdToClassify(userId, enterpriseId);
    }

    public List<StrategyLabelEntity> findByUserIdAndEnterpriseIdToLabel(Long userId , Long enterpriseId)
    {
        return strategyUserMapper.findByUserIdAndEnterpriseIdToLabel(userId, enterpriseId);
    }

    public List<StrategyVehicleEntity> findByUserIdAndEnterpriseIdToVehicle(Long userId , Long enterpriseId)
    {
        return strategyUserMapper.findByUserIdAndEnterpriseIdToVehicle(userId, enterpriseId);
    }

    public StrategyBasicEntity findByUserIdAndEnterpriseIdToBasic(Long userId , Long enterpriseId)
    {
        return strategyUserMapper.findByUserIdAndEnterpriseIdToBasic(userId, enterpriseId);
    }
}
