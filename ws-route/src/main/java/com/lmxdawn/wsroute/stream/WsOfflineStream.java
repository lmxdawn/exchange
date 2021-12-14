package com.lmxdawn.wsroute.stream;

import com.lmxdawn.wsroute.mq.WsOfflineMq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class WsOfflineStream {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * ws 下线通知
     */
    @Bean
    public Consumer<WsOfflineMq> wsOffline() {

        return wsOfflineMq -> {


        };

    }


}
