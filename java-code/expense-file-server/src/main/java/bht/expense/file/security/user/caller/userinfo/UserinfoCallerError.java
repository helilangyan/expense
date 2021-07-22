package bht.expense.file.security.user.caller.userinfo;


import bht.expense.file.security.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/7 15:33
 */
@Slf4j
public class UserinfoCallerError implements  UserinfoCaller{

    @Override
    public UserEntity findById(Long id) {
        log.error("invoke expense-user-server: /userinfo/{id} error");
        return null;
    }
}
