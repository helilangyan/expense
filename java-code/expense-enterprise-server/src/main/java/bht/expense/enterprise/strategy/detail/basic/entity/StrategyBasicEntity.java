package bht.expense.enterprise.strategy.detail.basic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/6/24 13:55
 */
@ApiModel
@TableName("bus_enterprise_strategy_basic")
@Data
public class StrategyBasicEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
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
