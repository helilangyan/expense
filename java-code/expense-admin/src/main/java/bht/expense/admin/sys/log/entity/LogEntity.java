package bht.expense.admin.sys.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 姚轶文
 * @date 2020/7/20 9:53
 */
@ApiModel
@TableName("sys_log")
@Data
public class LogEntity {

    @ApiModelProperty(value = "id")
    @TableId(value = "id" , type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "日志创建时间")
    private Date createTime;

    @ApiModelProperty(value = "日志级别1-9")
    private Integer logLevel;

    @ApiModelProperty(value = "操作模块")
    private String operationUnit;

    @ApiModelProperty(value = "执行的方法")
    private String method;

    @ApiModelProperty(value = "传递参数")
    private String args;

    @ApiModelProperty(value = "操作人")
    private String username;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "操作类型，CRUD")
    private String operationType;

    @ApiModelProperty(value = "执行时间")
    private Long runTime;

    @ApiModelProperty(value = "返回值")
    private String returnValue;

    @ApiModelProperty(value = "访问者令牌")
    private String authorization;
}
