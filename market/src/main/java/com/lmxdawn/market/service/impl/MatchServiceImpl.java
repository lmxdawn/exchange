package com.lmxdawn.market.service.impl;

import com.lmxdawn.dubboapi.req.trade.EntrustOrderMatchDubboReq;
import com.lmxdawn.dubboapi.req.user.MemberCoinMatchDubboReq;
import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.dubboapi.service.user.MemberCoinDubboService;
import com.lmxdawn.market.service.MatchService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {

    @DubboReference
    private MemberCoinDubboService memberCoinDubboService;

    @DubboReference
    private EntrustOrderDubboService entrustOrderDubboService;

    @Override
    @GlobalTransactional
    public boolean complete(MemberCoinMatchDubboReq memberCoinMatchDubboReq, EntrustOrderMatchDubboReq entrustOrderMatchDubboReq) {

        System.out.println("用户余额");
        System.out.println(memberCoinMatchDubboReq);
        System.out.println(entrustOrderMatchDubboReq);

        boolean b = memberCoinDubboService.matchBalance(memberCoinMatchDubboReq);
        if (!b) {
            throw new RuntimeException();
        }

        boolean b1 = entrustOrderDubboService.matchIncr(entrustOrderMatchDubboReq);

        if (!b1) {
            throw new RuntimeException();
        }

        return true;
    }
}
