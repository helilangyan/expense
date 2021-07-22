package bht.expense.admin.sys.authority.controller;

import bht.expense.admin.common.PageListDto;
import bht.expense.admin.common.ResultDto;
import bht.expense.admin.sys.authority.entity.AuthorityEntity;
import bht.expense.admin.sys.authority.service.AuthorityService;
import bht.expense.admin.sys.log.aop.OperationLogDetail;
import bht.expense.admin.sys.log.aop.OperationType;
import bht.expense.admin.sys.log.aop.OperationUnit;
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
 * @date 2020/7/14 10:07
 */

@Api(tags = "权限管理相关接口")
@RestController
@RequestMapping("authority")
public class AuthorityController {

    @Autowired
    AuthorityService authorityService;


    @ApiOperation("权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit)
    {
        Map<String,String> map = new HashMap();

        PageListDto<AuthorityEntity> pageListData = new PageListDto();
        PageInfo pageInfo = authorityService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "权限id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable String id)
    {
        AuthorityEntity authorityEntity = authorityService.findById(id);
        return ResultDto.success(authorityEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @OperationLogDetail(detail = "编辑权限", level = 3, operationUnit = OperationUnit.AUTHORITY, operationType = OperationType.INSERT)
    @PostMapping("/insert")
    public ResultDto insert(@ModelAttribute AuthorityEntity authorityEntity) {
        authorityEntity = authorityService.insert(authorityEntity);
        return ResultDto.success(authorityEntity);
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true)
    @OperationLogDetail(detail = "删除权限", level = 3, operationUnit = OperationUnit.AUTHORITY, operationType = OperationType.DELETE)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable String id) {
        authorityService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @OperationLogDetail(detail = "删除权限", level = 3, operationUnit = OperationUnit.AUTHORITY, operationType = OperationType.DELETE)
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable String[] id) {
        authorityService.dels(id);
        return ResultDto.success();
    }
}
