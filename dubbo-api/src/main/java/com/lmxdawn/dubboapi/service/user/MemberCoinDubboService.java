package com.lmxdawn.dubboapi.service.user;

/**
 * 用户钱包dubbo服务
 */
public interface MemberCoinDubboService {

    // 增加所有用户的钱包币种
    boolean createAll(Long coinId);

}
