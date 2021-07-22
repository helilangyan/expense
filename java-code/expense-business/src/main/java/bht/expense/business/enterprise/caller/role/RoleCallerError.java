package bht.expense.business.enterprise.caller.role;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.role.dto.RoleEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/14 15:29
 */
@Slf4j
public class RoleCallerError implements RoleCaller{
    @Override
    public ResultDto list(int page, int limit, Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /role/list error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-enterprise-server: /role/findById error");
        return null;
    }

    @Override
    public ResultDto insert(RoleEntityDto roleEntity) {
        log.error("invoke expense-enterprise-server: /role/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /role/delete error");
        return null;
    }

    @Override
    public ResultDto deletes(Long[] id) {
        log.error("invoke expense-enterprise-server: /role/deletes error");
        return null;
    }


}
