package bht.expense.business.enterprise.strategy.detail.vehicle.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/6/24 16:43
 */
@ApiModel
@Data
public class StrategyVehicleEntityDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "编号")
    private String vehicleCode;

    @ApiModelProperty(value = "名称")
    private String vehicleName;

    @ApiModelProperty(value = "状态，0 禁用  1启用，默认1")
    private String status;

    @ApiModelProperty(value = "策略ID")
    private Long strategyId;
}
