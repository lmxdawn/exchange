package com.lmxdawn.other.vo;

import lombok.Data;

@Data
public class HuaWeiSmsSendVo {
    private String smsAccessKey;
    private String smsAppSecret;
    private String smsSendUrl;
    private String smsSender;
    private String smsSignature;
    private String templateId;
    private String templateParas;
    private String receiver;

}
