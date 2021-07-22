package bht.expense.business.detail.apply.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/22 16:33
 */
@ApiModel
@Data
public class ApplyTravelUserEntityDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "申请ID")
    private Long applyId;

    @ApiModelProperty(value = "出差人员ID")
    private Long userId;
}
