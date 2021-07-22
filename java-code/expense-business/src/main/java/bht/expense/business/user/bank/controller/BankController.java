package bht.expense.business.user.bank.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.security.jwt.JwtTokenUtil;
import bht.expense.business.user.bank.dto.BankEntityDto;
import bht.expense.business.user.bank.service.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/7 14:23
 */

@Api(tags = "银行卡设置")
@RestController
@RequestMapping("user/bank")
public class BankController {

    @Autowired
    BankService bankService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true)
    })
    @PostMapping({"/list"})
    public ResultDto list(int page, int limit , @RequestHeader("Authorization") String authorization) {
        String userId = jwtTokenUtil.getUsernameFromToken(authorization);

        return bankService.list(page,limit,Long.parseLong(userId));
    }

    @ApiOperation("新增或修改")
    @PostMapping({"/insert"})
    public ResultDto insert(BankEntityDto bankEntityDto, @RequestHeader("Authorization") String authorization)
    {
        String userId = jwtTokenUtil.getUsernameFromToken(authorization);
        bankEntityDto.setUserId(Long.parseLong(userId));
        return bankService.insert(bankEntityDto);
    }

    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return bankService.findById(id);
    }

    @ApiOperation("根据ID删除")
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id) {

        return bankService.delete(id);
    }
}
