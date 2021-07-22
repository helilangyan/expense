package bht.expense.admin.sys.role.controller;

import bht.expense.admin.common.PageListDto;
import bht.expense.admin.common.ResultDto;
import bht.expense.admin.sys.log.aop.OperationLogDetail;
import bht.expense.admin.sys.log.aop.OperationType;
import bht.expense.admin.sys.log.aop.OperationUnit;
import bht.expense.admin.sys.role.entity.RoleEntity;
import bht.expense.admin.sys.role.service.RoleService;
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
 * @date 2020/7/13 15:20
 */
@Api(tags = "角色管理相关接口")
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleService roleService;


    @ApiOperation("角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
    })
    @PostMapping("/list")
    public ResultDto list(int page,int limit)
    {
        Map<String,String> map = new HashMap();

        PageListDto<RoleEntity> pageListData = new PageListDto();
        PageInfo pageInfo = roleService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "角色id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable String id)
    {
        RoleEntity roleEntity = roleService.findById(id);
        return ResultDto.success(roleEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @OperationLogDetail(detail = "编辑角色", level = 3, operationUnit = OperationUnit.ROLE, operationType = OperationType.INSERT)
    @PostMapping("/insert")
    public ResultDto insert(@ModelAttribute RoleEntity roleEntity) {
        roleEntity = roleService.insert(roleEntity);
        return ResultDto.success(roleEntity);
    }


    @ApiOperation("根据角色ID删除")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    @OperationLogDetail(detail = "删除角色", level = 3, operationUnit = OperationUnit.ROLE, operationType = OperationType.DELETE)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable String id) {
        roleService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @OperationLogDetail(detail = "删除角色", level = 3, operationUnit = OperationUnit.ROLE, operationType = OperationType.DELETE)
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable String[] id) {
        roleService.dels(id);
        return ResultDto.success();
    }
}
