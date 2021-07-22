package bht.expense.bpm.design.service;

import bht.expense.bpm.design.dao.ActivitiDeploymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/27 17:13
 */
@Service
public class ActivitiDeploymentService {

    @Autowired
    ActivitiDeploymentMapper activitiDeploymentMapper;

    public String findByEnterpriseIdAndType(Long enterpriseId , String type)
    {
        return activitiDeploymentMapper.findByEnterpriseIdAndType(enterpriseId, type);
    }
}
