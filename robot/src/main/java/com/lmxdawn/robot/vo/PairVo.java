package com.lmxdawn.robot.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PairVo {
    private Long tradeCoinId; // 交易币种ID
    private Long coinId; // 计价币种ID
    private String coinName = ""; // 如btcusdt
    private boolean running = true; // 是否开始
    private double minAmount = 0.001; // 最低交易量
    private double randRange0 = 20; // 交易量随机数范围 1%概率
    private double randRange1 = 4; // 交易量随机数范围 9%概率
    private double randRange2 = 1; //  交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
    private double randRange3 = 0.1; // 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
    private double randRange4 = 0.01; // 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
    private double randRange5 = 0.001; // 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
    private double randRange6 = 0.0001; // 交易量随机数范围0.1(0.0001 ~ 0.09) 10%概率
    private int pricePrecision = 4;//价格精度要求
    private int amountPrecision = 6; // 数量精度要求
    private BigDecimal maxSubPrice = BigDecimal.valueOf(20); // 买盘最高价与卖盘最低价相差超过20美金
    private int initOrderCount = 30; // 初始订单数量（此数字必须大于24）
    private BigDecimal priceStepRate = BigDecimal.valueOf(0.003); // 价格变化步长(0.01 = 1%)
    private int runTime = 1000; // 行情请求间隔时间（5000 = 5秒）
}
