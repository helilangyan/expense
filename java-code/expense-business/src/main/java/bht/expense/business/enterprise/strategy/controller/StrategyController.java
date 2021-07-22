package bht.expense.business.enterprise.strategy.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.label.dto.StrategyLabelEntityDto;
import bht.expense.business.enterprise.strategy.dto.StrategyEntityDto;
import bht.expense.business.enterprise.strategy.service.StrategyService;
import bht.expense.business.security.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/6/25 15:17
 */
@Api(tags = "费用策略")
@RestController
@RequestMapping("enterprise/strategy")
public class StrategyController {

    @Autowired
    StrategyService strategyService;

    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit , Long enterpriseId)
    {
        return strategyService.list(page, limit, enterpriseId);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return strategyService.findById(id);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody StrategyEntityDto strategyEntityDto) {
        return strategyService.insert(strategyEntityDto);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        return strategyService.delete(id);
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        return strategyService.deletes(id);
    }

    @ApiOperation("查询用户匹配的策略费用分类")
    @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    @PostMapping("/user-classify")
    public ResultDto findUserClassify(Long enterpriseId)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return  strategyService.findUserClassify(Long.parseLong(userEntity.getId()), enterpriseId);
    }

    @ApiOperation("查询用户匹配的标签分类")
    @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    @PostMapping("/user-label")
    public ResultDto findUserLabel(Long enterpriseId)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return  strategyService.findUserLabel(Long.parseLong(userEntity.getId()), enterpriseId);
    }

    @ApiOperation("查询用户匹配的交通工具分类")
    @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    @PostMapping("/user-vehicle")
    public ResultDto findUserVehicle(Long enterpriseId)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return  strategyService.findUserVehicle(Long.parseLong(userEntity.getId()), enterpriseId);
    }
}
