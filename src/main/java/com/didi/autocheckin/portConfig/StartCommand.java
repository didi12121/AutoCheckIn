package com.didi.autocheckin.portConfig;

import com.didi.autocheckin.util.ServerPortUtil;
import org.springframework.util.StringUtils;

public class StartCommand {
    /**
     * 端口属性名称，如果名称为server.port则在配置文件中不用指定，否则需要指定为此处配置的名称，如${auto.port}
     */
    private static final String SERVER_PORT = "auto.port";

    public StartCommand(String[] args) {
        boolean isServerPort = false;
        String serverPort = "";
        if (args != null) {
            for (String arg : args) {
                if (StringUtils.hasText(arg) && arg.startsWith("--server.port" )) {
                    isServerPort = true;
                    serverPort = arg;
                    break;
                }
            }
        }

        String port;
        if (isServerPort) {
            port = serverPort.split("=")[1];
        } else {
            port = ServerPortUtil.getAvailablePort();
        }
        System.out.println("Current project port is " + port);
        System.setProperty(SERVER_PORT, port);
    }
}

