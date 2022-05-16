package com.lmxdawn.other.dubbo.service;

import com.lmxdawn.dubboapi.service.other.CodeDubboService;
import com.lmxdawn.other.constant.CacheConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 验证码服务
 */
@DubboService
public class CodeDubboServiceImpl implements CodeDubboService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean verify(String str, Integer scene, String code) {

        String tt = scene + ":" + str;
        String key = String.format(CacheConstant.CODE_SEND, tt);
        String s = redisTemplate.opsForValue().get(key);
        // 不管验证成功还是失败，都让它失效，防止攻击，这里可以做验证失败多少次后失效
        redisTemplate.delete(key);
        if (StringUtils.isBlank(s)) {
            return false;
        }

        if (!s.equals(code)) {
            return false;
        }

        return true;
    }
}
