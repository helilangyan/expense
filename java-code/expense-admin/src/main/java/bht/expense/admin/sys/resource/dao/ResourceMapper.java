package bht.expense.admin.sys.resource.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.admin.sys.resource.entity.ResourceEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/14 10:44
 */
public interface ResourceMapper extends BaseMapper<ResourceEntity> {

    void del(String id);

    void dels(String[] id);

    List<ResourceEntity> findByAll(Map<String, String> map);

    List<ResourceEntity> findByParentId(String parentId);
}
