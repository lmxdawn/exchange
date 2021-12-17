package com.lmxdawn.user.dubbo.service;

import com.lmxdawn.dubboapi.req.user.MemberCoinMatchDubboReq;
import com.lmxdawn.dubboapi.res.user.MemberCoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.user.MemberCoinDubboService;
import com.lmxdawn.user.dao.MemberBillDao;
import com.lmxdawn.user.dao.MemberCoinDao;
import com.lmxdawn.user.dao.MemberDao;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.entity.MemberBill;
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

    @Autowired
    private MemberBillDao memberBillDao;

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

    @Override
    public boolean matchBalance(MemberCoinMatchDubboReq req) {

        Long tradeCoinId = req.getTradeCoinId(); // 交易币种
        Long coinId = req.getCoinId(); // 计价币种
        Long buyMemberId = req.getBuyMemberId();
        Double buyMoney = req.getBuyMoney();
        Double buyMoneyFee = req.getBuyMoneyFee();
        Double buyUnfrozenMoney = req.getBuyUnfrozenMoney();
        Long sellMemberId = req.getSellMemberId();
        Double sellMoney = req.getSellMoney();
        Double sellMoneyFee = req.getSellMoneyFee();
        Double sellUnfrozenMoney = req.getSellUnfrozenMoney();

        String billCategory = "trade";
        // 增加明细
        List<MemberBill> memberBillList = new ArrayList<>();

        // 增加买入
        MemberCoin buyIncr = new MemberCoin();
        buyIncr.setMemberId(buyMemberId);
        buyIncr.setCoinId(tradeCoinId);
        buyIncr.setBalance(buyMoney);
        boolean b = memberCoinDao.incrBalance(buyIncr);
        if (!b) {
            return false;
        }
        // 增加买入的明细
        MemberBill buyMemberBill = new MemberBill();
        buyMemberBill.setMemberId(buyMemberId);
        buyMemberBill.setCoinId(tradeCoinId);
        buyMemberBill.setCategory(billCategory);
        buyMemberBill.setType(1);
        buyMemberBill.setMoney(buyMoney);
        buyMemberBill.setFee(buyMoneyFee);
        buyMemberBill.setRemark("币币交易买入");
        buyMemberBill.setCreateDate(new Date());
        buyMemberBill.setCreateTime(new Date());
        buyMemberBill.setModifiedTime(new Date());
        memberBillList.add(buyMemberBill);

        // 解冻买入
        MemberCoin buyUnfrozen = new MemberCoin();
        buyUnfrozen.setMemberId(buyMemberId);
        buyUnfrozen.setCoinId(coinId);
        buyUnfrozen.setFrozenBalance(buyUnfrozenMoney);
        boolean unfrozen = memberCoinDao.unfrozen(buyUnfrozen);
        if (!unfrozen) {
            return false;
        }
        // 增加解冻买入的明细
        MemberBill buyUnfrozenMemberBill = new MemberBill();
        buyUnfrozenMemberBill.setMemberId(buyMemberId);
        buyUnfrozenMemberBill.setCoinId(coinId);
        buyUnfrozenMemberBill.setCategory(billCategory);
        buyUnfrozenMemberBill.setType(2);
        buyUnfrozenMemberBill.setMoney(buyUnfrozenMoney);
        buyUnfrozenMemberBill.setFee(0.00);
        buyUnfrozenMemberBill.setRemark("币币交易解冻买入");
        buyUnfrozenMemberBill.setCreateDate(new Date());
        buyUnfrozenMemberBill.setCreateTime(new Date());
        buyUnfrozenMemberBill.setModifiedTime(new Date());
        memberBillList.add(buyUnfrozenMemberBill);

        // 增加卖出
        MemberCoin sellIncr = new MemberCoin();
        sellIncr.setMemberId(sellMemberId);
        sellIncr.setCoinId(coinId);
        sellIncr.setBalance(sellMoney);
        boolean b1 = memberCoinDao.incrBalance(sellIncr);
        if (!b1) {
            return false;
        }
        // 增加卖出的明细
        MemberBill sellMemberBill = new MemberBill();
        sellMemberBill.setMemberId(sellMemberId);
        sellMemberBill.setCoinId(coinId);
        sellMemberBill.setCategory(billCategory);
        sellMemberBill.setType(1);
        sellMemberBill.setMoney(sellMoney);
        sellMemberBill.setFee(sellMoneyFee);
        sellMemberBill.setRemark("币币交易卖出");
        sellMemberBill.setCreateDate(new Date());
        sellMemberBill.setCreateTime(new Date());
        sellMemberBill.setModifiedTime(new Date());
        memberBillList.add(sellMemberBill);

        // 解冻卖出
        MemberCoin sellUnfrozen = new MemberCoin();
        sellUnfrozen.setMemberId(sellMemberId);
        sellUnfrozen.setCoinId(tradeCoinId);
        sellUnfrozen.setFrozenBalance(sellUnfrozenMoney);
        boolean b2 = memberCoinDao.unfrozen(sellUnfrozen);
        if (!b2) {
            return false;
        }

        // 增加解冻卖出的明细
        MemberBill sellUnfrozenMemberBill = new MemberBill();
        sellUnfrozenMemberBill.setMemberId(sellMemberId);
        sellUnfrozenMemberBill.setCoinId(tradeCoinId);
        sellUnfrozenMemberBill.setCategory(billCategory);
        sellUnfrozenMemberBill.setType(2);
        sellUnfrozenMemberBill.setMoney(sellUnfrozenMoney);
        sellUnfrozenMemberBill.setFee(0.00);
        sellUnfrozenMemberBill.setRemark("币币交易解冻卖出");
        sellUnfrozenMemberBill.setCreateDate(new Date());
        sellUnfrozenMemberBill.setCreateTime(new Date());
        sellUnfrozenMemberBill.setModifiedTime(new Date());
        memberBillList.add(sellUnfrozenMemberBill);

        return memberBillDao.insertBatch(memberBillList);
    }


}
