package bht.expense.business.enterprise.strategy.detail.label.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.strategy.detail.label.dto.StrategyLabelEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bht.expense.business.enterprise.caller.strategy.detail.label.*;
/**
 * @author 姚轶文
 * @date 2021/6/24 16:35
 */
@Service
public class StrategyLabelService {

    @Autowired
    StrategyLabelCaller strategyLabelCaller;

    public ResultDto list(int page, int limit ,  Long enterpriseId)
    {
        return strategyLabelCaller.list(page, limit, enterpriseId);
    }


    public ResultDto findById( Long id)
    {
        return strategyLabelCaller.findById(id);
    }

    public ResultDto insert( StrategyLabelEntityDto strategyLabelEntityDto)
    {
        return strategyLabelCaller.insert(strategyLabelEntityDto);
    }


    public ResultDto delete(Long id)
    {
        return strategyLabelCaller.delete(id);
    }

    public ResultDto deletes(Long[] id)
    {
        return strategyLabelCaller.deletes(id);
    }

    public ResultDto updateStatus(Long id , String status)
    {
        return strategyLabelCaller.updateStatus(id, status);
    }
}
