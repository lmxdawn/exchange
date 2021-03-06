package com.lmxdawn.dubboapi.service.trade;

import com.lmxdawn.dubboapi.req.trade.EntrustOrderMatchDubboReq;
import com.lmxdawn.dubboapi.req.trade.EntrustOrderRobotCreateDubboReq;
import com.lmxdawn.dubboapi.res.trade.EntrustOrderMatchDubboRes;

import java.util.List;
import java.util.Map;

/**
 * 委托
 */
public interface EntrustOrderDubboService {

    Map<Long, EntrustOrderMatchDubboRes> mapByIdIn(List<Long> ids);


    boolean matchIncr(EntrustOrderMatchDubboReq req);

    /**
     * 机器人创建订单
     * @return
     */
    boolean robotCreate(EntrustOrderRobotCreateDubboReq req);

}
