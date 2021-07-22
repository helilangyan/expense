package bht.expense.admin.enterprise.caller.enterprise;


import bht.expense.admin.common.ResultDto;
import bht.expense.admin.enterprise.enterprise.enterprise.dto.EnterpriseEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/13 10:28
 */

@Slf4j
public class EnterpriseCallerError implements EnterpriseCaller{

    @Override
    public ResultDto list(int page, int limit) {
        log.error("invoke expense-enterprise-server: /enterprise/list error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-enterprise-server: /enterprise/findById error");
        return null;
    }

    @Override
    public ResultDto insert(EnterpriseEntityDto enterpriseEntityDto) {
        log.error("invoke expense-enterprise-server: /enterprise/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /enterprise/delete error");
        return null;
    }

    @Override
    public ResultDto deletes(Long[] id) {
        log.error("invoke expense-enterprise-server: /enterprise/deletes error");
        return null;
    }

    @Override
    public ResultDto myEnterprise(Long userId) {
        log.error("invoke expense-enterprise-server: /enterprise/myEnterprise error");
        return null;
    }

    @Override
    public ResultDto checkTaxCode(String taxCode) {
        log.error("invoke expense-enterprise-server: /enterprise/checkTaxCode error");
        return null;
    }

    @Override
    public ResultDto approve(String status, Long id) {
        log.error("invoke expense-enterprise-server: /enterprise/approve error");
        return null;
    }
}
