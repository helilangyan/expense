package bht.expense.detail.apply.copy.dao;

import bht.expense.detail.apply.copy.dto.ApplyCopyDto;
import bht.expense.detail.apply.copy.entity.ApplyCopyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * @author 姚轶文
 * @date 2021/6/22 10:37
 */
public interface ApplyCopyMapper extends BaseMapper<ApplyCopyEntity> {

    List<ApplyCopyDto> toMyCopy(Long userId);
}
