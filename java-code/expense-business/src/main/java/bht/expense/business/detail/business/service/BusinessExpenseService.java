package bht.expense.business.detail.business.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.business.caller.BusinessExpenseCaller;
import bht.expense.business.detail.business.dto.ExpenseDetailEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/21 9:52
 */
@Service
public class BusinessExpenseService {

    @Autowired
    BusinessExpenseCaller businessExpenseCaller;

    public ResultDto findByUserIdAndEnterpriseId(String expenseName ,String expenseType , String tags ,String startTime , String endTime , Long userId, Long enterpriseId ,  int page, int limit)
    {
        return businessExpenseCaller.findByUserIdAndEnterpriseId(expenseName ,expenseType,tags,startTime , endTime,userId, enterpriseId, page, limit);
    }

    public ResultDto delete(Long id)
    {
        return businessExpenseCaller.delete(id);
    }

    public ResultDto findById( Long id)
    {
        return businessExpenseCaller.findById(id);
    }

    public ResultDto insert(ExpenseDetailEntityDto expenseDetailEntityDto)
    {
        return businessExpenseCaller.insert(expenseDetailEntityDto);
    }

    public ResultDto checkInsert(ExpenseDetailEntityDto expenseDetailEntityDto)
    {
        return businessExpenseCaller.checkInsert(expenseDetailEntityDto);
    }
}



