package bht.expense.admin.enterprise.caller.enterprise;


import bht.expense.admin.common.ResultDto;
import bht.expense.admin.enterprise.enterprise.enterprise.dto.EnterpriseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/13 10:28
 */
@FeignClient(value = "expense-enterprise-server",fallback = EnterpriseCallerError.class, contextId = "EnterpriseCaller")
public interface EnterpriseCaller {

    @PostMapping("/enterprise/list")
    ResultDto list(@RequestParam("page") int page, @RequestParam("limit") int limit);

    @GetMapping("/enterprise/{id}")
    ResultDto findById(@RequestParam("id") Long id);

    @PostMapping("/enterprise/insert")
    ResultDto insert(@RequestBody EnterpriseEntityDto enterpriseEntityDto);

    @DeleteMapping("/enterprise/del/{id}")
    ResultDto delete(@RequestParam("id") Long id);

    @DeleteMapping("/enterprise/dels/{id}")
    ResultDto deletes(@RequestParam("id") Long[] id);

    @GetMapping("/enterprise/my-enterprise/{userId}")
    ResultDto myEnterprise(@RequestParam("userId") Long userId);

    @GetMapping("/enterprise/check-tax-code/{taxCode}")
    ResultDto checkTaxCode(@RequestParam("taxCode") String taxCode);

    @PutMapping("/enterprise/approve")
    ResultDto approve(@RequestParam("status")String status , @RequestParam("id")Long id);
}
