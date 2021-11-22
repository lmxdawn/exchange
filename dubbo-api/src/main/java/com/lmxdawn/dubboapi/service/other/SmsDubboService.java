package com.lmxdawn.dubboapi.service.other;


/**
 * 短信
 */
public interface SmsDubboService {

    boolean verify(String tel, Integer scene, String code);

}
