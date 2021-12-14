package com.lmxdawn.wsroute.service;

import com.lmxdawn.wsroute.res.LoadBalancingIpRes;
import com.lmxdawn.wsroute.service.impl.LoadBalancingServiceImpl;

import java.util.Map;


public interface LoadBalancingService {

    Map<String, LoadBalancingServiceImpl.PortMap> getServerMap();

    LoadBalancingIpRes wsIpHash(String memberId);

}
