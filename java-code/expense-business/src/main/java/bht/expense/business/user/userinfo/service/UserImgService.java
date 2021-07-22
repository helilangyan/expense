package bht.expense.business.user.userinfo.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.user.caller.userinfo.UserImgCaller;
import bht.expense.business.user.userinfo.dto.UserImgEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 姚轶文
 * @date 2021/7/20 14:39
 */
@Service
public class UserImgService {

    @Autowired
    UserImgCaller userImgCaller;

    public ResultDto insert(UserImgEntityDto userImgEntity)
    {
        return userImgCaller.insert(userImgEntity);
    }

    public ResultDto findByUserId(Long userId)
    {
        return userImgCaller.findByUserId(userId);
    }
}
