package bht.expense.admin.sys.user.service;

import bht.expense.admin.sys.user.dao.UserRoleMapper;
import bht.expense.admin.sys.user.dto.UserRoleDto;
import bht.expense.admin.sys.user.entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/7/14 15:26
 */
@Service
public class UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    public List<UserRoleDto> findByUserId(String userId)
    {
        return userRoleMapper.findByUserId(userId);
    }

    @Transactional
    public void insert(String userId , String roleIds[])
    {
        userRoleMapper.deleteByUserId(userId);

        if(roleIds!=null && roleIds.length>0)
        {
            for(String roleId : roleIds)
            {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setRoleId(roleId);
                userRoleEntity.setUserId(userId);
                userRoleMapper.insert(userRoleEntity);
            }
        }
    }


}
