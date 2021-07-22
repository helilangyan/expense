package bht.expense.admin.menu.controller;

import bht.expense.admin.common.ResultDto;
import bht.expense.admin.menu.dto.MenuEntityDto;
import bht.expense.admin.menu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/14 11:24
 */
@Api(tags = "设置业务端菜单导航")
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true)
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit)
    {
        return menuService.list(page, limit);
    }

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return menuService.findById(id);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(MenuEntityDto menuEntityDto)
    {
        return menuService.insert(menuEntityDto);
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        return menuService.delete(id);
    }

    @ApiOperation("批量删除，传入ID数组")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id)
    {
        return menuService.deletes(id);
    }
}
