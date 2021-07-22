package bht.expense.file.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.file.file.entity.FileRelation;
import org.apache.ibatis.annotations.Param;


public interface FileRelationMapper extends BaseMapper<FileRelation> {

    void deleteFileRelation(@Param("fileId") String fileId,@Param("businessId") String businessId);

    void deleteAllFileRelation(@Param("businessId") String businessId,@Param("type") String type);
}