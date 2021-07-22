package bht.expense.detail.apply.expense.dto;

import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.expense.entity.ApplyExpenseEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelEntity;
import bht.expense.detail.detail.entity.ExpenseDetailEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/21 16:52
 */
@ApiModel
@Data
public class ApplyExpenseInfoDto {

    @ApiModelProperty(value = "申请基本信息")
    private ApplyExpenseEntity applyExpenseEntity;

    @ApiModelProperty(value = "费用清单")
    private List<ExpenseDetailEntity> detailList;

    @ApiModelProperty(value = "前置条件，出差申请")
    private List<ApplyTravelEntity> travelList;

    @ApiModelProperty(value = "前置条件，借款申请")
    private List<ApplyBorrowEntity> borrowList;

}
