package bht.expense.admin.menu.caller;

import bht.expense.admin.common.ResultDto;

import bht.expense.admin.menu.dto.MenuEntityDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/14 11:11
 */
@FeignClient(value = "expense-enterprise-server",fallback = MenuCallerError.class, contextId = "MenuCaller")
public interface MenuCaller {


    @PostMapping("/menu/list")
    ResultDto list(@RequestParam("page") int page, @RequestParam("limit") int limit);


    @GetMapping("/menu/{id}")
    ResultDto findById(@RequestParam("id") Long id);

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/menu/insert")
    ResultDto insert(@RequestBody MenuEntityDto menuEntityDto);


    @DeleteMapping("/menu/del/{id}")
    ResultDto delete(@RequestParam("id") Long id);

    @DeleteMapping("/menu/dels/{id}")
    ResultDto deletes(@RequestParam("id") Long[] id);
}
