package bht.expense.detail.apply.travel.dao;

import bht.expense.detail.apply.travel.dto.ApplyTravelUserDto;
import bht.expense.detail.apply.travel.entity.ApplyTravelDetailEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/22 11:28
 */
public interface ApplyTravelUserMapper extends BaseMapper<ApplyTravelUserEntity> {

    List<ApplyTravelUserDto> findByApplyId(Long applyId);

    void deleteByApplyId(Long applyId);
}
