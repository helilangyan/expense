package bht.expense.business.enterprise.caller.role;

import bht.expense.business.common.ResultDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/15 18:18
 */
@FeignClient(value = "expense-enterprise-server",fallback = RoleMenuCallerError.class, contextId = "RoleMenuCaller")
public interface RoleMenuCaller {

    @PostMapping("/role-menu/findByRoleId")
    ResultDto findByRoleId(@RequestParam("roleId")  Long roleId);


    @PostMapping("/role-menu/insert")
    ResultDto insert(@RequestParam("roleId") Long roleId , @RequestParam("menuId") Long[] menuId);
}
