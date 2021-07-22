package bht.expense.detail.apply.copy.controller;

import bht.expense.detail.apply.copy.dto.ApplyCopyDto;
import bht.expense.detail.apply.copy.service.ApplyCopyService;
import bht.expense.detail.common.PageListDto;
import bht.expense.detail.common.ResultDto;
import bht.expense.detail.security.user.entity.UserEntity;
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
 * @date 2021/6/22 11:09
 */
@Api(tags = "抄送给我的")
@RestController
@RequestMapping("apply-copy")
public class ApplyCopyController {

    @Autowired
    ApplyCopyService applyCopyService;


    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "登陆人ID", required = true),
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
    })
    @PostMapping("/list")
    public ResultDto toMyCopy(@RequestParam("userId")Long userId , @RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        //UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageListDto<ApplyCopyDto> pageListData = new PageListDto();
        PageInfo pageInfo = applyCopyService.toMyCopy(userId,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());
        return  ResultDto.success(pageListData);
    }
}
