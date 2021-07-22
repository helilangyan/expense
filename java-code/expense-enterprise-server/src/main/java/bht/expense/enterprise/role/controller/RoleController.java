package bht.expense.enterprise.role.controller;

import bht.expense.enterprise.bank.entity.BankEntity;
import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.role.entity.RoleEntity;
import bht.expense.enterprise.role.service.RoleService;
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
 * @date 2021/4/14 14:43
 */

@Api(tags = "设置本企业角色")
@RestController
@RequestMapping("role")
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
        Map<String,Object> map = new HashMap();
        map.put("enterpriseId",enterpriseId);

        PageListDto<BankEntity> pageListData = new PageListDto();
        PageInfo pageInfo = roleService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        RoleEntity roleEntity = roleService.findById(id);
        return ResultDto.success(roleEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody RoleEntity roleEntity) {
        roleEntity = roleService.insert(roleEntity);
        return ResultDto.success(roleEntity);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        roleService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        roleService.dels(id);
        return ResultDto.success();
    }
}
