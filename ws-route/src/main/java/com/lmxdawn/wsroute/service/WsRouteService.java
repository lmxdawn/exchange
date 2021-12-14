package com.lmxdawn.wsroute.service;

import com.lmxdawn.wsroute.res.ConnectionInfoRes;

public interface WsRouteService {
    ConnectionInfoRes connectionLogin(String memberId);
}
