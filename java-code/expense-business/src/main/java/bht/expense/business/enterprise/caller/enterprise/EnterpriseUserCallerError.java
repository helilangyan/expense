package bht.expense.business.enterprise.caller.enterprise;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.enterprise.dto.EnterpriseUserEntityDao;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/13 11:01
 */
@Slf4j
public class EnterpriseUserCallerError implements EnterpriseUserCaller{
    @Override
    public ResultDto findByUserId(Long userId) {
        log.error("invoke expense-enterprise-server: /enterprise-user/findByUserId error");
        return null;
    }

    @Override
    public ResultDto findByEnterpriseId(int page, int limit, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /enterprise-user/findByEnterpriseId error");
        return null;
    }

    @Override
    public ResultDto setDefault(Long id, Long userId) {
        log.error("invoke expense-enterprise-server: /enterprise-user/set-default error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-enterprise-server: /enterprise-user/findById error");
        return null;
    }

    @Override
    public ResultDto update(EnterpriseUserEntityDao enterpriseUserEntity) {
        log.error("invoke expense-enterprise-server: /enterprise-user/update error");
        return null;
    }

    @Override
    public ResultDto remove(Long id) {
        log.error("invoke expense-enterprise-server: /enterprise-user/remove error");
        return null;
    }
}
