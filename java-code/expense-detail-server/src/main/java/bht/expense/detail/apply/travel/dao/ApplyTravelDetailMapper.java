package bht.expense.detail.apply.travel.dao;

import bht.expense.detail.apply.travel.entity.ApplyTravelDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/22 11:23
 */
public interface ApplyTravelDetailMapper extends BaseMapper<ApplyTravelDetailEntity> {

    List<ApplyTravelDetailEntity> findByApplyId(Long applyId);

    void deleteByApplyId(Long applyId);
}
