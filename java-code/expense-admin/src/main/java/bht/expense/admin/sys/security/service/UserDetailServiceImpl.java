package bht.expense.admin.sys.security.service;


import bht.expense.admin.sys.resource.entity.ResourceEntity;
import bht.expense.admin.sys.user.entity.UserEntity;
import bht.expense.admin.sys.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 姚轶文
 * @date 2020/7/28 16:02
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity users = userService.selectUserByUserName(username);
        if (users == null){
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        //将数据库的roles解析为UserDetails的权限集
        //AuthorityUtils.commaSeparatedStringToAuthorityList将逗号分隔的字符集转成权限对象列表
        //users.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(users.getRole()));
        List<ResourceEntity> list = userService.findResourceByUserId(users.getId());

        String authorityStr = list.stream().map(ResourceEntity::getResourceUrl).map(String::valueOf).collect(Collectors.joining(","));
        if(StringUtils.isEmpty(authorityStr)){
            authorityStr = "ROLE_ACTIVITI_USER";
        }else{
            authorityStr +=",ROLE_ACTIVITI_USER";
        }
        users.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(authorityStr));
        return users;
    }
}
