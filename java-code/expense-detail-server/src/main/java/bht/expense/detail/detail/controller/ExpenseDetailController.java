package bht.expense.detail.detail.controller;

import bht.expense.detail.common.PageListDto;
import bht.expense.detail.common.ResultDto;
import bht.expense.detail.detail.dto.CheckInsertDto;
import bht.expense.detail.detail.dto.ExpenseDetailListDto;
import bht.expense.detail.detail.entity.ExpenseDetailEntity;
import bht.expense.detail.detail.service.ExpenseDetailService;
import bht.expense.detail.strategy.dto.StrategyBasicEntityDto;
import bht.expense.detail.strategy.dto.StrategyClassifyEntityDto;
import bht.expense.detail.strategy.service.StrategyService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/20 17:38
 */
@Api(tags = "报销信息")
@RestController
@RequestMapping("expense-detail")
public class ExpenseDetailController {

    @Autowired
    ExpenseDetailService expenseDetailService;

    @Autowired
    StrategyService strategyService;

    @ApiOperation("列表，我填写的报销清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true),
            @ApiImplicitParam(name = "expenseType", value = "分类ID"),
            @ApiImplicitParam(name = "tags", value = "标签ID"),
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "expenseName", value = "费用名称")
    })
    @PostMapping("/list")
    public ResultDto findByUserIdAndEnterpriseId(String expenseName , String expenseType , String tags , String startTime , String endTime ,Long userId, Long enterpriseId , int page, int limit)
    {
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("enterpriseId",enterpriseId);
        map.put("expenseType",expenseType);
        map.put("tags",tags);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("expenseName",expenseName);

        PageListDto<ExpenseDetailListDto> pageListData = new PageListDto();
        PageInfo pageInfo = expenseDetailService.findByUserIdAndEnterpriseId(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        expenseDetailService.delete(id);
        return ResultDto.success();
    }

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return ResultDto.success(expenseDetailService.findById(id));
    }


    @ApiOperation("验证策略")
    @PostMapping("/check-insert")
    public ResultDto checkInsert(@RequestBody ExpenseDetailEntity expenseDetailEntity)
    {
        ResultDto resultStrategyBasicDto = strategyService.findByUserIdAndEnterpriseIdToBasic(expenseDetailEntity.getUserId(), expenseDetailEntity.getEnterpriseId());
        ResultDto resultStrategyClassifyDto = strategyService.classifyFindById(Long.parseLong(expenseDetailEntity.getExpenseType()));
        CheckInsertDto checkInsertDto = new CheckInsertDto();
        if(resultStrategyBasicDto.getData()!=null && resultStrategyClassifyDto.getData()!=null)
        {
            JSONObject jsonBasicObject = (JSONObject) JSONObject.toJSON(resultStrategyBasicDto.getData());
            StrategyBasicEntityDto strategyBasicEntityDto = JSONObject.toJavaObject(jsonBasicObject,StrategyBasicEntityDto.class);

            JSONObject jsonClassifyObject = (JSONObject) JSONObject.toJSON(resultStrategyClassifyDto.getData());
            StrategyClassifyEntityDto strategyClassifyEntityDto = JSONObject.toJavaObject(jsonClassifyObject,StrategyClassifyEntityDto.class);


            checkInsertDto.setIsSubmit(strategyBasicEntityDto.getIsSubmit());
            checkInsertDto.setMessage("0000");

            if(!strategyBasicEntityDto.getCurrency().equalsIgnoreCase(expenseDetailEntity.getMoneyType()))
            {
                checkInsertDto.setMessage("-0001");
                return ResultDto.success(checkInsertDto); //货币类型与策略设置的货币类型不匹配
            }
            else{
                if(strategyClassifyEntityDto.getLimitType().equals("1") && expenseDetailEntity.getMoney() > strategyClassifyEntityDto.getMoney())
                {
                    checkInsertDto.setMessage("-0002"); //超出金额设置
                    return ResultDto.success(checkInsertDto);
                }
            }
        }
        return ResultDto.success(checkInsertDto);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody ExpenseDetailEntity expenseDetailEntity)
    {
        expenseDetailService.insert(expenseDetailEntity);
        return ResultDto.success();
    }
}
