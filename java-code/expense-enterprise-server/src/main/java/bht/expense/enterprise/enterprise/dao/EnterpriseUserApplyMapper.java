package bht.expense.enterprise.enterprise.dao;

import bht.expense.enterprise.enterprise.dto.EnterpriseUserApplyDto;
import bht.expense.enterprise.enterprise.entity.EnterpriseUserApplyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/12 9:58
 */
public interface EnterpriseUserApplyMapper extends BaseMapper<EnterpriseUserApplyEntity> {

    List<EnterpriseUserApplyDto> findByEnterpriseId(Long enterpriseId);

    List<EnterpriseUserApplyDto> findByUserId(Long userId);

    void approve(String status , String message , Long id);
}
