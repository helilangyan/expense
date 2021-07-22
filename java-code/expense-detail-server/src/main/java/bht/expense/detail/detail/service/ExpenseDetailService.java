package bht.expense.detail.detail.service;

import bht.expense.detail.common.TimeUtil;
import bht.expense.detail.detail.dao.ExpenseDetailMapper;
import bht.expense.detail.detail.dto.ExpenseDetailInfoDto;
import bht.expense.detail.detail.dto.ExpenseDetailListDto;
import bht.expense.detail.detail.entity.ExpenseDetailEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/20 17:11
 */
@Service
public class ExpenseDetailService {

    @Autowired
    ExpenseDetailMapper expenseDetailMapper;

    public PageInfo findByUserIdAndEnterpriseId(Map<String , Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<ExpenseDetailListDto> list = expenseDetailMapper.findByUserIdAndEnterpriseId(map);
        PageInfo<ExpenseDetailListDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public void delete(Long id)
    {
        expenseDetailMapper.delete(id);
    }

    public ExpenseDetailInfoDto findById(Long id)
    {
        return expenseDetailMapper.findById(id);
    }

    @Transactional
    public ExpenseDetailEntity insert(ExpenseDetailEntity expenseDetailEntity)
    {
        if(expenseDetailEntity.getId()==null || expenseDetailEntity.getId()==0)
        {
            expenseDetailEntity.setCreateTime(TimeUtil.getUTC());
            expenseDetailMapper.insert(expenseDetailEntity);
        }
        else {
            expenseDetailMapper.updateById(expenseDetailEntity);
        }
        return expenseDetailEntity;
    }
}
