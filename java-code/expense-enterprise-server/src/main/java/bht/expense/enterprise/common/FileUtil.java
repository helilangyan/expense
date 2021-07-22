package bht.expense.enterprise.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;

/**
 * @author 姚轶文
 * @create 2020- 02-25 15:33
 */
public class FileUtil {

    public static String ext(String filename) {
        int index = filename.lastIndexOf(".");

        if (index == -1) {
            return null;
        }
        String result = filename.substring(index + 1);
        return result;
    }

    public static String getFileSize(long fileSize)
    {
        DecimalFormat df = new DecimalFormat("#.00");

        String fileSizeSrc = "";

        if(fileSize<1024)
        {
            return fileSize+"(B)";
        }
        if(fileSize>=1024 && fileSize<(1024*1024))
        {
            return df.format(fileSize/1024)+"(KB)";
        }
        if(fileSize>=(1024*1024) && fileSize<(1024*1024*1024))
        {
            return df.format(fileSize/1024/1024)+"(MB)";
        }
        if(fileSize>=(1024*1024*1024))
        {
            return df.format(fileSize/1024/1024/1024)+"(GB)";
        }


        return fileSizeSrc;
    }

    public static File multipartFileToFile(MultipartFile file)  {
        File toFile = null;
        InputStream ins = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            try {
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file)  {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }



}
