package bht.expense.admin.sys.role.service;

import bht.expense.admin.sys.role.dao.RoleMapper;
import bht.expense.admin.sys.role.entity.RoleEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/13 15:16
 */
@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Transactional
    public RoleEntity insert(RoleEntity roleEntity)
    {
        if(roleEntity.getId()==null)
        {
            roleMapper.insert(roleEntity);
        }
        else {
            roleMapper.updateById(roleEntity);
        }
        return roleEntity;
    }

    @Transactional
    public void del(String id)
    {
        roleMapper.del(id);
    }

    @Transactional
    public void dels(String[] id)
    {
        roleMapper.dels(id);
    }

    public RoleEntity findById(String id)
    {
        return roleMapper.selectById(id);
    }

    public PageInfo findByAll(Map<String,String> map , int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<RoleEntity> list = roleMapper.findByAll(map);
        PageInfo<RoleEntity> pageInfo = new PageInfo<RoleEntity>(list);
        return pageInfo;
    }
}
