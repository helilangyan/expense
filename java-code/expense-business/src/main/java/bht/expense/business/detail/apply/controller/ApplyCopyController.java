package bht.expense.business.detail.apply.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.detail.apply.service.ApplyCopyService;
import bht.expense.business.security.entity.UserEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 姚轶文
 * @date 2021/6/22 14:21
 */
@Api(tags = "抄送给我的")
@RestController
@RequestMapping("detail/apply-copy")
public class ApplyCopyController {

    @Autowired
    ApplyCopyService applyCopyService;

    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
    })
    @PostMapping("/list")
    public ResultDto toMyCopy(@RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return applyCopyService.toMyCopy(Long.parseLong(userEntity.getId()),page,limit);
    }
}
