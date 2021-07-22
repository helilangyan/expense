package bht.expense.enterprise.project.controller;

import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.project.entity.ProjectEntity;
import bht.expense.enterprise.project.service.ProjectService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/28 13:58
 */
@Api(tags = "项目设置")
@RestController
@RequestMapping("project")
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
        Map<String,Object> map = new HashMap();
        map.put("enterpriseId",enterpriseId);
        map.put("projectName",projectName);

        PageListDto<ProjectEntity> pageListData = new PageListDto();
        PageInfo pageInfo = projectService.findByEnterpriseId(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }

    @ApiOperation("新增、修改")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody ProjectEntity projectEntity)
    {
        projectService.insert(projectEntity);
        return ResultDto.success();
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/delete/{id}")
    public ResultDto delete(@PathVariable Long id)
    {
        projectService.delete(id);
        return ResultDto.success();
    }

    @ApiOperation("根据ID批量删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/deletes/{id}")
    public ResultDto deletes(@PathVariable Long[] id)
    {
        projectService.deletes(id);
        return ResultDto.success();
    }

    @ApiOperation("根据ID查看")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return ResultDto.success(projectService.findById(id));
    }
}
