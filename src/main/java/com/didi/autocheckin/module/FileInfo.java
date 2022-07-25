package com.didi.autocheckin.module;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaidi
 */
@Data
@Builder
public class FileInfo {
    private String url;
    private String path;
}
