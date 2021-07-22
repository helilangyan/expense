package bht.expense.enterprise.strategy.detail.classify.controller;

import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.strategy.detail.classify.entity.StrategyClassifyEntity;
import bht.expense.enterprise.strategy.detail.classify.service.StrategyClassifyService;
import bht.expense.enterprise.strategy.detail.label.entity.StrategyLabelEntity;
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
 * @date 2021/7/14 10:53
 */
@Api(tags = "费用策略--费用分类")
@RestController
@RequestMapping("strategy-classify")
public class StrategyClassifyController {

    @Autowired
    StrategyClassifyService strategyClassifyService;

    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "strategyId", value = "策略ID", required = true)
    })
    @PostMapping("/list")
    public ResultDto findByStrategyId(int page, int limit , Long strategyId)
    {
        Map<String,Object> map = new HashMap();
        map.put("strategyId",strategyId);

        PageListDto<StrategyLabelEntity> pageListData = new PageListDto();
        PageInfo pageInfo = strategyClassifyService.findByStrategyId(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        StrategyClassifyEntity strategyClassifyEntity = strategyClassifyService.findById(id);
        return ResultDto.success(strategyClassifyEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody StrategyClassifyEntity strategyClassifyEntity) {
        strategyClassifyEntity = strategyClassifyService.insert(strategyClassifyEntity);
        return ResultDto.success(strategyClassifyEntity);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        strategyClassifyService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        strategyClassifyService.dels(id);
        return ResultDto.success();
    }

    @ApiOperation("修改状态， 0禁用  1启用，默认启用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true),
            @ApiImplicitParam(name = "classifyStatus", value = "状态 0禁用  1启用", required = true)
    })
    @PostMapping("/update-status")
    public ResultDto updateStatus(Long id , String classifyStatus)
    {
        strategyClassifyService.updateStatus(id, classifyStatus);
        return ResultDto.success();
    }
}
