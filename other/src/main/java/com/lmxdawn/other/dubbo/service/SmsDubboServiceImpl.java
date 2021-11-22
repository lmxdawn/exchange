package com.lmxdawn.other.dubbo.service;

import com.lmxdawn.dubboapi.service.other.SmsDubboService;
import com.lmxdawn.other.constant.CacheConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 配置服务
 */
@DubboService
public class SmsDubboServiceImpl implements SmsDubboService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean verify(String tel, Integer scene, String code) {

        String tt = scene + ":" + tel;
        String key = String.format(CacheConstant.SMS_SEND, tt);
        String s = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(s)) {
            return false;
        }

        if (!s.equals(code)) {
            return false;
        }

        redisTemplate.delete(key);

        return true;
    }
}
