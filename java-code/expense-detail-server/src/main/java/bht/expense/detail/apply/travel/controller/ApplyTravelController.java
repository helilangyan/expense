package bht.expense.detail.apply.travel.controller;

import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.travel.dto.ApplyTravelInsertDto;
import bht.expense.detail.apply.travel.entity.ApplyTravelDetailEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelUserEntity;
import bht.expense.detail.apply.travel.service.ApplyTravelService;
import bht.expense.detail.common.PageListDto;
import bht.expense.detail.common.ResultDto;
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
 * @date 2021/4/22 14:26
 */
@Api(tags = "出差申请")
@RestController
@RequestMapping("apply-travel")
public class ApplyTravelController {

    @Autowired
    ApplyTravelService applyTravelService;

    @ApiOperation("列表，我填写的出差申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/list")
    public ResultDto findByUserIdAndEnterpriseId(Long userId, Long enterpriseId , int page, int limit)
    {
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("enterpriseId",enterpriseId);

        PageListDto<ApplyTravelEntity> pageListData = new PageListDto();
        PageInfo pageInfo = applyTravelService.findByUserIdAndEnterpriseId(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody ApplyTravelInsertDto applyTravelInsertDto)
    {
        applyTravelService.insert(applyTravelInsertDto.getApplyTravelEntity(),applyTravelInsertDto.getDetailList(),applyTravelInsertDto.getUserList());
        return ResultDto.success();
    }

    @ApiOperation("保存，并启动流程")
    @PostMapping("/start")
    public ResultDto start(@RequestBody ApplyTravelInsertDto applyTravelInsertDto, @RequestParam("nextUserId")String nextUserId , @RequestParam("copyUserIds") String[] copyUserIds)
    {
        applyTravelService.start(applyTravelInsertDto.getApplyTravelEntity(),applyTravelInsertDto.getDetailList(),applyTravelInsertDto.getUserList(),nextUserId,copyUserIds);
        return ResultDto.success();
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        applyTravelService.delete(id);
        return ResultDto.success();
    }

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return ResultDto.success(applyTravelService.findById(id));
    }

}
