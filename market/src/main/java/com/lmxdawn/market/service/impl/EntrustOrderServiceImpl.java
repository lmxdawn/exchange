package com.lmxdawn.market.service.impl;

import com.lmxdawn.market.dao.EntrustOrderDao;
import com.lmxdawn.market.entity.EntrustOrder;
import com.lmxdawn.market.req.EntrustOrderCreateReq;
import com.lmxdawn.market.req.EntrustOrderListPageReq;
import com.lmxdawn.market.res.EntrustOrderRes;
import com.lmxdawn.market.service.EntrustOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EntrustOrderServiceImpl implements EntrustOrderService {

    @Autowired
    private EntrustOrderDao entrustOrderDao;

    @Override
    public List<EntrustOrderRes> listPage(EntrustOrderListPageReq req) {

        List<EntrustOrder> entrustOrders = entrustOrderDao.listPageByMemberId(req);

        // entrustOrders

        return null;
    }

    @Override
    public boolean create(EntrustOrderCreateReq req) {

        EntrustOrder entrustOrder = new EntrustOrder();
        BeanUtils.copyProperties(req, entrustOrder);
        entrustOrder.setAmountComplete((double) 0);
        entrustOrder.setStatus(1);
        entrustOrder.setCreateTime(new Date());
        entrustOrder.setModifiedTime(new Date());

        return entrustOrderDao.insert(entrustOrder);
    }
}
