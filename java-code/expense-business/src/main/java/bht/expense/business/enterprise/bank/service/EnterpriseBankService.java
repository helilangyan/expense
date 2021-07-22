package bht.expense.business.enterprise.bank.service;

import bht.expense.business.common.ResultDto;

import bht.expense.business.enterprise.bank.dto.EnterpriseBankEntityDto;

import bht.expense.business.enterprise.caller.bank.EnterpriseBankCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2021/4/7 14:20
 */

@Service
public class EnterpriseBankService {

    @Autowired
    EnterpriseBankCaller bankCaller;

    public ResultDto list(int page, int limit , Long userId)
    {
        return bankCaller.list(page,limit,userId);
    }

    public ResultDto insert(EnterpriseBankEntityDto bankEntityDto)
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
