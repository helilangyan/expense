package bht.expense.enterprise.menu.service;

import bht.expense.enterprise.menu.dao.MenuMapper;
import bht.expense.enterprise.menu.entity.MenuEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/14 10:42
 */
@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Transactional
    public void del(Long id)
    {
        menuMapper.del(id);
    }

    @Transactional
    public void dels(Long[] id)
    {
        menuMapper.dels(id);
    }

    public PageInfo findByAll(Map<String, String> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<MenuEntity> list = menuMapper.findByAll(map);
        PageInfo<MenuEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public MenuEntity insert(MenuEntity menuEntity)
    {
        if(menuEntity.getId()==null || menuEntity.getId()==0)
        {
            menuMapper.insert(menuEntity);
        }
        else {
            menuMapper.updateById(menuEntity);
        }
        return menuEntity;
    }

    public MenuEntity findById(Long id)
    {
        return menuMapper.selectById(id);
    }

}
