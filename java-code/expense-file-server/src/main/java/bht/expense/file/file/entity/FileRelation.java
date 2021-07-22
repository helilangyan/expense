package bht.expense.file.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
@TableName(value = "sys_file_relation")
public class FileRelation {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键")
    private String id;

    /**
     * 文件id
     */
    @TableField(value = "file_id")
    @ApiModelProperty(value="文件id")
    private String fileId;

    /**
     * 业务id
     */
    @TableField(value = "business_id")
    @ApiModelProperty(value="业务id")
    private String businessId;

    /**
     * 业务类型(自定义)
     */
    @TableField(value = "type")
    @ApiModelProperty(value="业务类型(自定义)")
    private String type;

    public static final String COL_ID = "id";

    public static final String COL_FILE_ID = "file_id";

    public static final String COL_BUSINESS_ID = "business_id";

    public static final String COL_TYPE = "type";
}