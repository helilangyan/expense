package bht.expense.business.user.caller.userinfo;

import bht.expense.business.common.ResultDto;
import bht.expense.business.user.userinfo.dto.UserImgEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/7/20 14:35
 */
@FeignClient(value = "expense-user-server",fallback = UserImgCallerError.class , contextId = "UserImgCaller")
public interface UserImgCaller {

    @PostMapping("/userinfo-img/insert")
    ResultDto insert(@RequestBody UserImgEntityDto userImgEntity);

    @PostMapping("/userinfo-img/find-userid")
    ResultDto findByUserId(@RequestParam("userId")Long userId);

}
