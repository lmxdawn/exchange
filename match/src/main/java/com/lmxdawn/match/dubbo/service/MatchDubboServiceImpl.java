package com.lmxdawn.match.dubbo.service;

import com.lmxdawn.dubboapi.req.match.MatchEventDubboReq;
import com.lmxdawn.dubboapi.service.match.MatchDubboService;
import com.lmxdawn.match.disruptor.MatchEvent;
import com.lmxdawn.match.service.MatchService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class MatchDubboServiceImpl implements MatchDubboService {

    @Autowired
    private MatchService matchService;

    @Override
    public boolean push(MatchEventDubboReq req) {

        MatchEvent matchEvent = new MatchEvent();
        BeanUtils.copyProperties(req, matchEvent);
        matchService.publish(matchEvent);

        return false;
    }
}
