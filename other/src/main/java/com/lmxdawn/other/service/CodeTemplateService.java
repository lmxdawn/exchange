package com.lmxdawn.other.service;

import com.lmxdawn.other.entity.CodeTemplate;


public interface CodeTemplateService {

    /**
     * 根据模块获取华为云短信的全部配置，返回一个对象
     * @return
     */
    CodeTemplate find(Integer platform, Integer scene, String lang);

}
