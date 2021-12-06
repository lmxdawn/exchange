package com.lmxdawn.trade.service.impl;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.user.MemberCoinDubboService;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.trade.dao.EntrustOrderDao;
import com.lmxdawn.trade.dao.SymbolDao;
import com.lmxdawn.trade.entity.EntrustOrder;
import com.lmxdawn.trade.entity.Symbol;
import com.lmxdawn.trade.enums.ResultEnum;
import com.lmxdawn.trade.exception.JsonException;
import com.lmxdawn.trade.req.EntrustOrderCreateReq;
import com.lmxdawn.trade.req.EntrustOrderListPageReq;
import com.lmxdawn.trade.res.EntrustOrderRes;
import com.lmxdawn.trade.service.EntrustOrderService;
import com.lmxdawn.trade.util.PageUtils;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrustOrderServiceImpl implements EntrustOrderService {

    @Autowired
    private EntrustOrderDao entrustOrderDao;

    @Autowired
    private SymbolDao symbolDao;

    @DubboReference
    private CoinDubboService coinDubboService;

    @DubboReference
    private MemberCoinDubboService memberCoinDubboService;

    @Override
    public List<EntrustOrderRes> listPage(EntrustOrderListPageReq req) {

        Integer offset = PageUtils.createOffset(req.getPage(), req.getLimit());
        req.setOffset(offset);

        List<EntrustOrder> entrustOrders = entrustOrderDao.listPageByMemberId(req);

        if (entrustOrders.size() == 0) {
            return new ArrayList<>();
        }

        Set<Long> coinIdSet = new HashSet<>();
        entrustOrders.forEach(v -> {
            coinIdSet.add(v.getTradeCoinId());
            coinIdSet.add(v.getCoinId());
        });
        List<Long> coinIds = new ArrayList<>(coinIdSet);

        Map<Long, CoinSimpleDubboRes> coinMap = coinDubboService.mapByCoinIds(coinIds);

        List<EntrustOrderRes> collect = entrustOrders.stream().map(v -> {
            EntrustOrderRes entrustOrderRes = new EntrustOrderRes();
            BeanUtils.copyProperties(v, entrustOrderRes);
            entrustOrderRes.setTradeCoin(coinMap.get(v.getTradeCoinId()));
            entrustOrderRes.setCoin(coinMap.get(v.getCoinId()));
            return entrustOrderRes;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    @GlobalTransactional
    public boolean create(EntrustOrderCreateReq req) {

        // 冻结余额
        boolean b = memberCoinDubboService.frozenBalance(req.getMemberId(), req.getFrozenCoinId(), req.getFrozenMoney());
        if (!b) {
            throw new RuntimeException("创建委托订单失败，用户余额不足");
        }

        EntrustOrder entrustOrder = new EntrustOrder();
        BeanUtils.copyProperties(req, entrustOrder);
        entrustOrder.setAmountComplete((double) 0);
        entrustOrder.setStatus(1);
        entrustOrder.setCreateTime(new Date());
        entrustOrder.setModifiedTime(new Date());
        boolean insert = entrustOrderDao.insert(entrustOrder);

        return insert;
    }


}
