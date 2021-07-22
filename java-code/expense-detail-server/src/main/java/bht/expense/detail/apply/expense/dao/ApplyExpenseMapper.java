package bht.expense.detail.apply.expense.dao;

import bht.expense.detail.apply.expense.entity.ApplyExpenseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/21 15:10
 */
public interface ApplyExpenseMapper extends BaseMapper<ApplyExpenseEntity> {

    void delete(Long id);

    List<ApplyExpenseEntity> findByUserIdAndEnterpriseId(Map<String , Object> map);


}
