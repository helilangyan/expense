package bht.expense.detail.security.service;




import bht.expense.detail.security.user.caller.userinfo.UserinfoCaller;
import org.springframework.beans.factory.annotation.Autowired;
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
    UserinfoCaller userinfoCaller;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        return userinfoCaller.findById(Long.parseLong(account));
    }
}
