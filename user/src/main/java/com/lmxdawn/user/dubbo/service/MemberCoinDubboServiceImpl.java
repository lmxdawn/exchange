package com.lmxdawn.user.dubbo.service;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.user.MemberCoinDubboService;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.user.dao.MemberCoinDao;
import com.lmxdawn.user.dao.MemberDao;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.entity.MemberCoin;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DubboService
public class MemberCoinDubboServiceImpl implements MemberCoinDubboService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberCoinDao memberCoinDao;

    @DubboReference
    private CoinDubboService coinDubboService;

    @Override
    public boolean createAll(Long coinId) {
        // 查询所有币种
        List<CoinSimpleDubboRes> coinSimpleDubboRes = coinDubboService.listAll();

        List<Member> members = memberDao.listAll();
        List<MemberCoin> insertBatch = new ArrayList<>();

        int maxInstall = 10000; // 每次批量数

        members.forEach(v -> {
            if (insertBatch.size() == maxInstall) {
                // 插入
                memberCoinDao.insertBatch(insertBatch);
                insertBatch.clear();
            }
            MemberCoin memberCoin = new MemberCoin();
            insertBatch.add(memberCoin);
        });

        return false;
    }
}
