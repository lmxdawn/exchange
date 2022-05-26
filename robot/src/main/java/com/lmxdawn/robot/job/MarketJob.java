package com.lmxdawn.robot.job;

import com.lmxdawn.dubboapi.res.trade.PairRobotDubboRes;
import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.dubboapi.service.trade.PairRobotDubboService;
import com.lmxdawn.robot.run.MarketRun;
import com.lmxdawn.robot.vo.PairRobotVo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 市场行情 Job
 */
@Component
public class MarketJob implements Runnable{

    @DubboReference
    private EntrustOrderDubboService entrustOrderDubboService;

    @DubboReference
    private PairRobotDubboService pairRobotDubboService;

    private List<PairRobotVo> pairRobotVoList;

    private boolean isStart;

    @PostConstruct
    public void start() {

        if (isStart) {
            return;
        }

        isStart = true;

        // 获取所有的交易对
        List<PairRobotDubboRes> pairRobotDubboRes = pairRobotDubboService.listAll();
        pairRobotVoList = pairRobotDubboRes.stream().map(v -> {
            PairRobotVo pairRobotVo = new PairRobotVo();
            BeanUtils.copyProperties(v, pairRobotVo);
            return pairRobotVo;
        }).collect(Collectors.toList());

        for (PairRobotVo pairRobotVo : pairRobotVoList) {
            MarketRun marketRun = new MarketRun(pairRobotVo, entrustOrderDubboService);
            new Thread(marketRun).start();
        }

        // 启动定时获取机器人配置
        new Thread(new MarketJob()).start();

    }

    /**
     * 设置机器人
     */
    public void setPairRobot() {

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
                BeanUtils.copyProperties(vo, pairRobotVo);
            }
        }

        for (PairRobotVo isAddPairRobot : isAddPairRobots) {
            MarketRun marketRun = new MarketRun(isAddPairRobot, entrustOrderDubboService);
            new Thread(marketRun).start();
        }

    }


    @Override
    public void run() {
        while (true) {
            try {
                this.setPairRobot();
                // 30秒执行一次
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
