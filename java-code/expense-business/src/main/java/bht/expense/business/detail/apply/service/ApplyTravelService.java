package bht.expense.business.detail.apply.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.caller.ApplyTravelCaller;
import bht.expense.business.detail.apply.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/22 16:41
 */
@Service
public class ApplyTravelService {

    @Autowired
    ApplyTravelCaller applyTravelCaller;

    public ResultDto findByUserIdAndEnterpriseId(Long userId, Long enterpriseId , int page, int limit)
    {
        return applyTravelCaller.findByUserIdAndEnterpriseId(userId, enterpriseId, page, limit);
    }


    public ResultDto insert(ApplyTravelInsertDto applyTravelInsertDto)
    {
        return applyTravelCaller.insert(applyTravelInsertDto);
    }


    public ResultDto delete(Long id)
    {
        return applyTravelCaller.delete(id);
    }


    public ResultDto findById(Long id)
    {
        return applyTravelCaller.findById(id);
    }


    public ResultDto start(ApplyTravelInsertDto applyTravelInsertDto, String nextUserId, String[] copyUserIds)
    {
        return applyTravelCaller.start(applyTravelInsertDto,nextUserId,copyUserIds);
    }
}
