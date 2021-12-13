package com.lmxdawn.market.constant;

/**
 * redis 常量
 */
public interface CacheConstant {

    // 买盘深度
    String BUY_DEPTH = "buy_depth:%s";
    String BUY_DEPTH_INFO = "buy_depth:%s:%s";

    // 卖盘深度
    String SELL_DEPTH = "sell_depth:%s";
    String SELL_DEPTH_INFO = "sell_depth:%s:%s";

}
