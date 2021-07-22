package bht.expense.user.userinfo.dao;

import bht.expense.user.userinfo.entity.UserImgEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 姚轶文
 * @date 2021/7/20 14:14
 */
public interface UserImgMapper extends BaseMapper<UserImgEntity> {

    UserImgEntity findByUserId(Long userId);
}
