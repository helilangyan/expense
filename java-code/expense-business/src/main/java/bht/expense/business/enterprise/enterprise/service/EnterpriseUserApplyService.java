package bht.expense.business.enterprise.enterprise.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.enterprise.EnterpriseUserApplyCaller;
import bht.expense.business.enterprise.enterprise.dto.ApproveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 姚轶文
 * @date 2021/4/13 11:20
 */
@Service
public class EnterpriseUserApplyService {

    @Autowired
    EnterpriseUserApplyCaller enterpriseUserApplyCaller;

    public ResultDto findByEnterpriseId(Long enterpriseId, int page, int limit)
    {
        return enterpriseUserApplyCaller.findByEnterpriseId(enterpriseId, page, limit);
    }

    public ResultDto findByUserId(Long userId)
    {
        return enterpriseUserApplyCaller.findByUserId(userId);
    }

    public ResultDto approve(ApproveDto approveDto)
    {
        return enterpriseUserApplyCaller.approve(approveDto);
    }

    public ResultDto apply(Long userId , String InvitationCode)
    {
        return enterpriseUserApplyCaller.apply(userId, InvitationCode);
    }
}
