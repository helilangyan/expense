package bht.expense.file.file.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import bht.expense.file.common.FileUtil;
import bht.expense.file.file.dao.FileMapper;
import bht.expense.file.file.entity.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author 姚轶文
 * @date 2020/7/21 10:35
 */

@Service
public class OssFileService {

    @Value("${file-upload.endpoint:0}")
    private String endpoint;

    @Value("${file-upload.access-key-id:0}")
    private String accessKeyId;

    @Value("${file-upload.access-key-secret:0}")
    private String accessKeySecret;

    @Value("${file-upload.bucket-name:0}")
    private String bucketName;

    @Autowired
    FileMapper fileMapper;

    @Transactional
    public FileEntity upload(MultipartFile file, String username)
    {
        String fileExtName = FileUtil.ext(file.getOriginalFilename());
        String randomFileName = UUID.randomUUID().toString().replace("-","")+"."+fileExtName; //随机文件名，保存用此文件名

        File tempFile = FileUtil.multipartFileToFile(file);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, randomFileName, tempFile);
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        FileUtil.delteTempFile(tempFile);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        fileEntity.setCreateUser(username);
        fileEntity.setFileSize(FileUtil.getFileSize(file.getSize()));
        fileEntity.setFileType(fileExtName);
        fileEntity.setSourceName(file.getOriginalFilename());
        fileEntity.setTargetName(randomFileName);
        fileEntity.setStorageType("oss");
        fileMapper.insert(fileEntity);
        return fileEntity;
    }

    public void download(FileEntity fileEntity , HttpServletResponse response)
    {
        File tempFile = null;

        try {
            tempFile = File.createTempFile(fileEntity.getTargetName(),fileEntity.getFileType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.getObject(new GetObjectRequest(bucketName, fileEntity.getTargetName()), tempFile);
        ossClient.shutdown();

        if (tempFile.exists())
        {
            response.setContentType("application/force-download");// 设置强制下载不打开
            try {
                String fileName = new String(fileEntity.getSourceName().getBytes("GBK"),"ISO-8859-1");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(tempFile);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileUtil.delteTempFile(tempFile);
            }
        }
        FileUtil.delteTempFile(tempFile);
    }

    public void openFile(FileEntity fileEntity , HttpServletResponse response)
    {
        File tempFile = null;

        try {
            tempFile = File.createTempFile(fileEntity.getTargetName(),fileEntity.getFileType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.getObject(new GetObjectRequest(bucketName, fileEntity.getTargetName()), tempFile);
        ossClient.shutdown();

        if (tempFile.exists())
        {
            //response.setContentType("application/force-download");// 设置强制下载不打开
            try {
                String fileName = new String(fileEntity.getSourceName().getBytes("GBK"),"ISO-8859-1");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName );
                response.addHeader("Content-Length", "" + tempFile.length());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(tempFile);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileUtil.delteTempFile(tempFile);
            }
        }
        FileUtil.delteTempFile(tempFile);
    }

    @Transactional
    public void del(FileEntity fileEntity)
    {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, fileEntity.getTargetName());
        ossClient.shutdown();
        fileMapper.del(fileEntity.getId());
    }

    public String getUrl(FileEntity fileEntity)
    {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        Date expiratio= new Date(new Date().getTime()+3600*1000);
        URL url = ossClient.generatePresignedUrl(bucketName, fileEntity.getTargetName() , expiratio);
        return url.toString();
    }
}
