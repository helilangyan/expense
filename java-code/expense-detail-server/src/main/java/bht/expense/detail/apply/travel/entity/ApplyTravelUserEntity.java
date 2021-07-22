package bht.expense.detail.apply.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/21 17:07
 */
@ApiModel
@TableName("bus_apply_travel_user")
@Data
public class ApplyTravelUserEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "申请ID")
    private Long applyId;

    @ApiModelProperty(value = "出差人员ID")
    private Long userId;
}
