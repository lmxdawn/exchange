package com.lmxdawn.other.constant;

/**
 * 发送短信的常量（场景（1：注册，2：登录，3：绑定邮箱，4：绑定手机，5：找回登录密码，6：设置支付密码，7：找回支付密码，8：绑定谷歌验证码，9：找回谷歌验证码））
 */
public interface CodeConstant {

    //**************************
    // 平台
    //**************************
    /**
     * 华为云短信
     */
    Integer HUAWEI_PLATFORM_SMS = 1;
    /**
     * 阿里云短信
     */
    Integer ALI_PLATFORM_SMS = 2;

    /**
     * 阿里云短信
     */
    Integer EMAIL = 3;

}
