package bht.expense.enterprise.strategy.detail.vehicle.dao;

import bht.expense.enterprise.strategy.detail.vehicle.entity.StrategyVehicleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/6/24 15:51
 */
public interface StrategyVehicleMapper extends BaseMapper<StrategyVehicleEntity> {

    void del(Long id);

    void dels(Long[] id);

    List<StrategyVehicleEntity> findByStrategyId(Map<String, Object> map );

    void updateStatus(Long id , String status);
}
