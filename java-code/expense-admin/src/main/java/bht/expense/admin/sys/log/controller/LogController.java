package bht.expense.admin.sys.log.controller;


import bht.expense.admin.common.PageListDto;
import bht.expense.admin.common.ResultDto;
import bht.expense.admin.sys.log.entity.LogEntity;
import bht.expense.admin.sys.log.service.LogService;
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
 * @date 2020/8/17 16:00
 */
@Api(tags = "操作日志相关接口")
@RestController
@RequestMapping("log")
public class LogController {

    @Autowired
    LogService logService;

    @ApiOperation("日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
            @ApiImplicitParam(name = "startTime", value = "查询条件，开始时间"),
            @ApiImplicitParam(name = "endTime", value = "查询条件，结束时间"),
            @ApiImplicitParam(name = "username", value = "操作账号")
    })
    @PostMapping("/list")
    public ResultDto list(int page, int limit, String startTime , String username, String endTime)
    {
        Map<String,String> map = new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("username",username);
        PageListDto<LogEntity> pageListData = new PageListDto();
        PageInfo pageInfo = logService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }


    @ApiOperation("根据ID查询")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/{id}")
    public ResultDto findById(@PathVariable String id)
    {
        LogEntity logEntity = logService.findById(id);
        return ResultDto.success(logEntity);
    }
}
