package com.lmxdawn.market.service;

import com.lmxdawn.market.res.EntrustOrderRes;

import java.util.List;

public interface EntrustOrderService {

    List<EntrustOrderRes> listPage(Long memberId);

}
