package com.didi.autocheckin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "wb")
public class WbConfig {

    @Value("${wb.cookie}")
    private String cookie;

    @Value("${wb.pid}")
    private List<String> pids;

}
