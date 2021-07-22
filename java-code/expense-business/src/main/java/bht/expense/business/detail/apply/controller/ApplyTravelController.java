package bht.expense.business.detail.apply.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.dto.*;
import bht.expense.business.detail.apply.service.ApplyTravelService;
import bht.expense.business.security.entity.UserEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/22 16:45
 */
@Api(tags = "出差申请")
@RestController
@RequestMapping("detail/apply-travel")
public class ApplyTravelController {

    @Autowired
    ApplyTravelService applyTravelService;

    @ApiOperation("列表，我填写的出差申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/list")
    public ResultDto findByUserIdAndEnterpriseId(Long enterpriseId , int page, int limit)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return applyTravelService.findByUserIdAndEnterpriseId(Long.parseLong(userEntity.getId()), enterpriseId, page, limit);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody ApplyTravelInsertDto applyTravelInsertDto)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        applyTravelInsertDto.getApplyTravelEntity().setUserId(Long.parseLong(userEntity.getId()));
        return applyTravelService.insert(applyTravelInsertDto);
    }

    @ApiOperation("保存，并启动流程")
    @PostMapping("/start")
    public ResultDto start(@RequestBody ApplyTravelInsertDto applyTravelInsertDto, @RequestParam("nextUserId")String nextUserId, @RequestParam("copyUserIds") String[] copyUserIds)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        applyTravelInsertDto.getApplyTravelEntity().setUserId(Long.parseLong(userEntity.getId()));
        return applyTravelService.start(applyTravelInsertDto,nextUserId,copyUserIds);
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        return applyTravelService.delete(id);
    }

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return applyTravelService.findById(id);
    }
}
