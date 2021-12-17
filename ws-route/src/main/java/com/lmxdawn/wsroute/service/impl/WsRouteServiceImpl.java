package com.lmxdawn.wsroute.service.impl;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.wsroute.constant.CacheConstant;
import com.lmxdawn.wsroute.res.ConnectionInfoRes;
import com.lmxdawn.wsroute.res.LoadBalancingIpRes;
import com.lmxdawn.wsroute.service.LoadBalancingService;
import com.lmxdawn.wsroute.service.WsRouteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class WsRouteServiceImpl implements WsRouteService {

    @Autowired
    private LoadBalancingService loadBalancingService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ConnectionInfoRes connectionLogin(String memberId) {

        String key = String.format(CacheConstant.WS_ROUTE_MEMBER_ID, memberId);
        String value = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isBlank(value)) {
            return JSON.parseObject(value, ConnectionInfoRes.class);
        }

        ConnectionInfoRes connectionInfoRes = new ConnectionInfoRes();
        // 负载均衡
        LoadBalancingIpRes loadBalancingIpRes = loadBalancingService.wsIpHash(memberId);
        connectionInfoRes.setMemberId(memberId);
        connectionInfoRes.setUrl(loadBalancingIpRes.getUrl());
        connectionInfoRes.setHttpPort(loadBalancingIpRes.getHttpPort());
        connectionInfoRes.setWsPort(loadBalancingIpRes.getWsPort());

        String str = JSON.toJSONString(connectionInfoRes);
        redisTemplate.opsForValue().set(key, str);

        return connectionInfoRes;

    }
}
