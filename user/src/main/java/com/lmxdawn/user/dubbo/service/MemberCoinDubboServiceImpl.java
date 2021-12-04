package com.lmxdawn.user.dubbo.service;

import com.lmxdawn.dubboapi.res.user.MemberCoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.user.MemberCoinDubboService;
import com.lmxdawn.user.dao.MemberCoinDao;
import com.lmxdawn.user.dao.MemberDao;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.entity.MemberCoin;
import com.lmxdawn.user.service.MemberCoinService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DubboService
public class MemberCoinDubboServiceImpl implements MemberCoinDubboService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberCoinDao memberCoinDao;

    @Autowired
    private MemberCoinService memberCoinService;

    @Override
    public boolean createAll(Long coinId) {

        List<Member> members = memberDao.listAll();

        int maxInstall = 10000; // 每次批量数
        List<MemberCoin> insertBatch = new ArrayList<>();
        members.forEach(v -> {
            if (insertBatch.size() == maxInstall) {
                // 插入
                memberCoinDao.insertBatch(insertBatch);
                insertBatch.clear();
            }
            MemberCoin memberCoin = new MemberCoin();
            memberCoin.setMemberId(v.getMemberId());
            memberCoin.setCoinId(coinId);
            memberCoin.setBalance(0.00);
            memberCoin.setFrozenBalance(0.00);
            memberCoin.setStatus(1);
            memberCoin.setCreateTime(new Date());
            memberCoin.setModifiedTime(new Date());
            insertBatch.add(memberCoin);
        });

        // 还有没有插入的
        if (insertBatch.size() > 0) {
            memberCoinDao.insertBatch(insertBatch);
        }

        return true;
    }

    @Override
    public MemberCoinSimpleDubboRes balance(Long memberId, Long coinId) {
        MemberCoin byMemberIdCoinId = memberCoinService.findByMemberIdCoinId(memberId, coinId);
        MemberCoinSimpleDubboRes res = new MemberCoinSimpleDubboRes();
        if (byMemberIdCoinId == null) {
            return null;
        }
        BeanUtils.copyProperties(byMemberIdCoinId, res);
        return res;
    }

    @Override
    public boolean frozenBalance(Long memberId, Long coinId, double money) {
        return memberCoinService.frozenBalance(memberId, coinId, money);
    }


}
