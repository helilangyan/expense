package bht.expense.enterprise.enterprise.controller;

import bht.expense.enterprise.common.PageListDto;
import bht.expense.enterprise.common.ResultCode;
import bht.expense.enterprise.common.ResultDto;
import bht.expense.enterprise.enterprise.entity.EnterpriseEntity;
import bht.expense.enterprise.enterprise.service.EnterpriseService;
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
 * @date 2021/4/9 16:43
 */
@Api(tags = "企业基本信息")
@RestController
@RequestMapping("enterprise")
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;

    @ApiOperation("全部企业列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true)
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit)
    {
        Map<String,String> map = new HashMap();

        PageListDto<EnterpriseEntity> pageListData = new PageListDto();
        PageInfo pageInfo = enterpriseService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable Long id)
    {
        EnterpriseEntity enterpriseEntity = enterpriseService.findById(id);
        return ResultDto.success(enterpriseEntity);
    }

    @ApiOperation("插入，新增或修改，根据ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody EnterpriseEntity enterpriseEntity) {
        Long check = enterpriseService.checkTaxCode(enterpriseEntity.getTaxCode() , enterpriseEntity.getId());
        if(check>0)
        {
            return ResultDto.failure(ResultCode.FAILURE,"税号重复");
        }
        enterpriseEntity = enterpriseService.insert(enterpriseEntity);
        return ResultDto.success(enterpriseEntity);
    }


    @ApiOperation("根据ID删除")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable Long id) {
        enterpriseService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable Long[] id) {
        enterpriseService.dels(id);
        return ResultDto.success();
    }

    @ApiOperation("我创建的企业列表")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    @GetMapping("/my-enterprise/{userId}")
    public ResultDto myEnterprise(@PathVariable Long userId)
    {
        List<EnterpriseEntity> list = enterpriseService.findByUserId(userId);
        return ResultDto.success(list);
    }

    @ApiOperation("验证唯一税号,返回值如果有数值说明重复")
    @GetMapping("/check-tax-code/{taxCode}")
    public ResultDto checkTaxCode(@PathVariable String taxCode)
    {
        return ResultDto.success(enterpriseService.checkTaxCode(taxCode , null));
    }

    @ApiOperation("审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "0=未开通 ， 1=已开通", required = true),
            @ApiImplicitParam(name = "id", value = "ID", required = true)
    })
    @PutMapping("/approve")
    public ResultDto approve(String status , Long id)
    {
        enterpriseService.approve(status,id);
        return ResultDto.success();
    }
}
