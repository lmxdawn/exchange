package com.lmxdawn.other.vo;

import lombok.Data;

@Data
public class EmailSendVo {
    private String emailHost;
    private String emailUsername;
    private String emailPassword;
    // 发件人
    private String from;
    // 收件人
    private String to;
    // 主题
    private String subject;
    // 内容
    private String text;

}
