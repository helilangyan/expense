package bht.expense.enterprise.role.dao;

import bht.expense.enterprise.menu.entity.MenuEntity;
import bht.expense.enterprise.role.entity.RoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/14 14:27
 */
public interface RoleMapper  extends BaseMapper<RoleEntity> {

    void del(Long id);

    void dels(Long[] id);

    List<MenuEntity> findByAll(Map<String, Object> map );
}
