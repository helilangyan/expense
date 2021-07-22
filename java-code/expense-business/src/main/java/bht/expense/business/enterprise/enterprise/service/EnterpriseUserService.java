package bht.expense.business.enterprise.enterprise.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.enterprise.EnterpriseUserCaller;
import bht.expense.business.enterprise.enterprise.dto.EnterpriseUserEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/13 11:24
 */
@Service
public class EnterpriseUserService {

    @Autowired
    EnterpriseUserCaller enterpriseUserCaller;

    public ResultDto findByUserId(Long userId)
    {
        return enterpriseUserCaller.findByUserId(userId);
    }

    public ResultDto findByEnterpriseId(int page, int limit, Long enterpriseId)
    {
        return enterpriseUserCaller.findByEnterpriseId(page, limit, enterpriseId);
    }

    public ResultDto setDefault(Long id , Long userId)
    {
        return enterpriseUserCaller.setDefault(id, userId);
    }

    public ResultDto findById(Long id)
    {
        return enterpriseUserCaller.findById(id);
    }


    public ResultDto update(EnterpriseUserEntityDao enterpriseUserEntity)
    {
        return enterpriseUserCaller.update(enterpriseUserEntity);
    }

    public ResultDto remove(Long id)
    {
        return enterpriseUserCaller.remove(id);
    }
}
