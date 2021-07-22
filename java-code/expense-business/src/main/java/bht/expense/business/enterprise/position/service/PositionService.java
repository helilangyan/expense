package bht.expense.business.enterprise.position.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.position.PositionCaller;
import bht.expense.business.enterprise.position.dto.PositionEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 姚轶文
 * @date 2021/4/16 14:55
 */

@Service
public class PositionService {

    @Autowired
    PositionCaller positionCaller;

    public ResultDto findByEnterpriseId(int page, int limit ,Long enterpriseId)
    {
        return positionCaller.findByEnterpriseId(page, limit, enterpriseId);
    }


    public ResultDto insert(PositionEntityDto positionEntityDto)
    {
        return positionCaller.insert(positionEntityDto);
    }


    public ResultDto delete(Long id)
    {
        return positionCaller.delete(id);
    }


    public ResultDto deletes(Long[] id)
    {
        return positionCaller.deletes(id);
    }
}
