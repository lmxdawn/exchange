package com.lmxdawn.wsroute.stream;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.wsroute.mq.WsMarketMq;
import com.lmxdawn.wsroute.service.LoadBalancingService;
import com.lmxdawn.wsroute.service.impl.LoadBalancingServiceImpl;
import com.lmxdawn.wsroute.util.OkHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@Service
public class WsMarketStream {

    @Autowired
    private LoadBalancingService loadBalancingService;

    /**
     * 监听行情推送消息
     */
    @Bean
    public Consumer<WsMarketMq> wsPush() {

        return wsMarketMq -> {

            Map<String, LoadBalancingServiceImpl.PortMap> serverMap = loadBalancingService.getServerMap();
            for (Map.Entry<String, LoadBalancingServiceImpl.PortMap> next : serverMap.entrySet()) {
                LoadBalancingServiceImpl.PortMap portMap = next.getValue();
                String url = portMap.getUrl();
                String httpPort = portMap.getHttpPort();
                url = "http://" + url + ":" + httpPort + "/push/market";
                // TODO 需要实现多线程调用
                OkHttpUtil.postJson(url, JSON.toJSONString(wsMarketMq));
            }


        };

    }


}
