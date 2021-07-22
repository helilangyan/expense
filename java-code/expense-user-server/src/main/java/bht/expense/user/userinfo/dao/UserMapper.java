package bht.expense.user.userinfo.dao;

import bht.expense.user.userinfo.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/6 15:05
 */
public interface UserMapper extends BaseMapper<UserEntity> {
    void del(String id);

    void dels(String[] id);

    List<UserEntity> findByAll(Map<String, String> map);


    void resetPassword(String userId);

    void updatePassword(String password , Long id);

    UserEntity findByAccount(String account);

    Long checkAccount(String account);

    void updateUsername(String firstName , String lastName , Long id);

    void updatePhone(String phone , Long id);

    void updateEmail(String email , Long id);
}
