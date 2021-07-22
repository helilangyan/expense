package bht.expense.admin.enterprise.enterprise.enterprise.controller;

import bht.expense.admin.common.ResultDto;
import bht.expense.admin.enterprise.enterprise.enterprise.dto.EnterpriseEntityDto;
import bht.expense.admin.enterprise.enterprise.enterprise.service.EnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/9 16:43
 */
@Api(tags = "企业基本信息")
@RestController
@RequestMapping("enterprise")
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;

    @ApiOperation("全部企业列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true)
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit)
    {
        return enterpriseService.list(page, limit);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return enterpriseService.findById(id);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        return  enterpriseService.delete(id);
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        return enterpriseService.deletes(id);
    }


    @ApiOperation("审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "0=未开通 ， 1=已开通", required = true),
            @ApiImplicitParam(name = "id", value = "ID", required = true)
    })
    @PutMapping("/approve")
    public ResultDto approve(String status , Long id)
    {
        return enterpriseService.approve(status,id);
    }
}
