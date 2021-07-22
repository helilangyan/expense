package bht.expense.user.userinfo.service;

import bht.expense.user.userinfo.dao.UserImgMapper;
import bht.expense.user.userinfo.entity.UserImgEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 姚轶文
 * @date 2021/7/20 14:15
 */
@Service
public class UserImgService extends ServiceImpl<UserImgMapper, UserImgEntity> {

    public UserImgEntity findByUserId(Long userId)
    {
        return baseMapper.findByUserId(userId);
    }

    @Transactional
    public void del(Long id)
    {
        baseMapper.deleteById(id);
    }


    @Transactional
    public void insert(UserImgEntity userImgEntity)
    {
        if(userImgEntity.getId() ==null || userImgEntity.getId()==0)
        {
            baseMapper.insert(userImgEntity);
        }
        else {
            baseMapper.updateById(userImgEntity);
        }
    }
}
