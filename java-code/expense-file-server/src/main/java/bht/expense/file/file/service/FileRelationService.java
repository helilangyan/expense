package bht.expense.file.file.service;

import bht.expense.file.file.dao.FileRelationMapper;
import bht.expense.file.file.entity.FileRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @AUTHOR: GC
 * @DATE: 2020-10-26
 * @VERSION: 1.0
 * @DESCRIPTION: 文件业务关系接口
 */
@Service
public class FileRelationService {
    @Autowired
    private FileRelationMapper fileRelationMapper;

    /**
     * @Description: 保存业务文件关联关系
     * @param fileId 文件id
     * @param businessId 业务id
     * @param type 业务类型
     * @Return void
     * @Author:GC
     * @Date: 2020/10/26 17:32
     */
    @Transactional
    public void addFileRelation(String fileId,String businessId,String type){
        FileRelation fileRelation = new FileRelation();
        fileRelation.setBusinessId(businessId);
        fileRelation.setFileId(fileId);
        fileRelation.setType(type);
        fileRelationMapper.insert(fileRelation);
    }

    /**
     * @Description: 删除文件关联关系
     * @param fileId 文件id
     * @param businessId 业务id
     * @Return void
     * @Author:GC
     * @Date: 2020/10/27 9:43
     */
    @Transactional
    public void deleteFileRelation(String fileId,String businessId){
        fileRelationMapper.deleteFileRelation(fileId, businessId);
    }

    @Transactional
    public void deleteAllFileRelation(String businessId,String type){
        fileRelationMapper.deleteAllFileRelation(businessId, type);
    }
}
