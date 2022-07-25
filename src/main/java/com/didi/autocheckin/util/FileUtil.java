package com.didi.autocheckin.util;

import cn.hutool.core.util.ZipUtil;
import lombok.Cleanup;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    /**
     * 获取文件类型
     */
    public static String getFileType(MultipartFile multipartFile){
        if (multipartFile.getContentType()!=null) {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] split = originalFilename.split("\\.");
            return split[split.length-1];
        }
        return null;
    }
    /**
     * 上传文件
     *
     * @param file     文件
     * @param filePath 保存路径
     * @param fileName 保存名称
     */
    public static void SaveFile(MultipartFile file, String filePath, String fileName) {
        try {
            // 输出的文件流保存到本地文件
            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            File f = new File(filePath, fileName);
            //将上传的文件存储到指定位置
            file.transferTo(f);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * @param url
     * @param fileName
     * @return
     */
    public static String getPath(String url, String fileName) {
        if (url == null) {
            url = "/api/file/Image/";
        }
        return url + fileName;
    }


    /**
     * 下载文件
     */
    public static void  fileDownload(String file){
        try {
            @Cleanup OutputStream outputStream =null;
            @Cleanup InputStream in = null;
            //读取指定路径下面的文件
            in = new FileInputStream(file);
            outputStream = new BufferedOutputStream(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().getOutputStream());
            //创建存放文件内容的数组
            byte[] buff = new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while ((n = in.read(buff)) != -1) {
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff, 0, n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * 下载文件
     */
    public static void  fileDownload(File file){
        try {
            @Cleanup OutputStream outputStream =null;
            @Cleanup InputStream in = null;
            //读取指定路径下面的文件
            in = new FileInputStream(file);
            outputStream = new BufferedOutputStream(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().getOutputStream());
            //创建存放文件内容的数组
            byte[] buff = new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while ((n = in.read(buff)) != -1) {
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff, 0, n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * 打包导出文件
     * @param filePaths
     * @param zipName
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public static File exportZip(List<String> filePaths, String zipName) throws InterruptedException, IOException {
        File zipFile = new File(zipName);
        InputStream[] ins = new InputStream[filePaths.size()];
        for (int i = 0; i < filePaths.size(); i++) {
            ins[i] = Files.newInputStream(Paths.get(filePaths.get(i)));
        }
        File zip = ZipUtil.zip(zipFile, filePaths.stream().toArray(String[]::new), ins);
        return zip;
    }
}
