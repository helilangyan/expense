package bht.expense.detail.detail.dao;

import bht.expense.detail.detail.dto.ExpenseDetailInfoDto;
import bht.expense.detail.detail.dto.ExpenseDetailListDto;
import bht.expense.detail.detail.entity.ExpenseDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/20 16:41
 */
public interface ExpenseDetailMapper extends BaseMapper<ExpenseDetailEntity> {

    List<ExpenseDetailListDto> findByUserIdAndEnterpriseId(Map<String , Object> map);

    void delete(Long id);

    ExpenseDetailInfoDto findById(Long id);
}
