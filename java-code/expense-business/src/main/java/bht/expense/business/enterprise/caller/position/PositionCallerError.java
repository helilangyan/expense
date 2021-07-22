package bht.expense.business.enterprise.caller.position;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.position.dto.PositionEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/16 14:51
 */
@Slf4j
public class PositionCallerError implements PositionCaller{
    @Override
    public ResultDto findByEnterpriseId(int page, int limit, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /position/findByEnterpriseId error");
        return null;
    }

    @Override
    public ResultDto insert(PositionEntityDto positionEntityDto) {
        log.error("invoke expense-enterprise-server: /position/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /position/delete error");
        return null;
    }

    @Override
    public ResultDto deletes(Long[] id) {
        log.error("invoke expense-enterprise-server: /position/deletes error");
        return null;
    }
}
