package bht.expense.admin.sys.authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.admin.sys.authority.entity.AuthorityEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/14 10:03
 */
public interface AuthorityMapper extends BaseMapper<AuthorityEntity> {

    void del(String id);

    void dels(String[] id);

    List<AuthorityEntity> findByAll(Map<String, String> map);
}
