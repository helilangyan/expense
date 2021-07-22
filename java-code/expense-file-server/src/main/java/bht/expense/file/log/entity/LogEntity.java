package bht.expense.file.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;

/**
 * @author 姚轶文
 * @date 2020/7/20 9:53
 */
@TableName("sys_log")
@Data
public class LogEntity {

    @TableId(value = "id" , type = IdType.UUID)
    private String id;

    private Date createTime;

    private Integer logLevel;

    private String operationUnit;

    private String method;

    private String args;

    private String username;

    private String description;

    private String operationType;

    private Long runTime;

    private String returnValue;

    private String authorization;
}
