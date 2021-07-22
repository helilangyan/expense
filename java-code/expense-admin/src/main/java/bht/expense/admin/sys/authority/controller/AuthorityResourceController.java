package bht.expense.admin.sys.authority.controller;

import bht.expense.admin.common.ResultDto;
import bht.expense.admin.sys.authority.entity.AuthorityResourceEntity;
import bht.expense.admin.sys.authority.service.AuthorityResourceService;
import bht.expense.admin.sys.log.aop.OperationLogDetail;
import bht.expense.admin.sys.log.aop.OperationType;
import bht.expense.admin.sys.log.aop.OperationUnit;
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
 * @date 2020/7/16 13:23
 */

@Api(tags = "给权限分配资源接口")
@RestController
@RequestMapping("authority-resource")
public class AuthorityResourceController {

    @Autowired
    AuthorityResourceService authorityResourceService;


    @ApiOperation("根据权限ID获取对应的资源ID")
    @ApiImplicitParam(name = "authorityId", value = "权限ID", required = true)
    @PostMapping("/findByAuthorityId")
    public ResultDto findByAuthorityId(String authorityId)
    {
        List<AuthorityResourceEntity> list = authorityResourceService.findByAuthorityId(authorityId);
        return ResultDto.success(list);
    }

    @ApiOperation("保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorityId", value = "权限ID"),
            @ApiImplicitParam(name = "resourceIds", value = "数组，资源ID")
    })
    @OperationLogDetail(detail = "给权限分配资源", level = 3, operationUnit = OperationUnit.AUTHORITY, operationType = OperationType.INSERT)
    @PostMapping("/insert")
    public ResultDto insert(String authorityId , String[] resourceIds)
    {
        authorityResourceService.insert(authorityId,resourceIds);
        return ResultDto.success();
    }

}
