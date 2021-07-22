package bht.expense.business.enterprise.project.service;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.caller.project.ProjectCaller;
import bht.expense.business.enterprise.project.dao.ProjectEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 姚轶文
 * @date 2021/4/28 14:15
 */
@Service
public class ProjectService {

    @Autowired
    ProjectCaller projectCaller;

    public ResultDto findByEnterpriseId(int page, int limit , Long enterpriseId,String projectName)
    {
        return projectCaller.findByEnterpriseId(page, limit, enterpriseId , projectName);
    }


    public ResultDto insert(ProjectEntityDto projectEntityDto)
    {
        return projectCaller.insert(projectEntityDto);
    }


    public ResultDto delete(Long id)
    {
        return projectCaller.delete(id);
    }


    public ResultDto deletes(Long[] id)
    {
        return projectCaller.deletes(id);
    }

    public ResultDto findById(Long id)
    {
        return projectCaller.findById(id);
    }
}
