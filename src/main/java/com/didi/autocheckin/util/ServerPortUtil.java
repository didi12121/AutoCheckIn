package com.didi.autocheckin.util;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class ServerPortUtil {
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 8000;

    public static String getAvailablePort() {
        Random random = new Random();
        // 最大尝试次数为端口范围
        int maxRetryCount = MAX_PORT - MIN_PORT;
        while (maxRetryCount > 0) {
            // 指定范围内随机端口，随便写的算法，根据自己需要调整
            int port = random.nextInt(MAX_PORT - MIN_PORT) + MIN_PORT;
            boolean isUsed = isLocalePortUsing(port);
            if (!isUsed) {
                return String.valueOf(port);
            }
            --maxRetryCount;
        }
        System.out.println("系统暂无端口可用，运行结束");
        System.exit(1);
        return null;
    }

    /**
     * 检查给定端口是否可用
     *
     * @author tianxincode@163.com
     * @since 2020/10/14
     */
    public static boolean isLocalePortUsing(int port) {
        try {
            // 建立一个Socket连接, 如果该端口还在使用则返回true, 否则返回false, 127.0.0.1代表本机
            new Socket(InetAddress.getByName("127.0.0.1"), port);
            return true;
        } catch (Exception e) {
            // 异常说明端口连接不上，端口能使用
        }
        return false;
    }
}

