package bht.expense.business.enterprise.enterprise.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.enterprise.dto.ApproveDto;
import bht.expense.business.enterprise.enterprise.service.EnterpriseService;
import bht.expense.business.enterprise.enterprise.service.EnterpriseUserApplyService;
import bht.expense.business.security.jwt.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/12 14:06
 */
@Api(tags = "企业用户关联申请")
@RestController
@RequestMapping("enterprise-user-apply")
public class EnterpriseUserApplyController {

    @Autowired
    EnterpriseUserApplyService enterpriseUserApplyService;

    @Autowired
    EnterpriseService enterpriseService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation("根据企业ID，查看本企业的全部用户申请记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/enterprise-list")
    public ResultDto findByEnterpriseId(Long enterpriseId, int page, int limit)
    {
        return enterpriseUserApplyService.findByEnterpriseId(enterpriseId, page, limit);
    }


    @ApiOperation("根据用户ID，查看本用户申请记录")
    @PostMapping("/user-list")
    public ResultDto findByUserId(@RequestHeader("Authorization") String authorization)
    {
        String userId = jwtTokenUtil.getUsernameFromToken(authorization);
        return enterpriseUserApplyService.findByUserId(Long.parseLong(userId));
    }

    @ApiOperation("企业审批")
    @PostMapping("/approve")
    public ResultDto approve(@RequestBody ApproveDto approveDto)
    {
        return enterpriseUserApplyService.approve(approveDto);
    }

    @ApiOperation("用户申请入驻企业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "InvitationCode", value = "推荐码", required = true)
    })
    @PostMapping("/apply")
    public ResultDto apply(@RequestHeader("Authorization") String authorization , String InvitationCode)
    {
        String userId = jwtTokenUtil.getUsernameFromToken(authorization);
        return enterpriseUserApplyService.apply(Long.parseLong(userId), InvitationCode);
    }
}
