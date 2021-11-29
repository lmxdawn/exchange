package com.lmxdawn.market.service.impl;

import com.lmxdawn.market.dao.EntrustOrderDao;
import com.lmxdawn.market.entity.EntrustOrder;
import com.lmxdawn.market.res.EntrustOrderRes;
import com.lmxdawn.market.service.EntrustOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrustOrderServiceImpl implements EntrustOrderService {

    @Autowired
    private EntrustOrderDao entrustOrderDao;

    @Override
    public List<EntrustOrderRes> listPage(Long memberId) {

        List<EntrustOrder> entrustOrders = entrustOrderDao.listPageByMemberId(memberId);

        // entrustOrders

        return null;
    }
}
