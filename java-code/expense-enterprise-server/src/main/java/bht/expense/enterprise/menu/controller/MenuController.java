package bht.expense.enterprise.menu.controller;

import bht.expense.enterprise.bank.entity.BankEntity;
import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.menu.entity.MenuEntity;
import bht.expense.enterprise.menu.service.MenuService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/14 10:46
 */
@Api(tags = "业务端导航菜单设置")
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
        Map<String,String> map = new HashMap();

        PageListDto<BankEntity> pageListData = new PageListDto();
        PageInfo pageInfo = menuService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        MenuEntity menuEntity = menuService.findById(id);
        return ResultDto.success(menuEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody MenuEntity menuEntity) {
        menuEntity = menuService.insert(menuEntity);
        return ResultDto.success(menuEntity);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        menuService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        menuService.dels(id);
        return ResultDto.success();
    }
}
