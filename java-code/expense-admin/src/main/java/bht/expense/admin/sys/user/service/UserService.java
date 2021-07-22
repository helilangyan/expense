package bht.expense.admin.sys.user.service;

import bht.expense.admin.sys.resource.entity.ResourceEntity;
import bht.expense.admin.sys.user.dao.UserMapper;
import bht.expense.admin.sys.user.entity.UserEntity;
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
 * @date 2020/7/13 10:41
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public UserEntity selectUserByUserName(String username)
    {
        List<UserEntity> list = userMapper.selectUserByUserName(username);
        if(list!=null && list.size()==1)
        {
            return list.get(0);
        }
        return null;
    }

    @Transactional
    public UserEntity insert(UserEntity userEntity)
    {
        if(userEntity.getId()==null)
        {
            userMapper.insert(userEntity);
        }
        else {
            userMapper.updateById(userEntity);
        }
        return userEntity;
    }

    @Transactional
    public void updata(UserEntity userEntity)
    {
        userMapper.updateById(userEntity);
    }

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

    public UserEntity findById(String id)
    {
        return userMapper.selectById(id);
    }

    public PageInfo findByAll(Map<String,String> map , int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<UserEntity> list = userMapper.findByAll(map);
        PageInfo<UserEntity> pageInfo = new PageInfo<UserEntity>(list);
        return pageInfo;
    }

    public List<ResourceEntity> findResourceByUserId(String userId)
    {
        Map<String,String> map = new HashMap<>();
        map.put("userId",userId);
        return userMapper.findResourceByUserId(map);
    }

    @Transactional
    public void resetPassword(String userId)
    {
        userMapper.resetPassword(userId);
    }

    @Transactional
    public void operationStatus(String userId , String status)
    {
        Map<String,String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("status",status);

        userMapper.operationStatus(map);
    }

    @Transactional
    public void updataPassword(String password , String username)
    {
        Map<String,String> map = new HashMap<>();
        map.put("password",password);
        map.put("username",username);
        userMapper.updataPassword(map);
    }

}
