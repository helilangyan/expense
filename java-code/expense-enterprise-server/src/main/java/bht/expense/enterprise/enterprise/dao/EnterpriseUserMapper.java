package bht.expense.enterprise.enterprise.dao;

import bht.expense.enterprise.department.dto.DepartmentUserDto;
import bht.expense.enterprise.enterprise.dto.EnterpriseUserDto;
import bht.expense.enterprise.enterprise.entity.EnterpriseUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/12 11:13
 */
public interface EnterpriseUserMapper  extends BaseMapper<EnterpriseUserEntity> {

    List<EnterpriseUserDto> findByUserId(Long userId);

    List<EnterpriseUserDto> findByEnterpriseId(Long enterpriseId);

    void clearDefault(Long userId);

    void setDefault(Long id);

    DepartmentUserDto findById(Long id);
}
