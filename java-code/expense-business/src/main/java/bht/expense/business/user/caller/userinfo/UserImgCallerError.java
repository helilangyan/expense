package bht.expense.business.user.caller.userinfo;

import bht.expense.business.common.ResultDto;
import bht.expense.business.user.userinfo.dto.UserImgEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/7/20 14:37
 */
@Slf4j
public class UserImgCallerError implements UserImgCaller{
    @Override
    public ResultDto insert(UserImgEntityDto userImgEntity) {
        log.error("invoke expense-user-server: /userinfo-img/insert error");
        return null;
    }

    @Override
    public ResultDto findByUserId(Long userId) {
        log.error("invoke expense-user-server: /userinfo-img/findByUserId error");
        return null;
    }
}
