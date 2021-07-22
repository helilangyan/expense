package bht.expense.admin.sys.authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.admin.sys.authority.entity.AuthorityResourceEntity;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/7/16 11:15
 */


public interface AuthorityResourceMapper extends BaseMapper<AuthorityResourceEntity> {

    public List<AuthorityResourceEntity> findByAuthorityId(String authorityId);

    public void deleteByAuthorityId(String authorityId);
}
