package bht.expense.business.enterprise.caller.enterprise;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.enterprise.dto.ApproveDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/13 10:50
 */
@FeignClient(value = "expense-enterprise-server",fallback = EnterpriseUserApplyCallerError.class, contextId = "EnterpriseUserApplyCaller")
public interface EnterpriseUserApplyCaller {

    @PostMapping("/enterprise-user-apply/enterprise-list")
    ResultDto findByEnterpriseId(@RequestParam("enterpriseId") Long enterpriseId, @RequestParam("page") int page, @RequestParam("limit") int limit);

    @PostMapping("/enterprise-user-apply/user-list")
    ResultDto findByUserId(@RequestParam("userId") Long userId);

    @PostMapping("/enterprise-user-apply/approve")
    ResultDto approve(@RequestBody ApproveDto approveDto);

    @PostMapping("/enterprise-user-apply/apply")
    ResultDto apply(@RequestParam("userId") Long userId , @RequestParam("InvitationCode") String InvitationCode);


}
