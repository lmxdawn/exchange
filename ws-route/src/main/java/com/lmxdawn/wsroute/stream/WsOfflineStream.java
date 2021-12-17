package com.lmxdawn.wsroute.stream;

import com.lmxdawn.wsroute.constant.CacheConstant;
import com.lmxdawn.wsroute.mq.WsOfflineMq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class WsOfflineStream {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * ws 下线通知
     */
    @Bean
    public Consumer<WsOfflineMq> wsOffline() {

        return wsOfflineMq -> {

            String memberId = wsOfflineMq.getMemberId();

            log.info("用户下线：{}", memberId);

            String key = String.format(CacheConstant.WS_ROUTE_MEMBER_ID, memberId);

            redisTemplate.delete(key);

        };

    }


}
