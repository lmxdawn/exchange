package com.lmxdawn.market.ws;

import lombok.Data;

import java.util.List;

@Data
public class DataVo {

    private List<DepthVo> depthVoList;

    private MatchVo matchVo;

}
