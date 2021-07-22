package bht.expense.detail.apply.travel.dto;

import bht.expense.detail.apply.travel.entity.ApplyTravelDetailEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelUserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/23 14:07
 */
@ApiModel
@Data
public class ApplyTravelInsertDto {

    @ApiModelProperty(value = "出差基本信息")
    ApplyTravelEntity applyTravelEntity;

    @ApiModelProperty(value = "出差行表")
    List<ApplyTravelDetailEntity> detailList;

    @ApiModelProperty(value = "出差人员表")
    List<ApplyTravelUserEntity> userList;
}
