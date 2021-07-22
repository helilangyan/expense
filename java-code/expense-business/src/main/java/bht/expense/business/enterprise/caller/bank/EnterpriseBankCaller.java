package bht.expense.business.enterprise.caller.bank;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.bank.dto.EnterpriseBankEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/7 15:37
 */

@FeignClient(value = "expense-enterprise-server",fallback = EnterpriseBankCallerError.class, contextId = "EnterpriseBankCaller")
public interface EnterpriseBankCaller {

    @PostMapping("/bank/list")
    ResultDto list(@RequestParam("page") int page, @RequestParam("limit") int limit , @RequestParam("enterpriseId") Long enterpriseId);

    @GetMapping("/bank/{id}")
    ResultDto findById(@RequestParam("id") Long id);

    @PostMapping("/bank/insert")
    ResultDto insert(@RequestBody EnterpriseBankEntityDto bankEntityDto);

    @DeleteMapping("/bank/del/{id}")
    ResultDto delete(@RequestParam("id") Long id);

}
