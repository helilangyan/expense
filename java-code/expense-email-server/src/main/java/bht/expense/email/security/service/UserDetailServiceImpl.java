package bht.expense.email.security.service;




import bht.expense.email.security.user.caller.admin.AdminInfoCaller;
import bht.expense.email.security.user.caller.userinfo.UserinfoCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


/**
 * @author 姚轶文
 * @date 2020/7/28 16:02
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserinfoCaller userinfoCaller;

    @Autowired
    AdminInfoCaller adminInfoCaller;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if(pattern.matcher(account).matches())
        {
            return userinfoCaller.findById(Long.parseLong(account));
        }
        else {
            UserDetails userDetails = adminInfoCaller.selectUserByUserName(account);
            return userDetails;
        }
    }
}
