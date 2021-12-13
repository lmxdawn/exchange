package com.lmxdawn.dubboapi.service.match;

import com.lmxdawn.dubboapi.req.match.MatchEventDubboReq;

/**
 * 撮合引擎
 */
public interface MatchDubboService {

    /**
     * 加入撮合队列
     */
    boolean push(MatchEventDubboReq req);

}
