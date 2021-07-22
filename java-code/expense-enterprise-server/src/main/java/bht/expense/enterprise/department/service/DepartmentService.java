package bht.expense.enterprise.department.service;

import bht.expense.enterprise.department.dao.DepartmentMapper;
import bht.expense.enterprise.department.dto.DepartmentUserDto;
import bht.expense.enterprise.department.entity.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/15 15:36
 */
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    public List<DepartmentEntity> findByEnterpriseId(Long enterpriseId)
    {
        return departmentMapper.findByEnterpriseId(enterpriseId);
    }

    @Transactional
    public void delete(Long id)
    {
        List<DepartmentEntity> list = departmentMapper.findByParentId(id);
        for(DepartmentEntity departmentEntity : list)
        {
            delete(departmentEntity.getId());
        }
        departmentMapper.del(id);
    }

    @Transactional
    public void insert(DepartmentEntity departmentEntity)
    {
        departmentMapper.insert(departmentEntity);
    }

    @Transactional
    public void updateDepartmentName(String departmentName , Long id)
    {
        departmentMapper.updateDepartmentName(departmentName, id);
    }

    public List<DepartmentUserDto> findUsers(Long enterpriseId,Long departmentId)
    {
        return departmentMapper.findUsers(enterpriseId,departmentId);
    }

}
