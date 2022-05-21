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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        entrustOrderDetail.setPrice(req.getPrice());
        entrustOrderDetail.setAmount(req.getAmount());
        entrustOrderDetail.setCreateTime(new Date());
        entrustOrderDetail.setModifiedTime(new Date());

        Integer direction = req.getDirection();

        Long buyMemberId = direction == 1 ? req.getMemberId() : req.getMatchMemberId();
        Long buyOrderId = direction == 1 ? req.getId() : req.getMatchId();
        Double buyFee = direction == 1 ? req.getFee() : req.getMatchFee();
        Long sellMemberId = direction == 2 ? req.getMemberId() : req.getMatchMemberId();
        Long sellOrderId = direction == 2 ? req.getId() : req.getMatchId();
        Double sellFee = direction == 2 ? req.getFee() : req.getMatchFee();
        entrustOrderDetail.setBuyMemberId(buyMemberId);
        entrustOrderDetail.setBuyOrderId(buyOrderId);
        entrustOrderDetail.setBuyFee(buyFee);
        entrustOrderDetail.setSellMemberId(sellMemberId);
        entrustOrderDetail.setSellOrderId(sellOrderId);
        entrustOrderDetail.setSellFee(sellFee);

        return entrustOrderDetailDao.insert(entrustOrderDetail);
    }
}
