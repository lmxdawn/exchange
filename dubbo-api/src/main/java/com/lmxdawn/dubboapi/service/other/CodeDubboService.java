package com.lmxdawn.dubboapi.service.other;


/**
 * 验证码服务
 */
public interface CodeDubboService {

    boolean verify(String str, Integer scene, String code);

}
