package bht.expense.file.file.service;

import bht.expense.file.common.FileUtil;
import bht.expense.file.file.dao.FileMapper;
import bht.expense.file.file.entity.FileEntity;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author 姚轶文
 * @date 2020/7/21 10:29
 */
@Service
public class LocatFileSerice {

    @Autowired
    FileMapper fileMapper;


    @Value("${file-upload.filePathPrefix:d:/upload}")
    String filePathPrefix;

    @Transactional
    public FileEntity upload(MultipartFile file, String username)
    {
        if(file!=null && !file.isEmpty())
        {
            String fileExtName = FileUtil.ext(file.getOriginalFilename());
            String randomFileName = UUID.randomUUID().toString().replace("-","")+"."+fileExtName; //随机文件名，保存用此文件名
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH )+1;
            String filePath = filePathPrefix+"/"+year+"/"+month+"/"+randomFileName;
            File dest = new File(filePath);

            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileEntity fileEntity = new FileEntity();
            fileEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            fileEntity.setCreateUser(username);
            fileEntity.setFilePath(filePath);
            fileEntity.setFileSize(FileUtil.getFileSize(file.getSize()));
            fileEntity.setFileType(fileExtName);
            fileEntity.setSourceName(file.getOriginalFilename());
            fileEntity.setTargetName(randomFileName);
            fileEntity.setStorageType("local");
            fileMapper.insert(fileEntity);
            return fileEntity;
        }
        return null;
    }

    public void download(FileEntity fileEntity , HttpServletResponse response)
    {
        String downloadFilePath = fileEntity.getFilePath();
        File file = new File(downloadFilePath);

        if (file.exists())
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
                fis = new FileInputStream(file);
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
            }
        }
    }


    public void openFile(FileEntity fileEntity , HttpServletResponse response)
    {
        String downloadFilePath = fileEntity.getFilePath();
        File file = new File(downloadFilePath);

        if (file.exists())
        {
            //response.setContentType("application/force-download");// 设置强制下载不打开
            try {
                String fileName = new String(fileEntity.getSourceName().getBytes("GBK"),"ISO-8859-1");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName );
                response.addHeader("Content-Length", "" + file.length());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
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
            }
        }
    }


    public void downloadTemplateJar(String templatePath , String templateName , HttpServletResponse response)
    {
        InputStream inputStream = null;
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource=resourceLoader.getResource(templatePath);

        BufferedInputStream bis=null;
        try {
            inputStream=resource.getInputStream();
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+ URLEncoder.encode(templateName, "UTF-8"));
            response.addHeader(HttpHeaders.CONTENT_TYPE,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            BufferedOutputStream bos = new BufferedOutputStream(
                    response.getOutputStream());
            bis = new BufferedInputStream(inputStream);
            byte[] b=new byte[1024];
            int i = bis.read(b);
            while (i != -1) {
                bos.write(b, 0, b.length);
                bos.flush();
                i = bis.read(b);
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void downloadExcel(String templatePath , String templateName , HttpServletResponse res)
    {
        try {
            OutputStream os = res.getOutputStream();
            InputStream bis = new BufferedInputStream(new ClassPathResource(templatePath).getInputStream());
            // 设置信息给客户端不解析
            String type = new MimetypesFileTypeMap().getContentType(templateName);
            // 设置content-type，即告诉客户端所发送的数据属于什么类型
            res.setContentType(type);
            // 设置编码
            String name = URLEncoder.encode(templateName, "UTF-8");

            // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
            res.setHeader("Content-Disposition", "attachment;filename=" + name);
            XSSFWorkbook workbook = new XSSFWorkbook(bis);
            workbook.write(os);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void downloadTemplateWar(String templatePath , String templateName , HttpServletResponse response)
    {
        File file = new File(templatePath);

        if (file.exists())
        {
            response.setContentType("application/force-download");// 设置强制下载不打开
            try {
                String fileName = new String(templateName.getBytes("GBK"),"ISO-8859-1");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
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
            }
        }
    }

    @Transactional
    public void del(FileEntity fileEntity)
    {
        File file = new File(fileEntity.getFilePath());
        if(file.exists())
        {
            file.delete();
        }
        fileMapper.del(fileEntity.getId());
    }
}
