package com.didi.autocheckin;

import com.alibaba.fastjson.JSONObject;
import com.didi.autocheckin.config.WbConfig;
import com.didi.autocheckin.portConfig.StartCommand;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author didi
 */
@SpringBootApplication
@Slf4j
public class AutoCheckInApplication {

    @Resource
    private WbConfig wbConfig;
    public static void main(String[] args) {
        new StartCommand(args);
        SpringApplication.run(AutoCheckInApplication.class, args);
    }

    @Scheduled(cron = "0 0  0 * * ?")
    @PostConstruct
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
        JSONObject dataJsonObject = JSONObject.parseObject(data);
        Integer code = dataJsonObject.getInteger("code");
        if (code.equals(scCode)) {
            log.info("签到成功");
        }else {
            log.info(dataJsonObject.getString("msg"));
        }
        log.info("结束");
    }
}
