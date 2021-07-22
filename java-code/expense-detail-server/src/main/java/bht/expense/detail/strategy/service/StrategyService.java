package bht.expense.detail.strategy.service;

import bht.expense.detail.common.ResultDto;
import bht.expense.detail.strategy.caller.StrategyCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 姚轶文
 * @date 2021/7/22 10:46
 */
@Service
public class StrategyService {

    @Autowired
    StrategyCaller strategyCaller;

    public ResultDto findByUserIdAndEnterpriseIdToBasic(Long userId , Long enterpriseId)
    {
        return strategyCaller.findByUserIdAndEnterpriseIdToBasic(userId, enterpriseId);
    }

    public ResultDto classifyFindById(Long id)
    {
        return strategyCaller.classifyFindById(id);
    }
}
