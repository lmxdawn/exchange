package com.lmxdawn.robot.run;

import com.lmxdawn.robot.vo.PairVo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketRunTest {

    @Test
    void run() {

        PairVo pairVo = new PairVo();
        pairVo.setCoinId(1L);
        pairVo.setTradeCoinId(3L);
        pairVo.setCoinName("ethusdt");
        MarketRun marketRun = new MarketRun(pairVo, null);
        new Thread(marketRun).start();

    }
}