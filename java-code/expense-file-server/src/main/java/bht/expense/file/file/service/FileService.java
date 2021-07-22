package bht.expense.file.file.service;

import bht.expense.file.file.dao.FileMapper;
import bht.expense.file.file.entity.FileEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/16 14:24
 */
@Service
public class FileService {

    @Autowired
    FileMapper fileMapper;

    @Autowired
    LocatFileSerice locatFileSerice;

    @Autowired
    OssFileService ossFileService;

    @Value("${file-upload.upload-type:local}")
    String uploadType;


    @Transactional
    public FileEntity insert(FileEntity fileEntity)
    {
        fileMapper.insert(fileEntity);
        return fileEntity;
    }

    public FileEntity findById(String id)
    {
        return fileMapper.selectById(id);
    }


    public void del(String id)
    {
        FileEntity fileEntity = findById(id);

        if(fileEntity.getStorageType().equals("local"))
        {
            locatFileSerice.del(fileEntity);
        }
        else if(fileEntity.getStorageType().equals("oss"))
        {
            ossFileService.del(fileEntity);
        }

    }

    @Transactional
    void dels(String[] id)
    {
        fileMapper.dels(id);
    }

    public PageInfo findByAll(Map<String, String> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<FileEntity> list = fileMapper.findByAll(map);
        PageInfo<FileEntity> pageInfo = new PageInfo<FileEntity>(list);
        return pageInfo;
    }

    @Transactional
    public FileEntity upload(MultipartFile file , String username)
    {
        if(uploadType.equals("local"))
        {
            return locatFileSerice.upload(file,username);
        }
        else if(uploadType.equals("oss"))
        {
            return ossFileService.upload(file,username);
        }
        return null;
    }

    public void download(String id , HttpServletResponse response)
    {
        FileEntity fileEntity = fileMapper.selectById(id);

        if(fileEntity.getStorageType().equals("local"))
        {
            locatFileSerice.download(fileEntity,response);
        }
        else if(fileEntity.getStorageType().equals("oss"))
        {
            ossFileService.download(fileEntity,response);
        }
    }

    public void openFile(String id, HttpServletResponse response)
    {
        FileEntity fileEntity = fileMapper.selectById(id);
        if(fileEntity.getStorageType().equals("local"))
        {
            locatFileSerice.openFile(fileEntity,response);
        }
        else if(fileEntity.getStorageType().equals("oss"))
        {
            ossFileService.openFile(fileEntity,response);
        }
    }

    public String getOssUrl(String id)
    {
        FileEntity fileEntity = fileMapper.selectById(id);
        return ossFileService.getUrl(fileEntity);
    }
}
