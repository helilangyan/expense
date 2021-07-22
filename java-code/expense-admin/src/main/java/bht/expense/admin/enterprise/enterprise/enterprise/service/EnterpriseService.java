package bht.expense.admin.enterprise.enterprise.enterprise.service;

import bht.expense.admin.common.ResultDto;
import bht.expense.admin.enterprise.caller.enterprise.EnterpriseCaller;
import bht.expense.admin.enterprise.enterprise.enterprise.dto.EnterpriseEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/13 11:11
 */
@Service
public class EnterpriseService {

    @Autowired
    EnterpriseCaller enterpriseCaller;

    public ResultDto list(int page, int limit)
    {
        return enterpriseCaller.list(page, limit);
    }

    public ResultDto findById(Long id)
    {
        return enterpriseCaller.findById(id);
    }

    public ResultDto insert(EnterpriseEntityDto enterpriseEntityDto)
    {
        return enterpriseCaller.insert(enterpriseEntityDto);
    }

    public ResultDto delete(Long id)
    {
        return enterpriseCaller.delete(id);
    }

    public ResultDto deletes(Long[] id)
    {
        return enterpriseCaller.deletes(id);
    }

    public ResultDto myEnterprise(Long userId)
    {
        return enterpriseCaller.myEnterprise(userId);
    }

    public ResultDto checkTaxCode(String taxCode)
    {
        return enterpriseCaller.checkTaxCode(taxCode);
    }

    public ResultDto approve(String status , Long id)
    {
        return enterpriseCaller.approve(status, id);
    }
}
