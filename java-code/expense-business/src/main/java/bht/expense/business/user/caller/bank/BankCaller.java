package bht.expense.business.user.caller.bank;

import bht.expense.business.common.ResultDto;
import bht.expense.business.user.bank.dto.BankEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/7 15:37
 */

@FeignClient(value = "expense-user-server",fallback = bht.expense.business.user.caller.bank.BankCallerError.class, contextId = "BankCaller")
public interface BankCaller {

    @PostMapping("/bank/list")
    ResultDto list(@RequestParam("page") int page, @RequestParam("limit") int limit , @RequestParam("userId") Long userId);

    @GetMapping("/bank/{id}")
    ResultDto findById(@RequestParam("id") Long id);

    @PostMapping("/bank/insert")
    ResultDto insert(@RequestBody BankEntityDto bankEntityDto);

    @DeleteMapping("/bank/del/{id}")
    ResultDto delete(@RequestParam("id") Long id);

}
