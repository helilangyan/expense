package bht.expense.detail.apply.copy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/6/22 10:33
 */
@ApiModel
@TableName("bus_apply_copy")
@Data
public class ApplyCopyEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "bill表ID")
    private Long billId;

    @ApiModelProperty(value = "抄送用户ID")
    private Long copyUserId;
}
