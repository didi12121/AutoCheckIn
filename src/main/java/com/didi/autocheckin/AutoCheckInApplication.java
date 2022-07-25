package com.didi.autocheckin;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.didi.autocheckin.config.FileConfig;
import com.didi.autocheckin.config.WbConfig;
import com.didi.autocheckin.module.FileInfo;
import com.didi.autocheckin.module.Result;
import com.didi.autocheckin.module.UploaderVO;
import com.didi.autocheckin.service.FileInfoService;
import com.didi.autocheckin.util.DateUtil;
import com.didi.autocheckin.util.FileUtil;
import com.didi.autocheckin.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author didi
 */
@SpringBootApplication
@Slf4j
@EnableScheduling
@RestController
public class AutoCheckInApplication {

    @Resource
    private WbConfig wbConfig;

    @Resource
    private FileConfig fileConfig;

    @Resource
    FileInfoService fileInfoService;


    public static void main(String[] args) {
        SpringApplication.run(AutoCheckInApplication.class, args);
    }

    public void auto(){
        String cookie = wbConfig.getCookie();
        List<String> pids = wbConfig.getPids();
        pids.forEach(x->{
            try {
                autoCheck(x,cookie);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        });
    }

    /**
     * 自动签到
     * @param pid 超话id
     * @param cookie cookie
     * @throws IOException
     */
    public void autoCheck(String pid,String cookie) throws IOException {
        Integer scCode = 100000;
        log.info("开始自动签到{}",pid);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("https://weibo.com/p/aj/general/button?api=http://i.huati.weibo.com/aj/super/checkin&id="+pid)
                .addHeader("cookie", cookie)
                .build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        try {
            JSONObject dataJsonObject = JSONObject.parseObject(data);
            Integer code = dataJsonObject.getInteger("code");
            if (code.equals(scCode)) {
                log.info("签到成功");
            } else {
                log.info(dataJsonObject.getString("msg"));
            }
            log.info("结束");
        }catch (JSONException e){
            log.info("签到失败,cookie失效");
        }
    }


    @PostMapping("/file/Uploader")
    public Result uploader(MultipartFile file) {
        String fileType = FileUtil.getFileType(file);
        String fileName = DateUtil.dateNow("yyyyMMdd") + "_" + RandomUtil.uuId() + "." + fileType;
        UploaderVO vo = UploaderVO.builder().name(fileName).build();
        String url;
            url = FileUtil.getPath( null, fileName);
        vo.setName(file.getName());
        vo.setUrl(url);
        vo.setFileName(file.getOriginalFilename());
        FileUtil.SaveFile(file, fileConfig.getFilepath(), fileName);
        fileInfoService.save(FileInfo.builder().path(fileConfig.getFilepath()+fileName).url(url).build());
        return Result.success(vo);
    }

    @GetMapping("/file/Image/{fileName}")
    public void Image(@PathVariable("fileName") String fileName) {
        fileName = fileConfig.getFilepath() + fileName;
        File file = new File(fileName);
        if (file.exists()){
            FileUtil.fileDownload(fileName);
        }
    }
    @GetMapping("/file/Image/packAll")
    public void packAll() throws IOException, InterruptedException {
        List<String> paths = this.fileInfoService.list().stream().map(FileInfo::getPath).collect(Collectors.toList());
        FileUtil.fileDownload(FileUtil.exportZip(paths, "打包下载.zip"));
    }

    @GetMapping("/file/Image/list")
    public Result Image(Page<FileInfo> page) {
        return Result.success(this.fileInfoService.page(page));
    }



}




