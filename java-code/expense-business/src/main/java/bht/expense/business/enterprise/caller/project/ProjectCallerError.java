package bht.expense.business.enterprise.caller.project;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.project.dao.ProjectEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/28 14:10
 */
@Slf4j
public class ProjectCallerError implements ProjectCaller{
    @Override
    public ResultDto findByEnterpriseId(int page, int limit, Long enterpriseId , String projectName) {
        log.error("invoke expense-enterprise-server: /project/findByEnterpriseId error");
        return null;
    }

    @Override
    public ResultDto insert(ProjectEntityDto projectEntityDto) {
        log.error("invoke expense-enterprise-server: /project/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /project/delete error");
        return null;
    }

    @Override
    public ResultDto deletes(Long[] id) {
        log.error("invoke expense-enterprise-server: /project/deletes error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-enterprise-server: /project/findById error");
        return null;
    }
}
