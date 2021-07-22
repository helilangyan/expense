package bht.expense.business.detail.apply.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/6/9 14:10
 */
@ApiModel
@Data
public class ApplyExpenseInsertDto {

    private ApplyExpenseEntityDto applyExpenseEntity;

    private Long[] detailIds;

    private Long[] borrowIds;

    private Long[] travelIds;
}
