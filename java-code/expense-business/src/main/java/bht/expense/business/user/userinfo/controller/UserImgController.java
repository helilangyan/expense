package bht.expense.business.user.userinfo.controller;

import bht.expense.business.common.ResultDto;
import bht.expense.business.security.entity.UserEntity;
import bht.expense.business.user.userinfo.dto.UserImgEntityDto;
import bht.expense.business.user.userinfo.service.UserImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/7/20 14:42
 */
@Api(tags = "用户头像")
@RestController
@RequestMapping("userinfo-img")
public class UserImgController {

    @Autowired
    UserImgService userImgService;

    @ApiOperation("插入，新增或修改，根据用户ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody UserImgEntityDto userImgEntity) {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userImgEntity.setUserId(Long.parseLong(userEntity.getId()));
        return ResultDto.success(userImgService.insert(userImgEntity));
    }

    @ApiOperation("根据用户获取头像文件ID")
    @PostMapping("/find-userid")
    public ResultDto findByUserId()
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userImgService.findByUserId(Long.parseLong(userEntity.getId()));
    }
}
