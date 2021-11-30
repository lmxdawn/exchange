package com.lmxdawn.user.service.impl;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.user.dao.MemberCoinDao;
import com.lmxdawn.user.entity.MemberCoin;
import com.lmxdawn.user.res.MemberCoinRes;
import com.lmxdawn.user.service.MemberCoinService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class MemberCoinServiceImpl implements MemberCoinService {

    @Autowired
    private MemberCoinDao memberCoinDao;

    @DubboReference
    private CoinDubboService coinDubboService;

    @Override
    public List<MemberCoinRes> listAllByMemberId(Long memberId) {

        List<CoinSimpleDubboRes> coinSimpleDubboRes = coinDubboService.listAll();

        List<MemberCoin> memberCoins = memberCoinDao.listAllByMemberId(memberId);

        Map<Long, MemberCoin> memberCoinMap = new HashMap<>();
        memberCoins.forEach(v -> {
            memberCoinMap.put(v.getCoinId(), v);
        });

        List<MemberCoinRes> list = new ArrayList<>();
        List<MemberCoin> insertBatch = new ArrayList<>();
        coinSimpleDubboRes.forEach(v -> {
            MemberCoinRes memberCoinRes = new MemberCoinRes();
            Long coinId = v.getId();
            MemberCoin memberCoin = memberCoinMap.get(coinId);
            if (memberCoin == null) {
                memberCoin = new MemberCoin();
                memberCoin.setMemberId(memberId);
                memberCoin.setCoinId(coinId);
                memberCoin.setBalance(0.00);
                memberCoin.setFrozenBalance(0.00);
                memberCoin.setStatus(1);
                memberCoin.setCreateTime(new Date());
                memberCoin.setModifiedTime(new Date());
                // 没有该币种则添加该币种
                insertBatch.add(memberCoin);
            }
            BeanUtils.copyProperties(memberCoin, memberCoinRes);
            memberCoinRes.setCoin(v);
            list.add(memberCoinRes);
        });

        if (insertBatch.size() > 0) {
            memberCoinDao.insertBatch(insertBatch);
        }

        return list;
    }
}
