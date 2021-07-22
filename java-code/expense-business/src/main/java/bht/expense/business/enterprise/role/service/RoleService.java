package bht.expense.business.enterprise.role.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.role.RoleCaller;
import bht.expense.business.enterprise.role.dto.RoleEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/4/14 15:37
 */
@Service
public class RoleService {

    @Autowired
    RoleCaller roleCaller;

    public ResultDto list(int page, int limit , Long enterpriseId)
    {
        return roleCaller.list(page, limit, enterpriseId);
    }

    public ResultDto findById(Long id)
    {
        return roleCaller.findById(id);
    }

    public ResultDto insert(RoleEntityDto roleEntity)
    {
        return roleCaller.insert(roleEntity);
    }

    public ResultDto delete(Long id)
    {
        return roleCaller.delete(id);
    }

    public ResultDto deletes(Long[] id)
    {
        return roleCaller.deletes(id);
    }



}
