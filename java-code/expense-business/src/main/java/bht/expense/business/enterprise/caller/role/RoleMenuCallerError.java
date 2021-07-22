package bht.expense.business.enterprise.caller.role;

import bht.expense.business.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/15 18:19
 */
@Slf4j
public class RoleMenuCallerError implements RoleMenuCaller{

    @Override
    public ResultDto findByRoleId(Long roleId) {
        log.error("invoke expense-enterprise-server: /role-menu/findByRoleId error");
        return null;
    }

    @Override
    public ResultDto insert(Long roleId, Long[] menuId) {
        log.error("invoke expense-enterprise-server: /role-menu/insert error");
        return null;
    }
}
