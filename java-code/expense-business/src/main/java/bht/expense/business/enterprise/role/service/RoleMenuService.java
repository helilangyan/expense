package bht.expense.business.enterprise.role.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.role.RoleMenuCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/15 18:17
 */
@Service
public class RoleMenuService {

    @Autowired
    RoleMenuCaller roleMenuCaller;

    public ResultDto findByRoleId(Long roleId)
    {
        return roleMenuCaller.findByRoleId(roleId);
    }


    public ResultDto insert(Long roleId ,Long[] menuId)
    {
        return roleMenuCaller.insert(roleId, menuId);
    }
}
