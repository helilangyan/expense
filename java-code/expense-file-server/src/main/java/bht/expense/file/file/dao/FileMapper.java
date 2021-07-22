package bht.expense.file.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.file.file.entity.FileEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/16 14:03
 */
public interface FileMapper  extends BaseMapper<FileEntity> {

    void del(String id);

    void dels(String[] id);

    List<FileEntity> findByAll(Map<String, String> map);
}
