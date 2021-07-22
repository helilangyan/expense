package bht.expense.business.enterprise.caller.enterprise;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.enterprise.dto.EnterpriseUserEntityDao;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/13 11:01
 */
@FeignClient(value = "expense-enterprise-server",fallback = EnterpriseUserCallerError.class, contextId = "EnterpriseUserCaller")
public interface EnterpriseUserCaller {

    @PostMapping("/enterprise-user/user-list")
    ResultDto findByUserId(@RequestParam("userId") Long userId);

    @PostMapping("/enterprise-user/enterprise-list")
    ResultDto findByEnterpriseId(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("enterpriseId") Long enterpriseId);

    @PostMapping("/enterprise-user/set-default")
    ResultDto setDefault(@RequestParam("id") Long id , @RequestParam("userId") Long userId);


    @GetMapping("/enterprise-user/{id}")
    ResultDto findById(@RequestParam("id") Long id);


    @PostMapping("/enterprise-user/update")
    ResultDto update(@RequestBody EnterpriseUserEntityDao enterpriseUserEntity);


    @DeleteMapping("/enterprise-user/del/{id}")
    ResultDto remove(@RequestParam("id") Long id);

}
