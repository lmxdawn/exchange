package com.lmxdawn.user.constant;

/**
 * 验证码的场景的常量（场景（1：注册，2：登录，3：绑定邮箱，4：绑定手机，5：找回登录密码，6：设置支付密码，7：找回支付密码，8：绑定谷歌验证码，9：找回谷歌验证码））
 */
public interface CodeSceneConstant {

    Integer REGISTER = 1; // 注册
    Integer LOGIN = 2; // 登录
    Integer BIND_EMAIL = 3; // 绑定邮箱
    Integer BIND_TEL = 4; // 绑定手机
    Integer FORGET_PWD = 5; // 找回登录密码
    Integer SET_PAY_PWD = 6; // 设置支付密码
    Integer FORGET_PAY_PWD = 7; // 找回支付密码
    Integer BIND_GOOGLE_KEY = 8; // 绑定谷歌验证码
    Integer FORGET_GOOGLE_KEY = 9; // 找回谷歌验证码

}
