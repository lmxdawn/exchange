package com.lmxdawn.trade.dubbo.service;

import com.lmxdawn.dubboapi.req.trade.PairRobotSaveDubboReq;
import com.lmxdawn.dubboapi.res.trade.PairRobotDubboRes;
import com.lmxdawn.dubboapi.service.trade.PairRobotDubboService;
import com.lmxdawn.trade.dao.PairRobotDao;
import com.lmxdawn.trade.entity.PairRobot;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@DubboService
public class PairRobotDubboServiceImpl implements PairRobotDubboService {

    @Autowired
    private PairRobotDao pairRobotDao;

    @Override
    public List<PairRobotDubboRes> listAll() {

        List<PairRobot> listAll = pairRobotDao.listAll();

        return listAll.stream().map(v -> {
            PairRobotDubboRes res = new PairRobotDubboRes();
            BeanUtils.copyProperties(v, res);
            return res;
        }).collect(Collectors.toList());
    }

    @Override
    public Long insert(PairRobotSaveDubboReq req) {
        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();
        PairRobot byTidAndCid = pairRobotDao.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid != null) {
            return null;
        }

        PairRobot pairRobot = new PairRobot();
        BeanUtils.copyProperties(req, pairRobot);
        pairRobot.setCreateTime(new Date());
        pairRobot.setModifiedTime(new Date());
        pairRobotDao.insert(pairRobot);
        return pairRobot.getId();
    }

    @Override
    public boolean update(PairRobotSaveDubboReq req) {
        PairRobot pairRobot = new PairRobot();
        BeanUtils.copyProperties(req, pairRobot);
        pairRobot.setModifiedTime(new Date());

        return pairRobotDao.update(pairRobot);
    }

    @Override
    public PairRobotDubboRes findByTidAndCid(Long tradeCoinId, Long coinId) {
        PairRobot byTidAndCid = pairRobotDao.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid == null) {
            return null;
        }
        PairRobotDubboRes res = new PairRobotDubboRes();
        BeanUtils.copyProperties(byTidAndCid, res);
        return res;
    }
}
