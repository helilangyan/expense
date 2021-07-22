package bht.expense.business.detail.apply.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.dto.ApplyBorrowEntityDto;
import bht.expense.business.detail.apply.service.ApplyBorrowService;
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
 * @date 2021/4/22 16:44
 */
@Api(tags = "借款申请")
@RestController
@RequestMapping("detail/apply-borrow")
public class ApplyBorrowController {

    @Autowired
    ApplyBorrowService applyBorrowService;

    @ApiOperation("列表，我填写的借款申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/list")
    public ResultDto findByUserIdAndEnterpriseId(@RequestParam("enterpriseId") Long enterpriseId , @RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return applyBorrowService.findByUserIdAndEnterpriseId(Long.parseLong(userEntity.getId()), enterpriseId, page, limit);
    }


    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody ApplyBorrowEntityDto applyBorrowEntity)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        applyBorrowEntity.setUserId(Long.parseLong(userEntity.getId()));
        return applyBorrowService.insert(applyBorrowEntity);
    }

    @ApiOperation("保存，并启动流程")
    @PostMapping("/start")
    public ResultDto start(@RequestBody ApplyBorrowEntityDto applyBorrowEntity, @RequestParam("nextUserId")String nextUserId,@RequestParam("copyUserIds") String[] copyUserIds)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        applyBorrowEntity.setUserId(Long.parseLong(userEntity.getId()));
        return applyBorrowService.start(applyBorrowEntity,nextUserId,copyUserIds);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        return applyBorrowService.delete(id);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return applyBorrowService.findById(id);
    }
}
