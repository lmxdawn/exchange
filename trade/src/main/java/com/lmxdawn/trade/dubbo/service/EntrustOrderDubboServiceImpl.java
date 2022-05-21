package com.lmxdawn.trade.dubbo.service;

import com.lmxdawn.dubboapi.req.trade.EntrustOrderMatchDubboReq;
import com.lmxdawn.dubboapi.res.trade.EntrustOrderMatchDubboRes;
import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.trade.dao.EntrustOrderDao;
import com.lmxdawn.trade.dao.EntrustOrderDetailDao;
import com.lmxdawn.trade.entity.EntrustOrder;
import com.lmxdawn.trade.entity.EntrustOrderDetail;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@DubboService
public class EntrustOrderDubboServiceImpl implements EntrustOrderDubboService {

    @Autowired
    private EntrustOrderDao entrustOrderDao;

    @Autowired
    private EntrustOrderDetailDao entrustOrderDetailDao;

    @Override
    public Map<Long, EntrustOrderMatchDubboRes> mapByIdIn(List<Long> ids) {

        List<EntrustOrder> entrustOrders = entrustOrderDao.listByIdIn(ids);
        Map<Long, EntrustOrderMatchDubboRes> map = new HashMap<>();
        if (entrustOrders.size() == 0) {
            return map;
        }

        for (EntrustOrder v : entrustOrders) {
            EntrustOrderMatchDubboRes res = new EntrustOrderMatchDubboRes();
            BeanUtils.copyProperties(v, res);
            map.put(v.getId(), res);
        }

        return map;
    }

    @Override
    public boolean matchIncr(EntrustOrderMatchDubboReq req) {

        // 更新订单
        EntrustOrder entrustOrder = new EntrustOrder();
        entrustOrder.setId(req.getId());
        entrustOrder.setAmountComplete(req.getAmountComplete());
        entrustOrder.setAmount(req.getAmount());
        entrustOrder.setTotalComplete(req.getTotal());
        entrustOrder.setTotalFee(req.getFee());
        if (req.getStatus() != null && req.getStatus() == 2) {
            entrustOrder.setStatus(2);
        } else {
            entrustOrder.setStatus(1);
        }
        boolean incr = entrustOrderDao.incr(entrustOrder);
        if (!incr) {
            return false;
        }

        // 更新对手订单
        EntrustOrder matchEntrustOrder = new EntrustOrder();
        matchEntrustOrder.setId(req.getMatchId());
        matchEntrustOrder.setAmountComplete(req.getMatchAmountComplete());
        matchEntrustOrder.setAmount(req.getAmount());
        matchEntrustOrder.setTotalComplete(req.getTotal());
        matchEntrustOrder.setTotalFee(req.getMatchFee());
        if (req.getMatchStatus() != null && req.getMatchStatus() == 2) {
            matchEntrustOrder.setStatus(2);
        } else {
            matchEntrustOrder.setStatus(1);
        }
        boolean incr1 = entrustOrderDao.incr(matchEntrustOrder);
        if (!incr1) {
            return false;
        }

        // 增加交易明细
        EntrustOrderDetail entrustOrderDetail = new EntrustOrderDetail();
        entrustOrderDetail.setCoinId(req.getCoinId());
        entrustOrderDetail.setTradeCoinId(req.getTradeCoinId());
        entrustOrderDetail.setMemberId(req.getMemberId());
        entrustOrderDetail.setOrderId(req.getId());
        entrustOrderDetail.setPrice(req.getPrice());
        entrustOrderDetail.setAmount(req.getAmount());
        entrustOrderDetail.setFee(req.getFee());
        entrustOrderDetail.setCreateTime(new Date());
        entrustOrderDetail.setModifiedTime(new Date());

        EntrustOrderDetail entrustOrderDetail2 = new EntrustOrderDetail();
        entrustOrderDetail2.setCoinId(req.getCoinId());
        entrustOrderDetail2.setTradeCoinId(req.getTradeCoinId());
        entrustOrderDetail2.setMemberId(req.getMatchMemberId());
        entrustOrderDetail2.setOrderId(req.getMatchId());
        entrustOrderDetail2.setPrice(req.getPrice());
        entrustOrderDetail2.setAmount(req.getAmount());
        entrustOrderDetail2.setFee(req.getMatchFee());
        entrustOrderDetail2.setCreateTime(new Date());
        entrustOrderDetail2.setModifiedTime(new Date());

        List<EntrustOrderDetail> batch = new ArrayList<>();
        batch.add(entrustOrderDetail);
        batch.add(entrustOrderDetail2);

        return entrustOrderDetailDao.insertBatch(batch);
    }
}
