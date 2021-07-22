package bht.expense.enterprise.position.dao;

import bht.expense.enterprise.position.entity.PositionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/16 14:21
 */
public interface PositionMapper  extends BaseMapper<PositionEntity> {

    List<PositionEntity> findByEnterpriseId(Map<String, Object> map);

    void delete(Long id);

    void deletes(Long[] id);

    void updatePositionName(String positionName , Long id);
}
