package com.lmxdawn.ws.dubbo.service;

import com.lmxdawn.dubboapi.req.ws.WsMarketDubboReq;
import com.lmxdawn.dubboapi.service.ws.WsPushDubboService;
import com.lmxdawn.ws.res.WSBaseRes;
import com.lmxdawn.ws.ws.WSServer;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class WsPushDubboServiceImpl implements WsPushDubboService {

    @Autowired
    private WSServer wsServer;

    @Override
    public boolean market(WsMarketDubboReq req) {
        Long memberId = req.getMemberId();
        Long orderId = req.getOrderId();
        if (memberId != null) {
            WSBaseRes orderWsBaseRes = new WSBaseRes();
            orderWsBaseRes.setType(3);
            orderWsBaseRes.setData(orderId.toString());
            wsServer.sendMsg(memberId.toString(), orderWsBaseRes);
        }
        WSBaseRes wsBaseRes = new WSBaseRes();
        wsBaseRes.setType(2);
        wsBaseRes.setData(req.getData());
        return wsServer.full(wsBaseRes);
    }
}
