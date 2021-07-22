package bht.expense.detail.apply.borrow.dao;

import bht.expense.detail.apply.borrow.dto.ApplyBorrowInfoDto;
import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/22 10:09
 */
public interface ApplyBorrowMapper extends BaseMapper<ApplyBorrowEntity> {

    void delete(Long id);

    List<ApplyBorrowInfoDto> findByUserIdAndEnterpriseId(Map<String , Object> map);

    ApplyBorrowInfoDto findById(Long id);
}
