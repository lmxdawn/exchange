package com.lmxdawn.robot.vo;

import lombok.Data;

@Data
public class PairRobotVo {
    private Long tradeCoinId; // 交易币种ID
    private Long coinId; // 计价币种ID
    private Long pairId; // 交易对ID
    private String lowerCoinName = ""; // 小写交易对名称
    private String upperCoinName = ""; // 大写交易对名称
    private Integer status = 1; // 是否开始
    private Double minAmount = 0.001; // 最低交易量
    private Double randRange0 = 20.0; // 交易量随机数范围 1%概率
    private Double randRange1 = 4.0; // 交易量随机数范围 9%概率
    private Double randRange2 = 1.0; //  交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
    private Double randRange3 = 0.1; // 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
    private Double randRange4 = 0.01; // 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
    private Double randRange5 = 0.001; // 交易量随机数范围0.1(0.0001 ~ 0.09) 20%概率
    private Double randRange6 = 0.0001; // 交易量随机数范围0.1(0.0001 ~ 0.09) 10%概率
    private Integer pricePrecision = 4;//价格精度要求
    private Integer amountPrecision = 6; // 数量精度要求
    private Double maxSubPrice = 20.0; // 买盘最高价与卖盘最低价相差超过20美金
    private Integer initOrderCount = 30; // 初始订单数量（此数字必须大于24）
    private Double priceStepRate = 0.003; // 价格变化步长(0.01 = 1%)
    private Integer runTime = 1000; // 行情请求间隔时间（5000 = 5秒）
}
