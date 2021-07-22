package bht.expense.admin.sys.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.admin.sys.user.dto.UserRoleDto;
import bht.expense.admin.sys.user.entity.UserRoleEntity;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2020/7/14 14:54
 */
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

    public List<UserRoleDto> findByUserId(String userId);

    public void deleteByUserId(String userId);
}
