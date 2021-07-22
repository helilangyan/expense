package bht.expense.enterprise.position.controller;

import bht.expense.enterprise.bank.entity.BankEntity;
import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.department.entity.DepartmentEntity;
import bht.expense.enterprise.position.entity.PositionEntity;
import bht.expense.enterprise.position.service.PositionService;
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
 * @date 2021/4/16 14:30
 */
@Api(tags = "职位设置")
@RestController
@RequestMapping("position")
public class PositionController {

    @Autowired
    PositionService positionService;

    @ApiOperation("列表，本企业设置的职位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/findByEnterpriseId")
    public ResultDto findByEnterpriseId(int page, int limit , Long enterpriseId)
    {
        Map<String,Object> map = new HashMap();
        map.put("enterpriseId",enterpriseId);

        PageListDto<BankEntity> pageListData = new PageListDto();
        PageInfo pageInfo = positionService.findByEnterpriseId(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }

    @ApiOperation("新增、修改")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody PositionEntity positionEntity)
    {
        positionService.insert(positionEntity);
        return ResultDto.success();
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        positionService.delete(id);
        return ResultDto.success();
    }

    @ApiOperation("根据ID批量删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/deletes/{id}")
    public ResultDto deletes(@PathVariable Long[] id)
    {
        positionService.deletes(id);
        return ResultDto.success();
    }
}
