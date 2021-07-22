package bht.expense.file.file.controller;

import bht.expense.file.common.PageListDto;
import bht.expense.file.common.ResultDto;
import bht.expense.file.file.entity.FileEntity;
import bht.expense.file.file.service.FileService;
import bht.expense.file.log.aop.OperationLogDetail;
import bht.expense.file.log.aop.OperationType;
import bht.expense.file.log.aop.OperationUnit;
import bht.expense.file.security.jwt.JwtTokenUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/16 14:34
 */
@Api(tags = "文件管理相关接口")
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation("文件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "文件名"),
            @ApiImplicitParam(name = "page", value = "第几页", required = true),
            @ApiImplicitParam(name = "limit", value = "每页多少条记录", required = true),
    })
    @PostMapping("/list")
    public ResultDto list(String fileName, int page, int limit)
    {
        Map<String,String> map = new HashMap();
        map.put("fileName",fileName);

        PageListDto<FileEntity> pageListData = new PageListDto();
        PageInfo pageInfo = fileService.findByAll(map,page,limit);
        pageListData.setData(pageInfo.getList());
        pageListData.setCount(pageInfo.getTotal());

        return ResultDto.success(pageListData);
    }

    @ApiOperation("文件上传")
    //@OperationLogDetail(detail = "插入文件", level = 3, operationUnit = OperationUnit.SYSTEM, operationType = OperationType.INSERT)
    @PostMapping("upload")
    public ResultDto upload(MultipartFile file, @RequestHeader("Authorization") String authorization)
    {
        String username = jwtTokenUtil.getUsernameFromToken(authorization);
        FileEntity fileEntity = fileService.upload(file,username);
        return ResultDto.success(fileEntity);
    }

    @ApiOperation("文件删除")
    @ApiImplicitParam(name = "id", value = "文件ID", required = true)
    @OperationLogDetail(detail = "删除文件", level = 3, operationUnit = OperationUnit.SYSTEM, operationType = OperationType.DELETE)
    @DeleteMapping("/del/{id}")
    public ResultDto delete(@PathVariable String id) {
        fileService.del(id);
        return ResultDto.success();
    }

    @ApiOperation("文件下载")
    @ApiImplicitParam(name = "id", value = "文件ID", required = true)
    @PostMapping("/download")
    public void download(String id , HttpServletResponse response)
    {
        fileService.download(id,response);
    }

    @ApiOperation("文件打开")
    @ApiImplicitParam(name = "id", value = "文件ID", required = true)
    @PostMapping("/openFile")
    public void openFile(String id, HttpServletResponse response)
    {
        fileService.openFile(id,response);
    }

    @ApiOperation("获取OSS文件的外网下载地址，有效时间1小时")
    @ApiImplicitParam(name = "id", value = "文件id", required = true)
    @GetMapping("/oss-url/{id}")
    public ResultDto getOssUrl(@PathVariable String id)
    {
        String url = fileService.getOssUrl(id);
        return ResultDto.success(url);
    }

    @ApiOperation("根据ID获取文件信息")
    @ApiImplicitParam(name = "id", value = "文件id", required = true)
    @GetMapping("/info/{id}")
    public ResultDto info(@PathVariable String id)
    {
        FileEntity fileEntity = fileService.findById(id);
        return ResultDto.success(fileEntity);
    }

}
