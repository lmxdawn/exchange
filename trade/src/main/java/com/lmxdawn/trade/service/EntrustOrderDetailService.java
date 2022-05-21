package com.lmxdawn.trade.service;

import com.lmxdawn.trade.req.EntrustOrderDetailReq;
import com.lmxdawn.trade.res.EntrustOrderDetailRes;

import java.util.List;

public interface EntrustOrderDetailService {

    List<EntrustOrderDetailRes> listPageByMemberIdAndOrderId(EntrustOrderDetailReq req);

}
