package bht.expense.business.enterprise.caller.department;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.department.dto.DepartmentEntityDto;
import bht.expense.business.enterprise.department.dto.StrategyUserEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/15 15:56
 */
@Slf4j
public class DepartmentCallerError implements DepartmentCaller{

    @Override
    public ResultDto findByEnterpriseId(Long enterpriseId) {
        log.error("invoke expense-enterprise-server: /department/findByEnterpriseId error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /department/delete error");
        return null;
    }

    @Override
    public ResultDto insert(DepartmentEntityDto departmentEntityDto) {
        log.error("invoke expense-enterprise-server: /department/insert error");
        return null;
    }

    @Override
    public ResultDto updateDepartmentName(String departmentName, Long id) {
        log.error("invoke expense-enterprise-server: /department/updateDepartmentName error");
        return null;
    }

    @Override
    public ResultDto findUsers(Long enterpriseId , Long departmentId) {
        log.error("invoke expense-enterprise-server: /department/findUsers error");
        return null;
    }

    @Override
    public ResultDto inputStrategy(StrategyUserEntityDto strategyUserEntity) {
        log.error("invoke expense-enterprise-server: /department/inputStrategy error");
        return null;
    }
}
