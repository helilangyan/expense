package bht.expense.admin.sys.role.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.admin.sys.role.dto.RoleAuthorityDto;
import bht.expense.admin.sys.role.entity.RoleAuthorityEntity;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/7/15 14:26
 */
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthorityEntity> {

    public List<RoleAuthorityDto> findByRoleId(String roleId);

    public void deleteByRoleId(String roleId);
}
