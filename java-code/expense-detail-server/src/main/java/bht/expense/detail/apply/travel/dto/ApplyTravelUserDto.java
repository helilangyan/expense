package bht.expense.detail.apply.travel.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/4/22 14:12
 */
@ApiModel
@Data
public class ApplyTravelUserDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "申请ID")
    private Long applyId;

    @ApiModelProperty(value = "出差人员ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String firstName;

    @ApiModelProperty(value = "用户姓")
    private String lastName;
}
