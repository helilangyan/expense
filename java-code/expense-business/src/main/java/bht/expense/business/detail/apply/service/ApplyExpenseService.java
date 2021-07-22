package bht.expense.business.detail.apply.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.caller.ApplyExpenseCaller;
import bht.expense.business.detail.apply.dto.ApplyExpenseEntityDto;
import bht.expense.business.detail.apply.dto.ApplyExpenseInsertDto;
import bht.expense.business.detail.apply.dto.ApplyTravelInsertDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/22 16:38
 */
@Service
public class ApplyExpenseService {

    @Autowired
    ApplyExpenseCaller applyExpenseCaller;

    public ResultDto findByUserIdAndEnterpriseId(Long userId, Long enterpriseId , int page, int limit)
    {
        return applyExpenseCaller.findByUserIdAndEnterpriseId(userId, enterpriseId, page, limit);
    }


    public ResultDto insert(ApplyExpenseInsertDto applyExpenseInsertDto)
    {
        return applyExpenseCaller.insert(applyExpenseInsertDto);
    }


    public ResultDto delete(Long id)
    {
        return applyExpenseCaller.delete(id);
    }


    public ResultDto findById(Long id)
    {
        return applyExpenseCaller.findById(id);
    }

    public ResultDto start(ApplyExpenseInsertDto applyExpenseInsertDto, String nextUserId, String[] copyUserIds)
    {
        return applyExpenseCaller.start(applyExpenseInsertDto,nextUserId,copyUserIds);
    }
}
