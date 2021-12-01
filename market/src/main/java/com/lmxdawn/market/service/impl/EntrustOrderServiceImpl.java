package com.lmxdawn.market.service.impl;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.user.MemberCoinDubboService;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.market.dao.EntrustOrderDao;
import com.lmxdawn.market.entity.EntrustOrder;
import com.lmxdawn.market.req.EntrustOrderCreateReq;
import com.lmxdawn.market.req.EntrustOrderListPageReq;
import com.lmxdawn.market.res.EntrustOrderRes;
import com.lmxdawn.market.service.EntrustOrderService;
import com.lmxdawn.market.util.PageUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntrustOrderServiceImpl implements EntrustOrderService {

    @Autowired
    private EntrustOrderDao entrustOrderDao;

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
    public boolean create(EntrustOrderCreateReq req) {

        // 获取交易对配置

        BigDecimal bigPrice = new BigDecimal(req.getPrice() + "");
        BigDecimal bigAmount = new BigDecimal(req.getAmount() + "");

        BigDecimal bigMoney = bigAmount.multiply(bigPrice);
        // TODO 冻结余额
        memberCoinDubboService.frozenBalance(req.getMemberId(), req.getCoinId(), bigMoney.doubleValue());

        EntrustOrder entrustOrder = new EntrustOrder();
        BeanUtils.copyProperties(req, entrustOrder);
        entrustOrder.setAmountComplete((double) 0);
        entrustOrder.setStatus(1);
        entrustOrder.setCreateTime(new Date());
        entrustOrder.setModifiedTime(new Date());


        return entrustOrderDao.insert(entrustOrder);
    }
}
