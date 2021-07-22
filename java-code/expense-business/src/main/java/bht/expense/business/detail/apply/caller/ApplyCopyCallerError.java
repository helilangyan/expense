package bht.expense.business.detail.apply.caller;

import bht.expense.business.common.ResultDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/6/22 14:17
 */
@Slf4j
public class ApplyCopyCallerError implements ApplyCopyCaller{
    @Override
    public ResultDto toMyCopy(Long userId, int page, int limit) {
        log.error("invoke expense-detail-server: /apply-copy/list error");
        return null;
    }
}
