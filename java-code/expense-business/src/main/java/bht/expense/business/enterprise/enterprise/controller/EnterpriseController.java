package bht.expense.business.enterprise.enterprise.controller;


import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.enterprise.dto.EnterpriseEntityDto;
import bht.expense.business.enterprise.enterprise.service.EnterpriseService;
import bht.expense.business.security.jwt.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/9 16:43
 */
@Api(tags = "企业基本信息")
@RestController
@RequestMapping("enterprise")
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return enterpriseService.findById(id);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody EnterpriseEntityDto enterpriseEntityDto,@RequestHeader("Authorization") String authorization) {
        String userId = jwtTokenUtil.getUsernameFromToken(authorization);
        enterpriseEntityDto.setUserId(Long.parseLong(userId));

        return enterpriseService.insert(enterpriseEntityDto);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        return  enterpriseService.delete(id);
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        return enterpriseService.deletes(id);
    }

    @ApiOperation("我创建的企业列表")
    @GetMapping("/my-enterprise")
    public ResultDto myEnterprise(@RequestHeader("Authorization") String authorization)
    {
        String userId = jwtTokenUtil.getUsernameFromToken(authorization);
        return enterpriseService.myEnterprise(Long.parseLong(userId));
    }

    @ApiOperation("验证唯一税号,返回值如果有数值说明重复")
    @GetMapping("/check-tax-code/{taxCode}")
    public ResultDto checkTaxCode(@PathVariable String taxCode)
    {
        return enterpriseService.checkTaxCode(taxCode);
    }

    @ApiOperation("审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "0=未开通 ， 1=已开通", required = true),
            @ApiImplicitParam(name = "id", value = "ID", required = true)
    })
    @PutMapping("/approve")
    public ResultDto approve(String status , Long id)
    {
        return enterpriseService.approve(status,id);
    }
}
