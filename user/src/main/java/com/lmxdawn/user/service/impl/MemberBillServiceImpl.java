package com.lmxdawn.user.service.impl;

import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.user.dao.MemberBillDao;
import com.lmxdawn.user.entity.MemberBill;
import com.lmxdawn.user.req.MemberBillListPageReq;
import com.lmxdawn.user.res.MemberBillRes;
import com.lmxdawn.user.service.MemberBillCategoryService;
import com.lmxdawn.user.service.MemberBillService;
import com.lmxdawn.user.util.PageUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberBillServiceImpl implements MemberBillService {

    @Autowired
    private MemberBillDao memberBillDao;

    @Autowired
    private MemberBillCategoryService memberBillCategoryService;

    @DubboReference
    private CoinDubboService coinDubboService;

    @Override
    public List<MemberBillRes> listPage(MemberBillListPageReq req) {

        Integer offset = PageUtils.createOffset(req.getPage(), req.getLimit());
        req.setOffset(offset);

        List<MemberBill> list = memberBillDao.listPage(req);

        if (list.size() == 0) {
            return new ArrayList<>();
        }

        List<Long> coinIds = list.stream().map(MemberBill::getCoinId).distinct().collect(Collectors.toList());
        List<String> categoryNames = list.stream().map(MemberBill::getCategory).distinct().collect(Collectors.toList());

        Map<String, String> categoryNameMap = memberBillCategoryService.mapByNameIn(categoryNames);

        Map<Long, CoinSimpleDubboRes> coinMap = coinDubboService.mapByCoinIds(coinIds);

        List<MemberBillRes> collect = list.stream().map(v -> {
            MemberBillRes memberBillRes = new MemberBillRes();
            BeanUtils.copyProperties(v, memberBillRes);
            memberBillRes.setCoin(coinMap.get(v.getCoinId()));
            memberBillRes.setCategory(categoryNameMap.get(v.getCategory()));
            return memberBillRes;
        }).collect(Collectors.toList());

        return collect;
    }
}
