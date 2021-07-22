package bht.expense.business.enterprise.department.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.department.DepartmentCaller;
import bht.expense.business.enterprise.department.dto.DepartmentEntityDto;
import bht.expense.business.enterprise.department.dto.StrategyUserEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author 姚轶文
 * @date 2021/4/15 16:03
 */
@Service
public class DepartmentService {

    @Autowired
    DepartmentCaller departmentCaller;

    public ResultDto findByEnterpriseId(Long enterpriseId)
    {
        return departmentCaller.findByEnterpriseId(enterpriseId);
    }


    public ResultDto delete(Long id)
    {
        return departmentCaller.delete(id);
    }


    public ResultDto insert(DepartmentEntityDto departmentEntityDto)
    {
        return departmentCaller.insert(departmentEntityDto);
    }


    public ResultDto updateDepartmentName(String departmentName , Long id)
    {
        return departmentCaller.updateDepartmentName(departmentName, id);
    }

    public ResultDto findUsers(Long enterpriseId , Long departmentId)
    {
        return departmentCaller.findUsers(enterpriseId , departmentId);
    }

    public ResultDto inputStrategy(@RequestBody StrategyUserEntityDto strategyUserEntity)
    {
        return departmentCaller.inputStrategy(strategyUserEntity);
    }
}
