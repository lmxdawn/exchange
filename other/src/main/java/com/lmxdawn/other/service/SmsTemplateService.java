package com.lmxdawn.other.service;

import com.lmxdawn.other.entity.SmsTemplate;


public interface SmsTemplateService {

    /**
     * 根据模块获取华为云短信的全部配置，返回一个对象
     * @return
     */
    SmsTemplate find(Integer platform, Integer scene, String lang);

}
