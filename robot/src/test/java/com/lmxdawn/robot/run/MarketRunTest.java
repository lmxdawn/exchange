package com.lmxdawn.robot.run;

import com.lmxdawn.robot.vo.PairRobotVo;
import org.junit.jupiter.api.Test;

class MarketRunTest {

    @Test
    void run() {

        PairRobotVo pairRobotVo = new PairRobotVo();
        pairRobotVo.setCoinId(1L);
        pairRobotVo.setTradeCoinId(3L);
        pairRobotVo.setCoinName("ethusdt");
        MarketRun marketRun = new MarketRun(pairRobotVo, null);
        new Thread(marketRun).start();

    }
}