package bht.expense.admin.sys.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.admin.sys.resource.entity.ResourceEntity;
import bht.expense.admin.sys.user.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/13 10:37
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    List<UserEntity> selectUserByUserName(String username);

    void del(String id);

    void dels(String[] id);

    List<UserEntity> findByAll(Map<String, String> map);

    List<ResourceEntity> findResourceByUserId(Map<String, String> map);

    void resetPassword(String userId);

    void updataPassword(Map<String, String> map);

    void operationStatus(Map<String, String> map);
}
