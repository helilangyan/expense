package bht.expense.enterprise.role.service;

import bht.expense.enterprise.role.dao.RoleMenuMapper;
import bht.expense.enterprise.role.dto.RoleMenuDto;
import bht.expense.enterprise.role.entity.RoleMenuEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/15 13:38
 */

@Service
public class RoleMenuService {

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Transactional
    public void deleteByRoleId(Long roleId)
    {
        roleMenuMapper.deleteByRoleId(roleId);
    }

    public List<RoleMenuDto> findByRoleId(Long roleId)
    {
        return roleMenuMapper.findByRoleId(roleId);
    }

    @Transactional
    public void insert(Long roleId , Long[] menuId)
    {
        roleMenuMapper.deleteByRoleId(roleId);
        for(Long mid : menuId)
        {
            RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
            roleMenuEntity.setMenuId(mid);
            roleMenuEntity.setRoleId(roleId);
            roleMenuMapper.insert(roleMenuEntity);
        }
    }
}
