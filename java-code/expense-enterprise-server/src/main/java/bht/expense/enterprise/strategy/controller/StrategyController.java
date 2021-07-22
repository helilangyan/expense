package bht.expense.enterprise.strategy.controller;

import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.strategy.detail.basic.entity.StrategyBasicEntity;
import bht.expense.enterprise.strategy.detail.classify.entity.StrategyClassifyEntity;
import bht.expense.enterprise.strategy.detail.label.entity.StrategyLabelEntity;
import bht.expense.enterprise.strategy.detail.vehicle.entity.StrategyVehicleEntity;
import bht.expense.enterprise.strategy.entity.StrategyEntity;
import bht.expense.enterprise.strategy.service.StrategyService;
import bht.expense.enterprise.strategy.service.StrategyUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/6/25 15:02
 */
@Api(tags = "费用策略")
@RestController
@RequestMapping("strategy")
public class StrategyController {

    @Autowired
    StrategyService strategyService;

    @Autowired
    StrategyUserService strategyUserService;

    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/list")
    public ResultDto findByEnterpriseId(int page, int limit , Long enterpriseId)
    {
        Map<String,Object> map = new HashMap();
        map.put("enterpriseId",enterpriseId);

        PageListDto<StrategyEntity> pageListData = new PageListDto();
        PageInfo pageInfo = strategyService.findByEnterpriseId(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        StrategyEntity strategyEntity = strategyService.findById(id);
        return ResultDto.success(strategyEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody StrategyEntity strategyEntity) {
        strategyEntity = strategyService.insert(strategyEntity);
        return ResultDto.success(strategyEntity);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        strategyService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        strategyService.dels(id);
        return ResultDto.success();
    }

    @ApiOperation("查询用户匹配的策略费用分类")
    @PostMapping("/user-classify")
    public ResultDto findUserClassify(Long userId , Long enterpriseId)
    {
        List<StrategyClassifyEntity> list =  strategyUserService.findByUserIdAndEnterpriseIdToClassify(userId, enterpriseId);
        return ResultDto.success(list);
    }

    @ApiOperation("查询用户匹配的标签分类")
    @PostMapping("/user-label")
    public ResultDto findUserLabel(Long userId , Long enterpriseId)
    {
        List<StrategyLabelEntity> list =  strategyUserService.findByUserIdAndEnterpriseIdToLabel(userId, enterpriseId);
        return ResultDto.success(list);
    }

    @ApiOperation("查询用户匹配的交通工具分类")
    @PostMapping("/user-vehicle")
    public ResultDto findUserVehicle(Long userId , Long enterpriseId)
    {
        List<StrategyVehicleEntity> list =  strategyUserService.findByUserIdAndEnterpriseIdToVehicle(userId, enterpriseId);
        return ResultDto.success(list);
    }

    @ApiOperation("查询用户匹配的策略基础信息")
    @PostMapping("/user-basic")
    public ResultDto findByUserIdAndEnterpriseIdToBasic(Long userId , Long enterpriseId)
    {
        return ResultDto.success(strategyUserService.findByUserIdAndEnterpriseIdToBasic(userId, enterpriseId));
    }

}
