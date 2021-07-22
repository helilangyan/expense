package bht.expense.user.bank.controller;

import bht.expense.user.bank.entity.BankEntity;
import bht.expense.user.bank.service.BankService;
import bht.expense.user.common.PageListDto;
import bht.expense.user.common.ResultDto;
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
 * @date 2021/4/7 13:58
 */

@Api(tags = "银行卡")
@RestController
@RequestMapping("bank")
public class BankController {

    @Autowired
    BankService bankService;

    @ApiOperation("银行卡列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit , Long userId)
    {
        Map<String,String> map = new HashMap();
        map.put("userId",userId+"");

        PageListDto<BankEntity> pageListData = new PageListDto();
        PageInfo pageInfo = bankService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        BankEntity bankEntity = bankService.findById(id);
        return ResultDto.success(bankEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody BankEntity bankEntity) {
        bankEntity = bankService.insert(bankEntity);
        return ResultDto.success(bankEntity);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        bankService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        bankService.dels(id);
        return ResultDto.success();
    }
}
