package com.lmxdawn.market.ws;

import lombok.Data;

import java.util.List;

@Data
public class DataVo {

    /**
     * 交易币种ID
     */
    private Long tradeCoinId;

    /**
     * 计价币种ID
     */
    private Long coinId;

    /**
     * 买入深度
     */
    private List<DepthVo> buyList;

    /**
     * 卖出深度
     */
    private List<DepthVo> sellList;

    /**
     * 撮合信息
     */
    private MatchVo match;

}
