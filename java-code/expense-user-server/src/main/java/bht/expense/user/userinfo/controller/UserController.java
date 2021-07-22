package bht.expense.user.userinfo.controller;

import bht.expense.user.common.PageListDto;
import bht.expense.user.common.ResultCode;
import bht.expense.user.common.ResultDto;
import bht.expense.user.userinfo.entity.UserEntity;
import bht.expense.user.userinfo.service.UserService;
import bht.expense.user.util.RedisUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/7 15:10
 */

@Api(tags = "用户信息")
@RestController
@RequestMapping("userinfo")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public UserEntity findById(@PathVariable Long id)
    {
        return userService.findById(id);
    }

    @ApiOperation("用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true)
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit)
    {
        Map<String,String> map = new HashMap();

        PageListDto<UserEntity> pageListData = new PageListDto();
        PageInfo pageInfo = userService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }

    @ApiOperation("插入，新增或修改，根据用户ID自动判断")
    @PostMapping("/insert")
    public ResultDto insert(@ModelAttribute UserEntity userEntity) {
        String encryptionPassword = BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt());
        userEntity.setPassword(encryptionPassword);
        userEntity = userService.insert(userEntity);
        return ResultDto.success(userEntity);
    }

    @ApiOperation("检查注册的用户名是否重复")
    @ApiImplicitParam(name = "account", value = "用户名，手机号", required = true)
    @PostMapping("/check-account")
    public ResultDto checkAccount(String account)
    {
        Long check = userService.checkAccount(account);
        if(check != 0 )
        {
            return ResultDto.failure(ResultCode.FAILURE,"用户名重复");
        }
        else {
            return ResultDto.success();
        }
    }

    @ApiOperation("修改姓名")
    @PostMapping("/update-username")
    public ResultDto updateUsername(String firstName , String lastName)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUsername(firstName,lastName,userEntity.getId());
        return ResultDto.success();
    }

    @ApiOperation("修改手机号")
    @PostMapping("/update-phone")
    public ResultDto updatePhone(String phone)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updatePhone(phone, userEntity.getId());
        return ResultDto.success();
    }

    @ApiOperation("修改邮箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "目标邮箱", required = true),
            @ApiImplicitParam(name = "verificationCode", value = "验证码", required = true)
    })
    @PostMapping("/update-email")
    public ResultDto updateEmail(String email , String verificationCode)
    {
        String vc = (String) redisUtil.get(email);
        if(vc.equals(verificationCode.trim()))
        {
            UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.updateEmail(email,userEntity.getId());
            return ResultDto.success();
        }
        else {
            return ResultDto.failure(ResultCode.FAILURE,"Verification Code Error");
        }
    }

    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true),
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true)
    })
    @PostMapping("/update-password")
    public ResultDto updatePassword(String newPassword , String oldPassword)
    {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userEntity = userService.findById(userEntity.getId());

        boolean checkpw = BCrypt.checkpw(oldPassword, userEntity.getPassword());
        if(checkpw)
        {
            String encryptionPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            userService.updatePassword(encryptionPassword,userEntity.getId());
        }
        else {
            return ResultDto.failure(ResultCode.FAILURE,"Password Error");
        }

        return ResultDto.success();
    }
}
