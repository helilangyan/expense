package bht.expense.detail.apply.expense.dao;

import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.expense.entity.ApplyExpensePrepEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/21 15:14
 */
public interface ApplyExpensePrepMapper  extends BaseMapper<ApplyExpensePrepEntity> {

    void deleteByApplyId(Long applyId);

    List<ApplyBorrowEntity> findByBorrow(Long applyId);

    List<ApplyTravelEntity> findByTravel(Long applyId);
}
