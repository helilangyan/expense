package bht.expense.business.detail.approval.servuce;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.approval.caller.ApprovalCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/6/17 14:04
 */
@Service
public class ApprovalService {

    @Autowired
    ApprovalCaller approvalCaller;

    public ResultDto list(String key , String userId, int firstResult,int maxResults)
    {
        ResultDto resultDto = approvalCaller.list(key, userId, firstResult, maxResults);
        return resultDto;
    }
}
