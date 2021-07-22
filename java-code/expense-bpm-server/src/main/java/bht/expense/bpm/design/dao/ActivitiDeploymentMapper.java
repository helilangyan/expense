package bht.expense.bpm.design.dao;

import bht.expense.bpm.design.entity.ActivitiDeploymentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 姚轶文
 * @date 2021/4/27 17:05
 */
public interface ActivitiDeploymentMapper extends BaseMapper<ActivitiDeploymentEntity> {

    void deleteByEnterpriseIdAndType(Long enterpriseId , String type);

    String findByEnterpriseIdAndType(Long enterpriseId , String type);
}
