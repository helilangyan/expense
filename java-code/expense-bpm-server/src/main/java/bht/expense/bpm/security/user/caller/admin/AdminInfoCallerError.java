package bht.expense.bpm.security.user.caller.admin;

import bht.expense.bpm.security.user.entity.UserEntity;

/**
 * @author 姚轶文
 * @date 2021/4/23 14:52
 */
public class AdminInfoCallerError implements AdminInfoCaller{


    @Override
    public UserEntity selectUserByUserName(String username) {
        return null;
    }
}
