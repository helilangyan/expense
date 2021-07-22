package bht.expense.enterprise.enterprise.controller;

import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultCode;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.enterprise.dto.ApproveDto;
import bht.expense.enterprise.enterprise.dto.EnterpriseUserApplyDto;
import bht.expense.enterprise.enterprise.entity.EnterpriseEntity;
import bht.expense.enterprise.enterprise.entity.EnterpriseUserApplyEntity;
import bht.expense.enterprise.enterprise.service.EnterpriseService;
import bht.expense.enterprise.enterprise.service.EnterpriseUserApplyService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation("根据企业ID，查看本企业的全部用户申请记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/enterprise-list")
    public ResultDto findByEnterpriseId(Long enterpriseId, int page, int limit)
    {
        Map<String,String> map = new HashMap();

        PageListDto<EnterpriseEntity> pageListData = new PageListDto();
        PageInfo pageInfo = enterpriseUserApplyService.findByEnterpriseId(enterpriseId, page, limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据用户ID，查看本用户申请记录")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    @PostMapping("/user-list")
    public ResultDto findByUserId(Long userId)
    {
        List<EnterpriseUserApplyDto> list = enterpriseUserApplyService.findByUserId(userId);
        return ResultDto.success(list);
    }

    @ApiOperation("企业审批")
    @PostMapping("/approve")
    public ResultDto approve(@RequestBody ApproveDto approveDto)
    {
        enterpriseUserApplyService.approve(approveDto);
        return ResultDto.success();
    }

    @ApiOperation("用户申请入驻企业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "InvitationCode", value = "推荐码", required = true)
    })
    @PostMapping("/apply")
    public ResultDto apply(Long userId , String InvitationCode)
    {
        Long enterpriseId = enterpriseService.findByInvitationCode(InvitationCode);
        if(enterpriseId==null || enterpriseId==0)
        {
            return ResultDto.failure(ResultCode.FAILURE,"推荐码不存在");
        }
        EnterpriseUserApplyEntity enterpriseUserApplyEntity = new EnterpriseUserApplyEntity();
        enterpriseUserApplyEntity.setEnterpriseId(enterpriseId);
        enterpriseUserApplyEntity.setStatus("0");
        enterpriseUserApplyEntity.setUserId(userId);
        enterpriseUserApplyEntity.setApplyTime(new Date());
        enterpriseUserApplyService.insert(enterpriseUserApplyEntity);
        return ResultDto.success();
    }
}
