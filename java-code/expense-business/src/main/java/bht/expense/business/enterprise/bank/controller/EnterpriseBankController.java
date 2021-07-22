package bht.expense.business.enterprise.bank.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.bank.dto.EnterpriseBankEntityDto;
import bht.expense.business.enterprise.bank.service.EnterpriseBankService;
import bht.expense.business.security.jwt.JwtTokenUtil;

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
@RequestMapping("enterprise/bank")
public class EnterpriseBankController {

    @Autowired
    EnterpriseBankService bankService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping({"/list"})
    public ResultDto list(int page, int limit , Long enterpriseId) {
        return bankService.list(page,limit,enterpriseId);
    }

    @ApiOperation("新增或修改")
    @PostMapping({"/insert"})
    public ResultDto insert(EnterpriseBankEntityDto bankEntityDto)
    {
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
