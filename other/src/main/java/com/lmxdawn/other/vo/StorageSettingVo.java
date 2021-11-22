package com.lmxdawn.other.vo;

import lombok.Data;

@Data
public class StorageSettingVo {
    private String domain;
    private String uploadUrl;
    private String accessKey;
    private String secretKey;
    private String bucket;

}
