package com.lmxdawn.market.service.impl;

import com.lmxdawn.market.service.KLineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KLineServiceImplTest {

    @Autowired
    private KLineService kLineService;

    @Test
    void install() {


        Long tradeCoinId = 2L;
        Long coinId = 1L;
        BigDecimal price = BigDecimal.valueOf(1.5);
        BigDecimal amount = BigDecimal.valueOf(1);
        kLineService.install(tradeCoinId, coinId, price, amount);

    }
}