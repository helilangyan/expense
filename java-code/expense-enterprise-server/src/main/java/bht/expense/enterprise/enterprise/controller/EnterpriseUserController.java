package bht.expense.enterprise.enterprise.controller;

import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.department.dto.DepartmentUserDto;
import bht.expense.enterprise.enterprise.dto.EnterpriseUserDto;
import bht.expense.enterprise.enterprise.entity.EnterpriseEntity;
import bht.expense.enterprise.enterprise.entity.EnterpriseUserEntity;
import bht.expense.enterprise.enterprise.service.EnterpriseUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/12 15:18
 */
@Api(tags = "企业用户关联")
@RestController
@RequestMapping("enterprise-user")
public class EnterpriseUserController {

    @Autowired
    EnterpriseUserService enterpriseUserService;

    @ApiOperation("根据用户ID，查看本用户入驻的企业")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    @PostMapping("/user-list")
    public ResultDto findByUserId(Long userId)
    {
        List<EnterpriseUserDto> list = enterpriseUserService.findByUserId(userId);
        return ResultDto.success(list);
    }

    @ApiOperation("根据企业ID，查看本企业的全部用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", required = true)
    })
    @PostMapping("/enterprise-list")
    public ResultDto findByEnterpriseId(int page, int limit, Long enterpriseId)
    {
        Map<String,String> map = new HashMap();

        PageListDto<EnterpriseEntity> pageListData = new PageListDto();
        PageInfo pageInfo = enterpriseUserService.findByEnterpriseId(page, limit, enterpriseId);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());
        return ResultDto.success(pageListData);
    }

    @ApiOperation("用户设置默认企业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "本条数据ID", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    @PostMapping("/set-default")
    public ResultDto setDefault(Long id , Long userId)
    {
        enterpriseUserService.setDefault(id,userId);
        return ResultDto.success();
    }

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        return ResultDto.success(enterpriseUserService.findById(id));
    }

    @ApiOperation("修改员工信息")
    @PostMapping("/update")
    public ResultDto update(@RequestBody EnterpriseUserEntity enterpriseUserEntity)
    {
        enterpriseUserService.update(enterpriseUserEntity);
        return ResultDto.success(enterpriseUserEntity);
    }

    @ApiOperation("根据ID删除,移除企业")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto remove(@PathVariable Long id)
    {
        enterpriseUserService.remove(id);
        return ResultDto.success();
    }
}
