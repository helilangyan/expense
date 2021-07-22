package bht.expense.enterprise.role.dao;

import bht.expense.enterprise.role.dto.RoleMenuDto;
import bht.expense.enterprise.role.entity.RoleMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/15 11:11
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {

    void deleteByRoleId(Long roleId);

    List<RoleMenuDto> findByRoleId(Long roleId);
}
