package bht.expense.admin.sys.authority.service;

import bht.expense.admin.sys.authority.dao.AuthorityResourceMapper;
import bht.expense.admin.sys.authority.entity.AuthorityResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/7/16 11:24
 */

@Service
public class AuthorityResourceService {

    @Autowired
    AuthorityResourceMapper authorityResourceMapper;

    public List<AuthorityResourceEntity> findByAuthorityId(String authorityId)
    {
        return authorityResourceMapper.findByAuthorityId(authorityId);
    }

    @Transactional
    public void insert(String authorityId, String resourceIds[])
    {
        authorityResourceMapper.deleteByAuthorityId(authorityId);

        if(resourceIds!=null && resourceIds.length>0)
        {
            for(String resourceId : resourceIds)
            {
                AuthorityResourceEntity authorityResourceEntity = new AuthorityResourceEntity();
                authorityResourceEntity.setAuthorityId(authorityId);
                authorityResourceEntity.setResourceId(resourceId);
                authorityResourceMapper.insert(authorityResourceEntity);
            }
        }
    }
}
