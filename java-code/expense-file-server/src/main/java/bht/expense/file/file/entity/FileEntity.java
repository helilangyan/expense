package bht.expense.file.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 姚轶文
 * @date 2020/7/16 13:53
 */
@ApiModel
@TableName("sys_file")
@Data
public class FileEntity {

    @ApiModelProperty(value = "文件id")
    @TableId(value = "id" , type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "原始文件名")
    private String sourceName;

    @ApiModelProperty(value = "随机文件名")
    private String targetName;

    @ApiModelProperty(value = "文件类型，扩展名")
    private String fileType;

    @ApiModelProperty(value = "文件大小")
    private String fileSize;

    @ApiModelProperty(value = "文件存储路径")
    private String filePath;

    @ApiModelProperty(value = "上传时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "上传人")
    private String createUser;

    @ApiModelProperty(value = "存储类型")
    private String storageType;
}
