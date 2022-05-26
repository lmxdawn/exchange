package com.lmxdawn.robot.job;

import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.robot.run.MarketRun;
import com.lmxdawn.robot.vo.PairRobotVo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 市场行情 Job
 */
@Component
public class MarketJob {

    @DubboReference
    private EntrustOrderDubboService entrustOrderDubboService;

    @PostConstruct
    public void start() {

        List<PairRobotVo> list = new ArrayList<>();

        PairRobotVo pairRobotVo1 = new PairRobotVo();
        pairRobotVo1.setTradeCoinId(3L);
        pairRobotVo1.setCoinId(1L);
        pairRobotVo1.setCoinName("ethusdt");
        list.add(pairRobotVo1);

        for (PairRobotVo pairRobotVo : list) {
            MarketRun marketRun = new MarketRun(pairRobotVo, entrustOrderDubboService);
            new Thread(marketRun).start();
        }

    }


}
