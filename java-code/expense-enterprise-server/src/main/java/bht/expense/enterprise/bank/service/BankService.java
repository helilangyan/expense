package bht.expense.enterprise.bank.service;


import bht.expense.enterprise.bank.dao.BankMapper;
import bht.expense.enterprise.bank.entity.BankEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/7 13:51
 */
@Service
public class BankService {

    @Autowired
    BankMapper bankMapper;

    @Transactional
    public void del(Long id)
    {
        bankMapper.del(id);
    }

    @Transactional
    public void dels(Long[] id)
    {
        bankMapper.dels(id);
    }

    public PageInfo findByAll(Map<String, String> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<BankEntity> list = bankMapper.findByAll(map);
        PageInfo<BankEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public BankEntity insert(BankEntity bankEntity)
    {
        if(bankEntity.getId()==null)
        {
            bankMapper.insert(bankEntity);
        }
        else {
            bankMapper.updateById(bankEntity);
        }
        return bankEntity;
    }

    public BankEntity findById(Long id)
    {
        return bankMapper.selectById(id);
    }

}
