package bht.expense.admin.sys.authority.service;

import bht.expense.admin.sys.authority.dao.AuthorityMapper;
import bht.expense.admin.sys.authority.entity.AuthorityEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/14 10:05
 */

@Service
public class AuthorityService {

    @Autowired
    AuthorityMapper authorityMapper;

    @Transactional
    public AuthorityEntity insert(AuthorityEntity authorityEntity)
    {
        if(authorityEntity.getId()==null)
        {
            authorityMapper.insert(authorityEntity);
        }
        else {
            authorityMapper.updateById(authorityEntity);
        }
        return authorityEntity;
    }

    @Transactional
    public void del(String id)
    {
        authorityMapper.del(id);
    }

    @Transactional
    public void dels(String[] id)
    {
        authorityMapper.dels(id);
    }

    public AuthorityEntity findById(String id)
    {
        return authorityMapper.selectById(id);
    }

    public PageInfo findByAll(Map<String,String> map , int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<AuthorityEntity> list = authorityMapper.findByAll(map);
        PageInfo<AuthorityEntity> pageInfo = new PageInfo<AuthorityEntity>(list);
        return pageInfo;
    }
}
