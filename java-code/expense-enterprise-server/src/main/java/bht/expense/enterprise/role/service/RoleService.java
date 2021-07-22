package bht.expense.enterprise.role.service;

import bht.expense.enterprise.menu.entity.MenuEntity;
import bht.expense.enterprise.role.dao.RoleMapper;
import bht.expense.enterprise.role.entity.RoleEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/14 14:42
 */

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Transactional
    public void del(Long id)
    {
        roleMapper.del(id);
    }

    @Transactional
    public void dels(Long[] id)
    {
        roleMapper.dels(id);
    }

    public PageInfo findByAll(Map<String, Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<MenuEntity> list = roleMapper.findByAll(map);
        PageInfo<MenuEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public RoleEntity insert(RoleEntity roleEntity)
    {
        if(roleEntity.getId()==null || roleEntity.getId()==0)
        {
            roleMapper.insert(roleEntity);
        }
        else {
            roleMapper.updateById(roleEntity);
        }
        return roleEntity;
    }

    public RoleEntity findById(Long id)
    {
        return roleMapper.selectById(id);
    }

}
