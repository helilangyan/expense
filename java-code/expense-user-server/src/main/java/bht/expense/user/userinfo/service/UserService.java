package bht.expense.user.userinfo.service;


import bht.expense.user.userinfo.dao.UserMapper;
import bht.expense.user.userinfo.entity.UserEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/6 15:12
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Transactional
    public void del(String id)
    {
        userMapper.del(id);
    }

    @Transactional
    public void dels(String[] id)
    {
        userMapper.dels(id);
    }

    public PageInfo findByAll(Map<String, String> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<UserEntity> list = userMapper.findByAll(map);
        PageInfo<UserEntity> pageInfo = new PageInfo<UserEntity>(list);
        return pageInfo;
    }

    @Transactional
    public UserEntity insert(UserEntity userEntity)
    {
        if(userEntity.getId()==null || userEntity.getId()==0)
        {
            userMapper.insert(userEntity);
        }
        else {
            userMapper.updateById(userEntity);
        }
        return userEntity;
    }

    @Transactional
    public void update(UserEntity userEntity)
    {
        userMapper.updateById(userEntity);
    }

    public UserEntity findById(Long id)
    {
        return userMapper.selectById(id);
    }

    @Transactional
    public void resetPassword(String userId)
    {
        userMapper.resetPassword(userId);
    }

    @Transactional
    public void updatePassword(String password , Long id)
    {
        userMapper.updatePassword(password,id);
    }

    public UserEntity findByAccount(String account)
    {
        return userMapper.findByAccount(account);
    }

    public Long checkAccount(String account)
    {
        return userMapper.checkAccount(account);
    }

    @Transactional
    public void updateUsername(String firstName , String lastName , Long id)
    {
        userMapper.updateUsername(firstName, lastName, id);
    }

    @Transactional
    public void updatePhone(String phone , Long id)
    {
        userMapper.updatePhone(phone, id);
    }

    @Transactional
    public void updateEmail(String email , Long id)
    {
        userMapper.updateEmail(email,id);
    }

}
