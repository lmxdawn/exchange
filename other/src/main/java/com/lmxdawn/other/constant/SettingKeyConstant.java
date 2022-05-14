package com.lmxdawn.other.constant;

/**
 * 配置表的常量key
 */
public interface SettingKeyConstant {

    /**
     * 运营相关
     */
    Integer OPERATE_MODULE = 1;
    String OPERATE_REGISTER_GIVE_COIN = "register_give_coin"; // 注册时送金币
    String OPERATE_REWARD_FEE_RATE = "reward_fee_rate"; // 打赏手续费率

    /**
     * 文件存储
     */
    Integer STORAGE_MODULE = 2;
    String STORAGE_DOMAIN = "domain"; // 文件的访问域名
    String STORAGE_UPLOAD_URL = "upload_url"; // 上传域名
    String STORAGE_ACCESS_KEY = "access_key"; // access_key
    String STORAGE_SECRET_KEY = "secret_key"; // secret_key
    String STORAGE_BUCKET = "bucket"; // 空间名称

    /**
     * 华为云短信
     */
    Integer HUA_WEI_SMS_MODULE = 3;
    String HUA_WEI_SMS_ACCESS_KEY = "sms_access_key";
    String HUA_WEI_SMS_APP_SECRET = "sms_app_secret";
    String HUA_WEI_SMS_SEND_URL = "sms_send_url";
    String HUA_WEI_SMS_SENDER = "sms_sender";
    String HUA_WEI_SMS_SIGNATURE = "sms_signature";

    /**
     * app配置
     */
    Integer APP_MODULE = 4;
    String APP_IOS_DOWNLOAD_URL = "ios_download_url"; //iOS下载链接
    String APP_ANDROID_DOWNLOAD_URL = "android_download_url"; //Android下载链接
    String APP_H5_URL = "h5_url"; //H5域名

    /**
     * 邮件配置
     */
    Integer EMAIL = 5;
    String EMAIL_HOST = "email_host"; // 邮件服务主机
    String EMAIL_USERNAME = "email_username"; // 邮件服务用户名
    String EMAIL_PASSWORD = "email_password"; // 邮件服务密码

}
