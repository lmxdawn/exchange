package com.lmxdawn.dubboapi.service.user;

/**
 * 用户钱包dubbo服务
 */
public interface MemberCoinDubboService {

    // 增加所有用户的钱包币种
    boolean createAll(Long coinId);

    // 冻结余额
    boolean frozenBalance(Long memberId, Long coinId, double money);

}
