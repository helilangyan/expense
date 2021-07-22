package bht.expense.business.enterprise.strategy.detail.classify.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.strategy.detail.classify.StrategyClassifyCaller;
import bht.expense.business.enterprise.strategy.detail.classify.dto.StrategyClassifyEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/7/14 11:17
 */
@Service
public class StrategyClassifyService {

    @Autowired
    StrategyClassifyCaller strategyClassifyCaller;

    public ResultDto list(int page, int limit , Long enterpriseId)
    {
        return strategyClassifyCaller.list(page, limit, enterpriseId);
    }


    public ResultDto findById( Long id)
    {
        return strategyClassifyCaller.findById(id);
    }

    public ResultDto insert( StrategyClassifyEntityDto strategyClassifyEntityDto)
    {
        return strategyClassifyCaller.insert(strategyClassifyEntityDto);
    }


    public ResultDto delete(Long id)
    {
        return strategyClassifyCaller.delete(id);
    }

    public ResultDto deletes(Long[] id)
    {
        return strategyClassifyCaller.deletes(id);
    }

    public ResultDto updateStatus(Long id , String classifyStatus)
    {
        return strategyClassifyCaller.updateStatus(id, classifyStatus);
    }
}
