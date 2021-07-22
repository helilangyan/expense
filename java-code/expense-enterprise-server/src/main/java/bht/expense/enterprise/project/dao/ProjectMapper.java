package bht.expense.enterprise.project.dao;

import bht.expense.enterprise.project.entity.ProjectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/28 13:48
 */
public interface ProjectMapper extends BaseMapper<ProjectEntity> {

    List<ProjectEntity> findByEnterpriseId(Map<String, Object> map);

    void delete(Long id);

    void deletes(Long[] id);

}
