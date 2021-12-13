package com.lmxdawn.market.service;

import com.lmxdawn.dubboapi.req.trade.EntrustOrderMatchDubboReq;
import com.lmxdawn.dubboapi.req.user.MemberCoinMatchDubboReq;

public interface MatchService {
    /**
     * 撮合完成
     */
    boolean complete(MemberCoinMatchDubboReq memberCoinMatchDubboReq, EntrustOrderMatchDubboReq entrustOrderMatchDubboReq);
}
