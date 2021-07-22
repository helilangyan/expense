package bht.expense.detail.apply.expense.dao;

import bht.expense.detail.apply.expense.entity.ApplyExpenseDetailEntity;
import bht.expense.detail.detail.entity.ExpenseDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/21 15:13
 */
public interface ApplyExpenseDetailMapper extends BaseMapper<ApplyExpenseDetailEntity> {

    void deleteByApplyId(Long applyId);

    List<ExpenseDetailEntity> findByApplyId(Long applyId);
}
