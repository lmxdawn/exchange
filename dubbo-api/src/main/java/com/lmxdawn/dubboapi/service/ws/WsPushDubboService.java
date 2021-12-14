package com.lmxdawn.dubboapi.service.ws;

import com.lmxdawn.dubboapi.req.ws.WsMarketDubboReq;

/**
 * ws 消息推送
 */
public interface WsPushDubboService {

    /**
     * 推送行情
     */
    boolean market(WsMarketDubboReq req);

}
