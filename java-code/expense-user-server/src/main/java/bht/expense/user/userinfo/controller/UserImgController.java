package bht.expense.user.userinfo.controller;

import bht.expense.user.common.ResultDto;
import bht.expense.user.userinfo.entity.UserImgEntity;
import bht.expense.user.userinfo.service.UserImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 姚轶文
 * @date 2021/7/20 14:22
 */
@Api(tags = "用户头像")
@RestController
@RequestMapping("userinfo-img")
public class UserImgController {

    @Autowired
    UserImgService userImgService;

    @ApiOperation("插入，新增或修改，根据用户ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@RequestBody UserImgEntity userImgEntity) {
        userImgService.insert(userImgEntity);
        return ResultDto.success(userImgEntity);
    }

    @ApiOperation("根据用户获取头像文件ID")
    @PostMapping("/find-userid")
    public ResultDto findByUserId(Long userId)
    {
        UserImgEntity userImgEntity = userImgService.findByUserId(userId);
        return ResultDto.success(userImgEntity);
    }
}
