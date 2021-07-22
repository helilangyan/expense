package bht.expense.business.enterprise.project.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.position.dto.PositionEntityDto;
import bht.expense.business.enterprise.project.dao.ProjectEntityDto;
import bht.expense.business.enterprise.project.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/4/28 14:17
 */
@Api(tags = "项目设置")
@RestController
@RequestMapping("enterprise/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @ApiOperation("列表，本企业设置的项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true),
            @ApiImplicitParam(name = "projectName", value = "查询条件，项目名称")
    })
    @PostMapping("/findByEnterpriseId")
    public ResultDto findByEnterpriseId(int page, int limit , Long enterpriseId , String projectName)
    {
        return projectService.findByEnterpriseId(page, limit, enterpriseId,projectName);
    }

    @ApiOperation("新增、修改")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody ProjectEntityDto projectEntityDto)
    {
        return projectService.insert(projectEntityDto);
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        return projectService.delete(id);
    }

    @ApiOperation("根据ID批量删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/deletes/{id}")
    public ResultDto deletes(@PathVariable Long[] id)
    {
        return projectService.deletes(id);
    }

    @ApiOperation("根据ID查看")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return projectService.findById(id);
    }
}
