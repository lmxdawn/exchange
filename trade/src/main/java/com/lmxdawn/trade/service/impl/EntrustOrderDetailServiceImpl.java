package com.lmxdawn.trade.service.impl;

import com.lmxdawn.trade.dao.EntrustOrderDetailDao;
import com.lmxdawn.trade.entity.EntrustOrderDetail;
import com.lmxdawn.trade.req.EntrustOrderDetailReq;
import com.lmxdawn.trade.res.EntrustOrderDetailRes;
import com.lmxdawn.trade.service.EntrustOrderDetailService;
import com.lmxdawn.trade.util.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrustOrderDetailServiceImpl implements EntrustOrderDetailService {

    @Autowired
    private EntrustOrderDetailDao entrustOrderDetailDao;

    @Override
    public List<EntrustOrderDetailRes> listPageByMemberIdAndOrderId(EntrustOrderDetailReq req) {
        Integer offset = PageUtils.createOffset(req.getPage(), req.getLimit());
        req.setOffset(offset);

        List<EntrustOrderDetail> entrustOrderDetails = entrustOrderDetailDao.listPageByMemberIdAndOrderId(req);

        return entrustOrderDetails.stream().map(v -> {
            EntrustOrderDetailRes res = new EntrustOrderDetailRes();
            BeanUtils.copyProperties(v, res);
            return res;
        }).collect(Collectors.toList());
    }
}
