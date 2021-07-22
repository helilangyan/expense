package bht.expense.detail.strategy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/7/22 10:56
 */
@ApiModel
@Data
public class StrategyClassifyEntityDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;

    @ApiModelProperty(value = "策略ID")
    private Long StrategyId;

    @ApiModelProperty(value = "分类状态 0关闭  1使用")
    private String classifyStatus;

    @ApiModelProperty(value = "分类代码")
    private String classifyCode;

    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    @ApiModelProperty(value = "限制类型 0无限制  1有限制")
    private String limitType;

    @ApiModelProperty(value = "限制金额")
    private Float money;
}
