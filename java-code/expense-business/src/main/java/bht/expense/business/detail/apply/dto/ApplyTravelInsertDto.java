package bht.expense.business.detail.apply.dto;


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
    ApplyTravelEntityDto applyTravelEntity;

    @ApiModelProperty(value = "出差行表")
    List<ApplyTravelDetailEntityDto> detailList;

    @ApiModelProperty(value = "出差人员表")
    List<ApplyTravelUserEntityDto> userList;
}
