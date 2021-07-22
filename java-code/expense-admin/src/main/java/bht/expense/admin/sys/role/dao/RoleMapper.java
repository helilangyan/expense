package bht.expense.admin.sys.role.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.admin.sys.role.entity.RoleEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/13 15:10
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    void del(String id);

    void dels(String[] id);

    List<RoleEntity> findByAll(Map<String, String> map);
}
