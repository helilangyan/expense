package bht.expense.business.enterprise.strategy.detail.basic.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:23
 */
@ApiModel
@Data
public class StrategyBasicEntityDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "1 允许提交并提示，0禁止提交并提示")
    private String isSubmit;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "策略ID")
    private Long strategyId;
}
