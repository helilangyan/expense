package bht.expense.user.security.service;


import bht.expense.user.userinfo.entity.UserEntity;
import bht.expense.user.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @date 2020/7/28 16:02
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        UserEntity users = userService.findByAccount(account);
        if (users == null){
            throw new UsernameNotFoundException("登录用户：" + account + " 不存在");
        }
        users.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ACTIVITI_USER"));

        return users;
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        UserEntity users = userService.findById(id);
        if (users == null){
            throw new UsernameNotFoundException("用户ID：" + id + " 不存在");
        }
        users.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));

        return users;
    }
}
