package bht.expense.business.enterprise.enterprise.controller;


import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.enterprise.dto.EnterpriseUserEntityDao;
import bht.expense.business.enterprise.enterprise.service.EnterpriseUserService;
import bht.expense.business.security.jwt.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/12 15:18
 */
@Api(tags = "企业用户关联")
@RestController
@RequestMapping("enterprise-user")
public class EnterpriseUserController {

    @Autowired
    EnterpriseUserService enterpriseUserService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation("根据用户ID，查看本用户入驻的企业")
    @PostMapping("/user-list")
    public ResultDto findByUserId( @RequestHeader("Authorization") String authorization)
    {
        String userId = jwtTokenUtil.getUsernameFromToken(authorization);
        return enterpriseUserService.findByUserId(Long.parseLong(userId));
    }

    @ApiOperation("根据企业ID，查看本企业的全部用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/enterprise-list")
    public ResultDto findByEnterpriseId(int page, int limit, Long enterpriseId)
    {
        return enterpriseUserService.findByEnterpriseId(page, limit, enterpriseId);
    }

    @ApiOperation("用户设置默认企业")
    @ApiImplicitParam(name = "id", value = "本条数据ID", required = true)
    @PostMapping("/enterprise-user/set-default")
    public ResultDto setDefault(@RequestParam("id") Long id , @RequestHeader("Authorization") String authorization)
    {
        String userId = jwtTokenUtil.getUsernameFromToken(authorization);
        return enterpriseUserService.setDefault(id, Long.parseLong(userId));
    }

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/enterprise-user/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return ResultDto.success(enterpriseUserService.findById(id));
    }

    @ApiOperation("修改员工信息")
    @PostMapping("/enterprise-user/update")
    public ResultDto update(@RequestBody EnterpriseUserEntityDao enterpriseUserEntity)
    {
        enterpriseUserService.update(enterpriseUserEntity);
        return ResultDto.success(enterpriseUserEntity);
    }

    @ApiOperation("根据ID删除,移除企业")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto remove(@PathVariable Long id)
    {
        enterpriseUserService.remove(id);
        return ResultDto.success();
    }
}
