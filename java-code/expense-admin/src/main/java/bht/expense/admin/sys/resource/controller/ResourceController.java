package bht.expense.admin.sys.resource.controller;

import bht.expense.admin.common.PageListDto;
import bht.expense.admin.common.ResultDto;
import bht.expense.admin.sys.log.aop.OperationLogDetail;
import bht.expense.admin.sys.log.aop.OperationType;
import bht.expense.admin.sys.log.aop.OperationUnit;
import bht.expense.admin.sys.resource.entity.ResourceEntity;
import bht.expense.admin.sys.resource.service.ResourceService;
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
 * @date 2020/7/14 10:50
 */
@Api(tags = "资源管理相关接口")
@RestController
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    @ApiOperation("资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit,String resourceName)
    {
        Map<String,String> map = new HashMap();
        map.put("resourceName",resourceName);
        PageListDto<ResourceEntity> pageListData = new PageListDto();
        PageInfo pageInfo = resourceService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "角色id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable String id)
    {
        ResourceEntity resourceEntity = resourceService.findById(id);
        return ResultDto.success(resourceEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @OperationLogDetail(detail = "编辑资源", level = 3, operationUnit = OperationUnit.RESOURCE, operationType = OperationType.INSERT)
    @PostMapping("/insert")
    public ResultDto insert(@ModelAttribute ResourceEntity resourceEntity) {
        resourceEntity = resourceService.insert(resourceEntity);
        return ResultDto.success(resourceEntity);
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    @OperationLogDetail(detail = "删除资源", level = 3, operationUnit = OperationUnit.RESOURCE, operationType = OperationType.DELETE)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable String id) {
        resourceService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @OperationLogDetail(detail = "删除资源", level = 3, operationUnit = OperationUnit.RESOURCE, operationType = OperationType.DELETE)
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable String[] id) {
        resourceService.dels(id);
        return ResultDto.success();
    }

    @ApiOperation("获取全部资源，树形结构，返回JSON字符串")
    @PostMapping("/resource-tree")
    public ResultDto getResourceTree()
    {
        return ResultDto.success(resourceService.getResourceTree());
    }
}
