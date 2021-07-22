package bht.expense.detail.apply.expense.dto;

import bht.expense.detail.apply.expense.entity.ApplyExpenseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/6/9 14:17
 */
@ApiModel
@Data
public class ApplyExpenseInsertDto {

    private ApplyExpenseEntity applyExpenseEntity;

    private Long[] detailIds;

    private Long[] borrowIds;

    private Long[] travelIds;
}
