package bht.expense.enterprise.enterprise.dao;


import bht.expense.enterprise.department.dto.DepartmentUserDto;
import bht.expense.enterprise.enterprise.entity.EnterpriseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/9 15:34
 */
public interface EnterpriseMapper extends BaseMapper<EnterpriseEntity> {

    void del(Long id);

    void dels(Long[] id);

    List<EnterpriseEntity> findByAll(Map<String, String> map);

    Long checkTaxCode(String taxCode , Long id);

    List<EnterpriseEntity> findByUserId(Long userId);

    void approve(String status , Long id);

    Long findByInvitationCode(String invitationCode);

}
