package com.lmxdawn.robot.run;

import com.lmxdawn.dubboapi.res.trade.PairRobotDubboRes;
import com.lmxdawn.dubboapi.service.market.DepthDubboService;
import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.dubboapi.service.trade.PairRobotDubboService;
import com.lmxdawn.robot.vo.PairRobotVo;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MarketStartRun implements Runnable {

    private final EntrustOrderDubboService entrustOrderDubboService;

    private final PairRobotDubboService pairRobotDubboService;

    private final DepthDubboService depthDubboService;

    private List<PairRobotVo> pairRobotVoList;

    private boolean isStart;

    public MarketStartRun(EntrustOrderDubboService entrustOrderDubboService, PairRobotDubboService pairRobotDubboService, DepthDubboService depthDubboService) {
        this.entrustOrderDubboService = entrustOrderDubboService;
        this.pairRobotDubboService = pairRobotDubboService;
        this.depthDubboService = depthDubboService;
    }


    @Override
    public void run() {

        if (isStart) {
            return;
        }

        isStart = true;

        // 获取所有的交易对
        List<PairRobotDubboRes> pairRobotDubboRes = pairRobotDubboService.listAll();
        System.out.println(pairRobotDubboRes);
        pairRobotVoList = pairRobotDubboRes.stream().map(v -> {
            PairRobotVo pairRobotVo = new PairRobotVo();
            BeanUtils.copyProperties(v, pairRobotVo);
            return pairRobotVo;
        }).collect(Collectors.toList());

        for (PairRobotVo pairRobotVo : pairRobotVoList) {
            MarketRun marketRun = new MarketRun(pairRobotVo, entrustOrderDubboService, depthDubboService);
            new Thread(marketRun).start();
        }

        // 启动定时获取机器人配置
        while (true) {
            try {
                this.setPairRobot();
                // 30秒执行一次
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 设置机器人
     */
    public void setPairRobot() {

        System.out.println("重新获取机器人");

        List<PairRobotDubboRes> pairRobotDubboResList = pairRobotDubboService.listAll();

        List<PairRobotVo> isAddPairRobots = new ArrayList<>();
        Map<Long, PairRobotDubboRes> map = new HashMap<>();
        Map<Long, PairRobotVo> map2 = new HashMap<>();
        for (PairRobotVo pairRobotVo : pairRobotVoList) {
            map2.put(pairRobotVo.getPairId(), pairRobotVo);
        }
        for (PairRobotDubboRes pairRobotDubboRes : pairRobotDubboResList) {
            map.put(pairRobotDubboRes.getPairId(), pairRobotDubboRes);
            if (!map2.containsKey(pairRobotDubboRes.getPairId())) {
                PairRobotVo pairRobotVo = new PairRobotVo();
                BeanUtils.copyProperties(pairRobotDubboRes, pairRobotVo);
                isAddPairRobots.add(pairRobotVo);
            }
        }

        for (PairRobotVo pairRobotVo : pairRobotVoList) {
            if (map.containsKey(pairRobotVo.getPairId())) {
                PairRobotDubboRes vo = map.get(pairRobotVo.getPairId());
                boolean isReset = pairRobotVo.getStatus() == 0 && vo.getStatus() == 1;
                BeanUtils.copyProperties(vo, pairRobotVo);
                if (isReset) {
                    MarketRun marketRun = new MarketRun(pairRobotVo, entrustOrderDubboService, depthDubboService);
                    new Thread(marketRun).start();
                }
            }
        }

        for (PairRobotVo isAddPairRobot : isAddPairRobots) {
            pairRobotVoList.add(isAddPairRobot);
            MarketRun marketRun = new MarketRun(isAddPairRobot, entrustOrderDubboService, depthDubboService);
            new Thread(marketRun).start();
        }

    }

}
