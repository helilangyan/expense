package bht.expense.detail.apply.borrow.controller;

import bht.expense.detail.apply.borrow.dto.ApplyBorrowInfoDto;
import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.borrow.service.ApplyBorrowService;
import bht.expense.detail.apply.expense.entity.ApplyExpenseEntity;
import bht.expense.detail.common.PageListDto;
import bht.expense.detail.common.ResultDto;
import bht.expense.detail.detail.entity.ExpenseDetailEntity;
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
 * @date 2021/4/22 10:54
 */
@Api(tags = "借款申请")
@RestController
@RequestMapping("apply-borrow")
public class ApplyBorrowController {

    @Autowired
    ApplyBorrowService applyBorrowService;

    @ApiOperation("列表，我填写的借款申请")
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

        PageListDto<ApplyBorrowInfoDto> pageListData = new PageListDto();
        PageInfo pageInfo = applyBorrowService.findByUserIdAndEnterpriseId(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody ApplyBorrowEntity applyBorrowEntity )
    {
        applyBorrowService.insert(applyBorrowEntity);
        return ResultDto.success();
    }

    @ApiOperation("保存，并启动流程")
    @PostMapping("/start")
    public ResultDto start(@RequestBody ApplyBorrowEntity applyBorrowEntity, @RequestParam("nextUserId")String nextUserId , @RequestParam("copyUserIds") String[] copyUserIds)
    {
        applyBorrowService.start(applyBorrowEntity,nextUserId,copyUserIds);
        return ResultDto.success();
    }



    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        applyBorrowService.delete(id);
        return ResultDto.success();
    }

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return ResultDto.success(applyBorrowService.findById(id));
    }
}
