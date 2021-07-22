package bht.expense.detail.apply.travel.dto;

import bht.expense.detail.apply.travel.entity.ApplyTravelDetailEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/22 14:11
 */
@ApiModel
@Data
public class ApplyTravelInfoDto {

    @ApiModelProperty(value = "id")
    private ApplyTravelEntity applyTravelEntity;

    @ApiModelProperty(value = "形成规划列表")
    private List<ApplyTravelDetailEntity> detailList;

    @ApiModelProperty(value = "出行人员列表")
    private List<ApplyTravelUserDto> userList;
}
