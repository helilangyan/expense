package bht.expense.user.userinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 姚轶文
 * @date 2021/7/20 14:10
 */
@ApiModel
@TableName("bus_user_img")
@Data
public class UserImgEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "文件id")
    private String fileId;
}
