package bht.expense.bpm.security.service;




import bht.expense.bpm.security.user.caller.admin.AdminInfoCaller;
import bht.expense.bpm.security.user.caller.userinfo.UserinfoCaller;
import bht.expense.bpm.security.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
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
            UserEntity userDetails =  userinfoCaller.findById(Long.parseLong(account));
            userDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ACTIVITI_USER"));
            return userDetails;
        }
        else {
            UserDetails userDetails = adminInfoCaller.selectUserByUserName(account);
            return userDetails;
        }
    }
}
