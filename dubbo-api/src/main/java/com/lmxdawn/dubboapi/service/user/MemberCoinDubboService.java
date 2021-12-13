package com.lmxdawn.dubboapi.service.user;

import com.lmxdawn.dubboapi.req.user.MemberCoinMatchDubboReq;
import com.lmxdawn.dubboapi.res.user.MemberCoinSimpleDubboRes;

/**
 * 用户钱包dubbo服务
 */
public interface MemberCoinDubboService {

    // 增加所有用户的钱包币种
    boolean createAll(Long coinId);

    // 查询账户余额
    MemberCoinSimpleDubboRes balance(Long memberId, Long coinId);

    // 冻结余额
    boolean frozenBalance(Long memberId, Long coinId, double money);

    // 撮合更新余额
    boolean matchBalance(MemberCoinMatchDubboReq req);

}
