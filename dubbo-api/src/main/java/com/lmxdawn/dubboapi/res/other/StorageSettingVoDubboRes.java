package com.lmxdawn.dubboapi.res.other;

import lombok.Data;

import java.io.Serializable;

@Data
public class StorageSettingVoDubboRes implements Serializable {
    private String domain;
    private String uploadUrl;
    private String accessKey;
    private String secretKey;
    private String bucket;
}
