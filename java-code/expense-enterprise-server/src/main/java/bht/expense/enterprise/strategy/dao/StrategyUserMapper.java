package bht.expense.enterprise.strategy.dao;

import bht.expense.enterprise.strategy.detail.basic.entity.StrategyBasicEntity;
import bht.expense.enterprise.strategy.detail.classify.entity.StrategyClassifyEntity;
import bht.expense.enterprise.strategy.detail.label.entity.StrategyLabelEntity;
import bht.expense.enterprise.strategy.detail.vehicle.entity.StrategyVehicleEntity;
import bht.expense.enterprise.strategy.entity.StrategyUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/7/14 16:35
 */
public interface StrategyUserMapper extends BaseMapper<StrategyUserEntity> {

    List<StrategyClassifyEntity> findByUserIdAndEnterpriseIdToClassify(Long userId , Long enterpriseId);

    List<StrategyLabelEntity> findByUserIdAndEnterpriseIdToLabel(Long userId , Long enterpriseId);

    List<StrategyVehicleEntity> findByUserIdAndEnterpriseIdToVehicle(Long userId , Long enterpriseId);

    StrategyBasicEntity findByUserIdAndEnterpriseIdToBasic(Long userId , Long enterpriseId);
}
