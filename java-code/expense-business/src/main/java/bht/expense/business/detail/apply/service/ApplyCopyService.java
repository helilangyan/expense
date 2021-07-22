package bht.expense.business.detail.apply.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.caller.ApplyCopyCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/6/22 14:20
 */
@Service
public class ApplyCopyService {

    @Autowired
    ApplyCopyCaller applyCopyCaller;

    public ResultDto toMyCopy(Long userId , int page,  int limit)
    {
        return applyCopyCaller.toMyCopy(userId, page, limit);
    }
}
