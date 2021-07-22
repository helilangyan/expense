package bht.expense.enterprise.menu.dao;

import bht.expense.enterprise.menu.entity.MenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/14 10:30
 */
public interface MenuMapper extends BaseMapper<MenuEntity> {

    void del(Long id);

    void dels(Long[] id);

    List<MenuEntity> findByAll(Map<String, String> map);
}
