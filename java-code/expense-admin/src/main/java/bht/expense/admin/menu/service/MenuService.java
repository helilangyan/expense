package bht.expense.admin.menu.service;

import bht.expense.admin.common.ResultDto;
import bht.expense.admin.menu.caller.MenuCaller;
import bht.expense.admin.menu.dto.MenuEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/14 11:20
 */
@Service
public class MenuService {

    @Autowired
    MenuCaller menuCaller;

    public ResultDto list(int page, int limit)
    {
        return menuCaller.list(page, limit);
    }

    public ResultDto findById(Long id)
    {
        return menuCaller.findById(id);
    }

    public ResultDto insert(MenuEntityDto menuEntityDto)
    {
        return menuCaller.insert(menuEntityDto);
    }

    public ResultDto delete(Long id)
    {
        return menuCaller.delete(id);
    }

    public ResultDto deletes(Long[] id)
    {
        return menuCaller.deletes(id);
    }
}
