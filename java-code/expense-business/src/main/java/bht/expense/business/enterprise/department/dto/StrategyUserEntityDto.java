package bht.expense.business.enterprise.department.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/7/14 16:57
 */
@ApiModel
@Data
public class StrategyUserEntityDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "企业id")
    private Long enterpriseId;

    @ApiModelProperty(value = "策略id")
    private Long strategyId;

    @ApiModelProperty(value = "用户id")
    private Long userId;
}
