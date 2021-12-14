package com.lmxdawn.wsroute.service.impl;

import com.lmxdawn.wsroute.res.LoadBalancingIpRes;
import com.lmxdawn.wsroute.service.LoadBalancingService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoadBalancingServiceImpl implements LoadBalancingService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public Map<String, PortMap> getServerMap() {
        List<ServiceInstance> instances = discoveryClient.getInstances("service-ws");
        // 负载均衡
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, PortMap> serverMap = new HashMap<>();

        int serverMapIndex = 0;
        for (ServiceInstance i : instances) {
            Map<String, String> metadata = i.getMetadata();
            String url = "";
            String wsPort = "";
            String httpPort = "";
            for (String ii : metadata.keySet()) {
                if (ii.equals("server-port")) {
                    httpPort = metadata.get(ii);
                } else if (ii.equals("ws-port")) {
                    wsPort = metadata.get(ii);
                } else if (ii.equals("ws-url")) {
                    url = metadata.get(ii);
                }
            }
            if (!wsPort.isEmpty() && !httpPort.isEmpty() && !url.isEmpty()) {
                PortMap portMap = new PortMap();
                portMap.setIp(i.getHost());
                portMap.setUrl(url);
                portMap.setHttpPort(httpPort);
                portMap.setWsPort(wsPort);
                serverMap.put(String.valueOf(serverMapIndex), portMap);
                serverMapIndex++;
            }
        }
        return serverMap;
    }

    @Override
    public LoadBalancingIpRes wsIpHash(String memberId) {
        Map<String, PortMap> serverMap = getServerMap();
        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<>(keySet);

        int serverListSize = keyList.size();
        int serverPos = memberId.hashCode() % serverListSize;

        String index = keyList.get(serverPos);
        PortMap portMap = serverMap.get(index);

        LoadBalancingIpRes loadBalancingIpRes = new LoadBalancingIpRes();
        loadBalancingIpRes.setUrl(portMap.getUrl());
        loadBalancingIpRes.setHttpPort(portMap.getHttpPort());
        loadBalancingIpRes.setWsPort(portMap.getWsPort());
        return loadBalancingIpRes;
    }


    @Data
    public static class PortMap {
        private String ip;
        private String url;
        private String wsPort;
        private String httpPort;
    }
}
