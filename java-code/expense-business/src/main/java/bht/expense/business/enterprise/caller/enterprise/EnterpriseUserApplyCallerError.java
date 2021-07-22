package bht.expense.business.enterprise.caller.enterprise;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.enterprise.dto.ApproveDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 姚轶文
 * @date 2021/4/13 10:50
 */
@Slf4j
public class EnterpriseUserApplyCallerError implements EnterpriseUserApplyCaller{
    @Override
    public ResultDto findByEnterpriseId(Long enterpriseId, int page, int limit) {
        log.error("invoke expense-enterprise-server: /enterprise-user-apply/enterprise-list error");
        return null;
    }

    @Override
    public ResultDto findByUserId(Long userId) {
        log.error("invoke expense-enterprise-server: /enterprise-user-apply/user-list error");
        return null;
    }

    @Override
    public ResultDto approve(@RequestBody ApproveDto approveDto) {
        log.error("invoke expense-enterprise-server: /enterprise-user-apply/approve error");
        return null;
    }

    @Override
    public ResultDto apply(Long userId, String InvitationCode) {
        log.error("invoke expense-enterprise-server: /enterprise-user-apply/apply error");
        return null;
    }
}
