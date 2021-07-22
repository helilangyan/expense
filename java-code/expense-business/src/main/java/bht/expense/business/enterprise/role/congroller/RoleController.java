package bht.expense.business.enterprise.role.congroller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.role.dto.RoleEntityDto;
import bht.expense.business.enterprise.role.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 姚轶文
 * @date 2021/4/14 15:40
 */
@Api(tags = "设置本企业角色")
@RestController
@RequestMapping("enterprise/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit , Long enterpriseId)
    {
        return roleService.list(page, limit, enterpriseId);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return roleService.findById(id);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody RoleEntityDto roleEntityDto) {
        return roleService.insert(roleEntityDto);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        return roleService.delete(id);
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        return roleService.deletes(id);
    }


}
