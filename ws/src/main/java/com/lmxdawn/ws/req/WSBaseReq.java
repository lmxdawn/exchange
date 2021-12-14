package com.lmxdawn.ws.req;

import lombok.Data;

/**
 * websocket 通信的类
 */
@Data
public class WSBaseReq {

    /**
     * 类型（0: 心跳，1：用户登录，2：游客登录，3：推送行情，4：委托订单变化）
     */
    private Integer type;

    /**
     * 用户ID（包含游客登录的ID）
     */
    private String memberId;

    /**
     * 连接 wsId
     */
    private String wsId;

    /**
     * 内容（如果 type = 1 时，该值为token值）
     */
    private String data;

}
