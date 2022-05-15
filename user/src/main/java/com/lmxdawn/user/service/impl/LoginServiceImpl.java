package com.lmxdawn.user.service.impl;

import com.lmxdawn.user.util.JwtUtils;
import com.lmxdawn.user.service.LoginService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.lmxdawn.user.constant.CacheConstant.USER_LOGIN;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String createToken(Long memberId) {
        String key = String.format(USER_LOGIN, memberId);
        String token = redisTemplate.opsForValue().get(key);
        if (token == null || token.isEmpty()) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("memberId", memberId);
            claims.put("t", System.currentTimeMillis());
            token = JwtUtils.createToken(claims);
            redisTemplate.opsForValue().set(key, token);
        }
        return token;
    }

    @Override
    public Long verify(String token) {
        // 验证 token
        Claims claims = JwtUtils.parse(token);
        if (claims == null) {
            return null;
        }
        long memberId;
        try {
            memberId = Long.parseLong(claims.get("memberId").toString());
        }catch (Exception e) {
            return null;
        }
        if (memberId <= 0) {
            return null;
        }

        String key = String.format(USER_LOGIN, memberId);
        String s = redisTemplate.opsForValue().get(key);
        if (s == null || s.isEmpty() || !s.equals(token)) {
            return null;
        }
        return memberId;
    }

    @Override
    public void delete(Long memberId) {
        String key = String.format(USER_LOGIN, memberId);
        redisTemplate.delete(key);
    }
}
