package bht.expense.admin.sys.role.service;

import bht.expense.admin.sys.role.dao.RoleAuthorityMapper;
import bht.expense.admin.sys.role.dto.RoleAuthorityDto;
import bht.expense.admin.sys.role.entity.RoleAuthorityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/7/15 14:43
 */

@Service
public class RoleAuthorityService {

    @Autowired
    RoleAuthorityMapper roleAuthorityMapper;

    public List<RoleAuthorityDto> findByRoleId(String roleId)
    {
        return roleAuthorityMapper.findByRoleId(roleId);
    }


    @Transactional
    public void insert(String roleId , String authorityIds[])
    {
        roleAuthorityMapper.deleteByRoleId(roleId);

        if(authorityIds!=null && authorityIds.length>0)
        {
            for(String authorityId : authorityIds)
            {
                RoleAuthorityEntity roleAuthorityEntity = new RoleAuthorityEntity();
                roleAuthorityEntity.setAuthorityId(authorityId);
                roleAuthorityEntity.setRoleId(roleId);
                roleAuthorityMapper.insert(roleAuthorityEntity);
            }
        }
    }
}
