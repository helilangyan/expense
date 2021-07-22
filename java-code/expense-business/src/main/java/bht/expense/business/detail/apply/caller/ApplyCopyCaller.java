package bht.expense.business.detail.apply.caller;

import bht.expense.business.common.ResultDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/6/22 14:16
 */
@FeignClient(value = "expense-detail-server",fallback = ApplyCopyCallerError.class, contextId = "ApplyCopyCaller")
public interface ApplyCopyCaller {

    @PostMapping("/apply-copy/list")
    ResultDto toMyCopy(@RequestParam("userId")Long userId , @RequestParam("page") int page, @RequestParam("limit") int limit);
}
