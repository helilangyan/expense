package bht.expense.business.user.caller.userinfo;

import bht.expense.business.common.ResultDto;
import bht.expense.business.security.entity.UserEntity;
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

    @Override
    public ResultDto checkAccount(String account) {
        log.error("invoke expense-user-server: /userinfo/check-account error");
        return null;
    }

    @Override
    public ResultDto updateUsername(String firstName, String lastName) {
        log.error("invoke expense-user-server: /userinfo/updateUsername error");
        return null;
    }

    @Override
    public ResultDto updatePhone(String phone) {
        log.error("invoke expense-user-server: /userinfo/updatePhone error");
        return null;
    }

    @Override
    public ResultDto updateEmail(String email, String verificationCode) {
        log.error("invoke expense-user-server: /userinfo/updateEmail error");
        return null;
    }

    @Override
    public ResultDto updatePassword(String newPassword, String oldPassword) {
        log.error("invoke expense-user-server: /userinfo/updatePassword error");
        return null;
    }


}
