package bht.expense.enterprise.strategy.detail.classify.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/7/14 10:41
 */
@ApiModel
@TableName("bus_enterprise_strategy_classify")
@Data
public class StrategyClassifyEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
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
