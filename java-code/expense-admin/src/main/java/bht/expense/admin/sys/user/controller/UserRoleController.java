package bht.expense.admin.sys.user.controller;


import bht.expense.admin.common.ResultDto;
import bht.expense.admin.sys.log.aop.OperationLogDetail;
import bht.expense.admin.sys.log.aop.OperationType;
import bht.expense.admin.sys.log.aop.OperationUnit;
import bht.expense.admin.sys.user.dto.UserRoleDto;
import bht.expense.admin.sys.user.service.UserRoleService;
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
 * @date 2020/7/14 15:41
 */
@Api(tags = "给用户分配角色接口")
@RestController
@RequestMapping("user-role")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @ApiOperation("列表")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    @PostMapping("/list")
    public ResultDto list(String userId)
    {
        List<UserRoleDto> list =  userRoleService.findByUserId(userId);
        return ResultDto.success(list);
    }

    @ApiOperation("保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID"),
            @ApiImplicitParam(name = "roleIds", value = "数组，角色ID")
    })
    @OperationLogDetail(detail = "配置用户角色", level = 3, operationUnit = OperationUnit.USER, operationType = OperationType.INSERT)
    @PostMapping("/insert")
    public ResultDto insert(String userId , String[] roleIds)
    {
        userRoleService.insert(userId,roleIds);
        return ResultDto.success();
    }
}
