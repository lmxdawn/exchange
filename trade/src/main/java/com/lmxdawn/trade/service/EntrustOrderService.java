package com.lmxdawn.trade.service;

import com.lmxdawn.trade.req.EntrustOrderCreateReq;
import com.lmxdawn.trade.req.EntrustOrderListPageReq;
import com.lmxdawn.trade.res.EntrustOrderRes;

import java.util.List;

public interface EntrustOrderService {

    List<EntrustOrderRes> listPage(EntrustOrderListPageReq req);

    boolean create(EntrustOrderCreateReq req);

}
