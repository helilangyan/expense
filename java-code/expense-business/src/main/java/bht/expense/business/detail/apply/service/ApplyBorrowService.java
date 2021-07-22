package bht.expense.business.detail.apply.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.caller.ApplyBorrowCaller;
import bht.expense.business.detail.apply.dto.ApplyBorrowEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/22 16:36
 */
@Service
public class ApplyBorrowService {

    @Autowired
    ApplyBorrowCaller applyBorrowCaller;

    public ResultDto findByUserIdAndEnterpriseId(Long userId, Long enterpriseId , int page, int limit)
    {
        return applyBorrowCaller.findByUserIdAndEnterpriseId(userId, enterpriseId, page, limit);
    }


    public ResultDto insert(ApplyBorrowEntityDto applyBorrowEntity)
    {
        return applyBorrowCaller.insert(applyBorrowEntity);
    }


    public ResultDto delete(Long id)
    {
        return applyBorrowCaller.delete(id);
    }



    public ResultDto findById(Long id)
    {
        return applyBorrowCaller.findById(id);
    }

    public ResultDto start(ApplyBorrowEntityDto applyBorrowEntity,String nextUserId,String[] copyUserIds)
    {
        return applyBorrowCaller.start(applyBorrowEntity,nextUserId,copyUserIds);
    }
}
