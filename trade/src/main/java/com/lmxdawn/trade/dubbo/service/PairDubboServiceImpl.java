package com.lmxdawn.trade.dubbo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmxdawn.dubboapi.req.trade.PairQueryDubboReq;
import com.lmxdawn.dubboapi.req.trade.PairSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.trade.PairDubboRes;
import com.lmxdawn.dubboapi.res.trade.PairRobotDubboRes;
import com.lmxdawn.dubboapi.res.trade.PairSimpleDubboRes;
import com.lmxdawn.dubboapi.service.trade.PairDubboService;
import com.lmxdawn.trade.dao.PairDao;
import com.lmxdawn.trade.dao.PairRobotDao;
import com.lmxdawn.trade.entity.Pair;
import com.lmxdawn.trade.entity.PairRobot;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@DubboService
public class PairDubboServiceImpl implements PairDubboService {

    @Autowired
    private PairDao pairDao;

    @Autowired
    private PairRobotDao pairRobotDao;

    @Override
    public PageSimpleDubboRes<PairDubboRes> list(PairQueryDubboReq req) {

        int offset = (req.getPage() - 1) * req.getLimit();
        PageHelper.offsetPage(offset, req.getLimit());
        List<PairDubboRes> infoDubboResList = pairDao.listPageDubbo(req);

        Set<Long> pairIds = new HashSet<>();
        for (PairDubboRes res : infoDubboResList) {
            pairIds.add(res.getId());
        }

        Map<Long, PairRobot> pairRobotMap = new HashMap<>();
        if (pairIds.size() > 0) {
            List<PairRobot> pairRobots = pairRobotDao.listByPairIdIn(new ArrayList<>(pairIds));
            for (PairRobot pairRobot : pairRobots) {
                pairRobotMap.put(pairRobot.getPairId(), pairRobot);
            }
        }

        PageSimpleDubboRes<PairDubboRes> pageSimpleDubboRes = new PageSimpleDubboRes<>();

        if (infoDubboResList.size() == 0) {
            return pageSimpleDubboRes;
        }

        PageInfo<PairDubboRes> infoDubboResPageInfo = new PageInfo<>(infoDubboResList);

        List<PairDubboRes> collect = infoDubboResList.stream().peek(v -> {
            PairRobotDubboRes res = new PairRobotDubboRes();
            if (pairRobotMap.containsKey(v.getId())) {
                BeanUtils.copyProperties(pairRobotMap.get(v.getId()), res);
            }
            v.setRobotDubboRes(res);
        }).collect(Collectors.toList());

        pageSimpleDubboRes.setTotal(infoDubboResPageInfo.getTotal());
        pageSimpleDubboRes.setList(collect);
        return pageSimpleDubboRes;
    }

    @Override
    public Long insert(PairSaveDubboReq req) {

        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();
        Pair byTidAndCid = pairDao.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid != null) {
            return null;
        }

        Pair coin = new Pair();
        BeanUtils.copyProperties(req, coin);
        coin.setCreateTime(new Date());
        coin.setModifiedTime(new Date());
        pairDao.insert(coin);
        return coin.getId();
    }

    @Override
    public boolean update(PairSaveDubboReq req) {

        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();
        Pair byTidAndCid = pairDao.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid != null && !byTidAndCid.getId().equals(req.getId())) {
            return false;
        }
        Pair coin = new Pair();
        BeanUtils.copyProperties(req, coin);
        coin.setModifiedTime(new Date());

        return pairDao.update(coin);
    }

    @Override
    public PairSimpleDubboRes findByTidAndCid(Long tradeCoinId, Long coinId) {
        Pair byTidAndCid = pairDao.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid == null) {
            return null;
        }
        PairSimpleDubboRes pairSimpleDubboRes = new PairSimpleDubboRes();
        BeanUtils.copyProperties(byTidAndCid, pairSimpleDubboRes);
        return pairSimpleDubboRes;
    }
}
