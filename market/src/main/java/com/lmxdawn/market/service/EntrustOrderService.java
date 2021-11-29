package com.lmxdawn.market.service;

import com.lmxdawn.market.req.EntrustOrderCreateReq;
import com.lmxdawn.market.req.EntrustOrderListPageReq;
import com.lmxdawn.market.res.EntrustOrderRes;

import java.util.List;

public interface EntrustOrderService {

    List<EntrustOrderRes> listPage(EntrustOrderListPageReq req);

    boolean create(EntrustOrderCreateReq req);

}
