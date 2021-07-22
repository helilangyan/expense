package bht.expense.business.user.bank.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.user.bank.dto.BankEntityDto;
import bht.expense.business.user.caller.bank.BankCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/7 14:20
 */

@Service
public class BankService {

    @Autowired
    BankCaller bankCaller;

    public ResultDto list(int page, int limit , Long userId)
    {
        return bankCaller.list(page,limit,userId);
    }

    public ResultDto insert(BankEntityDto bankEntityDto)
    {
        return bankCaller.insert(bankEntityDto);
    }

    public ResultDto findById(Long id)
    {
        return bankCaller.findById(id);
    }

    public ResultDto delete(Long id)
    {
        return bankCaller.delete(id);
    }
}
