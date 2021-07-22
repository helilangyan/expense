package bht.expense.business.enterprise.position.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.position.dto.PositionEntityDto;
import bht.expense.business.enterprise.position.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 姚轶文
 * @date 2021/4/16 14:58
 */
@Api(tags = "职位设置")
@RestController
@RequestMapping("enterprise/position")
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
        return positionService.findByEnterpriseId(page, limit, enterpriseId);
    }

    @ApiOperation("新增、修改")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody PositionEntityDto positionEntityDto)
    {
        return positionService.insert(positionEntityDto);
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        return positionService.delete(id);
    }

    @ApiOperation("根据ID批量删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/deletes/{id}")
    public ResultDto deletes(@PathVariable Long[] id)
    {
        return positionService.deletes(id);
    }
}
