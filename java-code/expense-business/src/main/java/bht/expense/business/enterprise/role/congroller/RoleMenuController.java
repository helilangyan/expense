package bht.expense.business.enterprise.role.congroller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.role.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 姚轶文
 * @date 2021/4/15 18:16
 */
@Api(tags = "角色分配菜单")
@RestController
@RequestMapping("enterprise/role-menu")
public class RoleMenuController {

    @Autowired
    RoleMenuService roleMenuService;

    @ApiOperation("根据角色ID查询菜单信息")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true)
    @PostMapping("/findByRoleId")
    public ResultDto findByRoleId(Long roleId)
    {
        return roleMenuService.findByRoleId(roleId);
    }

    @ApiOperation("保存分配菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色", required = true),
            @ApiImplicitParam(name = "menuId", value = "菜单ID，数组", required = true)
    })
    @PostMapping("/insert")
    public ResultDto insert(Long roleId , Long[] menuId)
    {
        return roleMenuService.insert(roleId, menuId);
    }
}
