package com.didi.autocheckin.module;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploaderVO {
    private String name;
    private String fileName;
    private String url;
}
