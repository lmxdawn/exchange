package com.lmxdawn.robot.job;

import com.lmxdawn.dubboapi.service.market.DepthDubboService;
import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.dubboapi.service.trade.PairRobotDubboService;
import com.lmxdawn.robot.run.MarketStartRun;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 市场行情 Job
 */
@Component
public class MarketJob {

    @DubboReference
    private EntrustOrderDubboService entrustOrderDubboService;

    @DubboReference
    private PairRobotDubboService pairRobotDubboService;

    @DubboReference
    private DepthDubboService depthDubboService;

    @PostConstruct
    public void start() {

        // 开线程启动
        new Thread(new MarketStartRun(entrustOrderDubboService, pairRobotDubboService, depthDubboService)).start();

    }

}
