package bht.expense.enterprise.department.dao;

import bht.expense.enterprise.department.dto.DepartmentUserDto;
import bht.expense.enterprise.department.entity.DepartmentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/15 15:29
 */
public interface DepartmentMapper  extends BaseMapper<DepartmentEntity> {

    List<DepartmentEntity> findByEnterpriseId(Long enterpriseId);

    List<DepartmentEntity> findByParentId(Long parentId);

    void del(Long id);

    void updateDepartmentName(String departmentName , Long id);

    List<DepartmentUserDto> findUsers(Long enterpriseId , Long departmentId);
}
