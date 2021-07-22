package bht.expense.business.enterprise.caller.role;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.role.dto.RoleEntityDto;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/14 15:29
 */
@FeignClient(value = "expense-enterprise-server",fallback = RoleCallerError.class, contextId = "RoleCaller")
public interface RoleCaller {


    @PostMapping("/role/list")
    ResultDto list(@RequestParam("page") int page, @RequestParam("limit") int limit , @RequestParam("enterpriseId") Long enterpriseId);


    @GetMapping("/role/{id}")
    ResultDto findById(@RequestParam("id") Long id);

    @PostMapping("/role/insert")
    ResultDto insert(@RequestBody RoleEntityDto roleEntity);


    @DeleteMapping("/role/del/{id}")
    ResultDto delete(@RequestParam("id") Long id);

    @DeleteMapping("/role/dels/{id}")
    ResultDto deletes(@RequestParam("id") Long[] id);




}
