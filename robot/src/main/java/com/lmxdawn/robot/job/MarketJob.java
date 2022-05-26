package com.lmxdawn.robot.job;

import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.robot.run.MarketRun;
import com.lmxdawn.robot.vo.PairVo;
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

        List<PairVo> list = new ArrayList<>();

        PairVo pairVo1 = new PairVo();
        pairVo1.setTradeCoinId(3L);
        pairVo1.setCoinId(1L);
        pairVo1.setCoinName("ethusdt");
        list.add(pairVo1);

        for (PairVo pairVo : list) {
            MarketRun marketRun = new MarketRun(pairVo, entrustOrderDubboService);
            new Thread(marketRun).start();
        }

    }


}
