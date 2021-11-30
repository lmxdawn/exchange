package com.lmxdawn.user.service;

import com.lmxdawn.user.entity.MemberCoin;
import com.lmxdawn.user.res.MemberCoinRes;

import java.util.List;

public interface MemberCoinService {

    List<MemberCoinRes> listAllByMemberId(Long memberId);

    // 获取
    MemberCoin findByMemberIdCoinId(Long memberId, Long coinId);

    // 冻结余额
    boolean frozenBalance(Long memberId, Long coinId, double money);

}
