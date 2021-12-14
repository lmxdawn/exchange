package com.lmxdawn.ws.res;

import lombok.Data;

/**
 * websocket 通信的类
 */
@Data
public class WSBaseRes {

    /**
     * 返回类型（0：心跳，1：登录成功的返回，2：行情的推送，3：委托订单变化）
     */
    private Integer type;

    /**
     * 返回的ws的ID
     */
    private String wsId;

    /**
     * 返回的数据
     */
    private String data;

}
