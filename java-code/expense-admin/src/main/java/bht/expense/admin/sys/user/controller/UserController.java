package bht.expense.admin.sys.user.controller;


import bht.expense.admin.common.PageListDto;
import bht.expense.admin.common.ResultCode;
import bht.expense.admin.common.ResultDto;
import bht.expense.admin.sys.log.aop.OperationLogDetail;
import bht.expense.admin.sys.log.aop.OperationType;
import bht.expense.admin.sys.log.aop.OperationUnit;
import bht.expense.admin.sys.resource.entity.ResourceEntity;
import bht.expense.admin.sys.security.jwt.JwtTokenUtil;
import bht.expense.admin.sys.user.entity.UserEntity;
import bht.expense.admin.sys.user.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/13 11:21
 */
@Api(tags = "用户管理相关接口")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation("根据用户ID查询")
    @ApiImplicitParam(name = "id", value = "用户id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable String id)
    {
        UserEntity userEntity = userService.findById(id);
        return ResultDto.success(userEntity);
    }


    @ApiOperation("用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
    })
    @PostMapping("/list")
    public ResultDto list(String username,String status,int page,int limit)
    {
        Map<String,String> map = new HashMap();
        map.put("username",username);
        map.put("status",status);

        PageListDto<UserEntity> pageListData = new PageListDto();
        PageInfo pageInfo = userService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }

    @ApiOperation("插入，新增或修改，根据用户ID自动判断")
    @OperationLogDetail(detail = "新增用户", level = 3, operationUnit = OperationUnit.USER, operationType = OperationType.INSERT)
    @PostMapping("/insert")
    public ResultDto insert(@ModelAttribute UserEntity userEntity) {
        UserEntity checkUser = userService.selectUserByUserName(userEntity.getUsername());
        if(checkUser != null)
        {
            return ResultDto.failure(ResultCode.FAILURE,"用户名已注册");
        }
        String encryptionPassword = BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt());
        userEntity.setPassword(encryptionPassword);
        userEntity = userService.insert(userEntity);
        return ResultDto.success(userEntity);
    }


    @ApiOperation("根据用户ID删除")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    @OperationLogDetail(detail = "删除用户", level = 3, operationUnit = OperationUnit.USER, operationType = OperationType.DELETE)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable String id) {
        userService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("批量删除，传入ID数组")
    @OperationLogDetail(detail = "删除用户", level = 3, operationUnit = OperationUnit.USER, operationType = OperationType.DELETE)
    @DeleteMapping("/dels/{id}")
    public ResultDto deletes(@PathVariable String[] id) {
        userService.dels(id);
        return ResultDto.success();
    }

    @ApiOperation("判断用户是否已经注册")
    @PostMapping("/check")
    public ResultDto checkUser(String username)
    {
        UserEntity userEntity = userService.selectUserByUserName(username);
        if(userEntity == null)
        {
            return ResultDto.success("未注册用户");
        }
        else {
            return ResultDto.failure(ResultCode.FAILURE,"用户名已注册");
        }
    }

    @ApiOperation("重置默认密码")
    @PutMapping("/reset-passwrod")
    @OperationLogDetail(detail = "重置密码", level = 3, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
    public ResultDto resetPassword(String userId)
    {
        userService.resetPassword(userId);
        return ResultDto.success();
    }

    @ApiOperation("启用或禁用用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "status", value = "状态0禁用，1启用", required = true),
    })
    @OperationLogDetail(detail = "修改用户状态", level = 3, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
    @PutMapping("/operation-status")
    public ResultDto operationStatus(String userId , String status)
    {
        userService.operationStatus(userId, status);
        return ResultDto.success();
    }

    @ApiOperation("修改密码")
    @OperationLogDetail(detail = "修改密码", level = 3, operationUnit = OperationUnit.USER, operationType = OperationType.UPDATE)
    @PutMapping("/updata-password")
    public ResultDto updataPassword(String password , @RequestHeader("Authorization") String authorization)
    {
        String username = jwtTokenUtil.getUsernameFromToken(authorization);
        String encryptionPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userService.updataPassword(encryptionPassword,username);
        return ResultDto.success();
    }



    @ApiOperation("根据用户ID获取导航菜单")
    @GetMapping("/navigation/{userId}")
    public ResultDto findResourceByUserId(@PathVariable String userId)
    {
        List<ResourceEntity> list = userService.findResourceByUserId(userId);
        return ResultDto.success(list);
    }

    @ApiOperation("根据用户名查询")
    @PostMapping("/select-username")
    public UserEntity selectUserByUserName(String username)
    {
        UserEntity userEntity = userService.selectUserByUserName(username);
        return userEntity;
    }

//    @OperationLogDetail(detail = "测试", level = 3, operationUnit = OperationUnit.USER, operationType = OperationType.LOGIN)
//    @GetMapping("/test")
//    public ResultDto test(String username) {
//        // code that uses the language variable
////        System.out.println(Authorization);
//        return ResultDto.success();
//    }

}
