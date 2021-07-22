package bht.expense.enterprise.strategy.detail.vehicle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/6/24 15:48
 */
@ApiModel
@TableName("bus_enterprise_strategy_vehicle")
@Data
public class StrategyVehicleEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
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
