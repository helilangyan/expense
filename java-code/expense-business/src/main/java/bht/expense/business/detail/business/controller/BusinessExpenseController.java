package bht.expense.business.detail.business.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.business.dto.ExpenseDetailEntityDto;
import bht.expense.business.detail.business.service.BusinessExpenseService;
import bht.expense.business.security.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/21 10:06
 */
@Api(tags = "普通费用单、出差费用单、里程费用单")
@RestController
@RequestMapping("detail/business-expense")
public class BusinessExpenseController {

    @Autowired
    BusinessExpenseService businessExpenseService;

    @ApiOperation("列表，我填写的报销清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true),
            @ApiImplicitParam(name = "expenseType", value = "分类ID"),
            @ApiImplicitParam(name = "tags", value = "标签ID"),
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "expenseName", value = "费用名称")
    })
    @PostMapping("/list")
    public ResultDto findByUserIdAndEnterpriseId(String expenseName,String expenseType , String tags , String startTime , String endTime ,Long enterpriseId , int page, int limit)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return businessExpenseService.findByUserIdAndEnterpriseId(expenseName,expenseType,tags,startTime , endTime,Long.parseLong(userEntity.getId()), enterpriseId, page, limit);
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        return businessExpenseService.delete(id);
    }

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return businessExpenseService.findById(id);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(ExpenseDetailEntityDto expenseDetailEntityDto)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        expenseDetailEntityDto.setUserId(Long.parseLong(userEntity.getId()));
        return businessExpenseService.insert(expenseDetailEntityDto);
    }

    @ApiOperation("验证策略")
    @PostMapping("/check-insert")
    public ResultDto checkInsert(ExpenseDetailEntityDto expenseDetailEntityDto)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        expenseDetailEntityDto.setUserId(Long.parseLong(userEntity.getId()));
        return businessExpenseService.checkInsert(expenseDetailEntityDto);
    }
}
