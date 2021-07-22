package bht.expense.admin.sys.role.controller;

import bht.expense.admin.common.ResultDto;
import bht.expense.admin.sys.log.aop.OperationLogDetail;
import bht.expense.admin.sys.log.aop.OperationType;
import bht.expense.admin.sys.log.aop.OperationUnit;
import bht.expense.admin.sys.role.dto.RoleAuthorityDto;
import bht.expense.admin.sys.role.service.RoleAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/7/15 14:47
 */

@Api(tags = "给角色分配权限接口")
@RestController
@RequestMapping("role-authority")
public class RoleAuthorityController {

    @Autowired
    RoleAuthorityService authorityService;

    @ApiOperation("列表")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true)
    @PostMapping("/list")
    public ResultDto list(String roleId)
    {
        List<RoleAuthorityDto> list =  authorityService.findByRoleId(roleId);
        return ResultDto.success(list);
    }

    @ApiOperation("保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID"),
            @ApiImplicitParam(name = "authorityIds", value = "数组，权限ID")
    })
    @OperationLogDetail(detail = "给角色分配权限", level = 3, operationUnit = OperationUnit.ROLE, operationType = OperationType.INSERT)
    @PostMapping("/insert")
    public ResultDto insert(String roleId , String[] authorityIds)
    {
        authorityService.insert(roleId,authorityIds);
        return ResultDto.success();
    }
}
