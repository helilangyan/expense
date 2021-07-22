package bht.expense.business.enterprise.caller.position;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.position.dto.PositionEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;



/**
 * @author 姚轶文
 * @date 2021/4/16 14:51
 */
@FeignClient(value = "expense-enterprise-server",fallback = PositionCallerError.class, contextId = "PositionCaller")
public interface PositionCaller {

    @PostMapping("/position/findByEnterpriseId")
    ResultDto findByEnterpriseId(@RequestParam("page") int page, @RequestParam("limit") int limit , @RequestParam("enterpriseId")Long enterpriseId);


    @PostMapping("/position/insert")
    ResultDto insert(@RequestBody PositionEntityDto positionEntityDto);


    @DeleteMapping("/position/delete/{id}")
    ResultDto delete(@RequestParam("id")  Long id);


    @DeleteMapping("/position/deletes/{id}")
    ResultDto deletes(@RequestParam("id") Long[] id);
}
