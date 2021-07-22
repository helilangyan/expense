package bht.expense.enterprise.project.service;

import bht.expense.enterprise.project.dao.ProjectMapper;
import bht.expense.enterprise.project.entity.ProjectEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/28 13:54
 */

@Service
public class ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Transactional
    public void delete(Long id)
    {
        projectMapper.delete(id);
    }

    @Transactional
    public void deletes(Long[] id)
    {
        projectMapper.deletes(id);
    }

    public PageInfo findByEnterpriseId(Map<String, Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<ProjectEntity> list = projectMapper.findByEnterpriseId(map);
        PageInfo<ProjectEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public ProjectEntity insert(ProjectEntity projectEntity)
    {
        if(projectEntity.getId()==null || projectEntity.getId()==0)
        {
            projectMapper.insert(projectEntity);
        }
        else {
            projectMapper.updateById(projectEntity);
        }
        return projectEntity;
    }

    public ProjectEntity findById(Long id)
    {
        return projectMapper.selectById(id);
    }
}
