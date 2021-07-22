package bht.expense.detail.apply.travel.dao;

import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/22 11:29
 */
public interface ApplyTravelMapper extends BaseMapper<ApplyTravelEntity> {

    void delete(Long id);

    List<ApplyTravelEntity> findByUserIdAndEnterpriseId(Map<String , Object> map);
}
