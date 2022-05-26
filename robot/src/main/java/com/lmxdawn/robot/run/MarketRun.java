package com.lmxdawn.robot.run;

import com.lmxdawn.dubboapi.req.trade.EntrustOrderRobotCreateDubboReq;
import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.robot.vo.PairRobotVo;

import java.math.BigDecimal;
import java.util.Random;

public class MarketRun implements Runnable {

    private PairRobotVo pairRobotVo;

    private EntrustOrderDubboService entrustOrderDubboService;

    public MarketRun(PairRobotVo pairRobotVo, EntrustOrderDubboService entrustOrderDubboService) {
        this.pairRobotVo = pairRobotVo;
        this.entrustOrderDubboService = entrustOrderDubboService;
    }

    protected Random rand = new Random();

    @Override
    public void run() {

        while (pairRobotVo.getRunning() == 1) {

            try {
                // 获取外部行情价格
                BigDecimal price = BigDecimal.valueOf(3623);

                // 获取初始化数量
                int initOrderCount = pairRobotVo.getInitOrderCount();

                // 获取平台的买盘行情深度
                int buyOrderCount = 0;
                int buyCreateCount = initOrderCount - buyOrderCount;
                if (buyCreateCount > 0) {
                    this.createOrder(1, buyCreateCount, price);
                }

                // 获取平台的卖盘行情深度
                int sellOrderCount = 0;
                int sellCreateCount = initOrderCount - sellOrderCount;
                if (sellCreateCount > 0) {
                    this.createOrder(2, sellCreateCount, price);
                }

                Thread.sleep(pairRobotVo.getRunTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 创建订单
     * @param direction 方向（1：买入，2：卖出）
     * @param count 订单数量
     * @param price 外部价格
     */
    private void createOrder(Integer direction, Integer count, BigDecimal price) {

        BigDecimal diffPrice = this.pairRobotVo.getMaxSubPrice().divide(BigDecimal.valueOf(2), this.pairRobotVo.getPricePrecision(), BigDecimal.ROUND_HALF_DOWN);

        // 价格加上价差
        if (direction == 1) {
            price = price.subtract(diffPrice);
        } else {
            price = price.add(diffPrice);
        }

        for(int i = 0; i < count; i++) {

            // 价格变化
            if (direction == 1) {
                // 买盘累减
                price = price.subtract(price.multiply(this.pairRobotVo.getPriceStepRate())).setScale(this.pairRobotVo.getPricePrecision(), BigDecimal.ROUND_HALF_DOWN);
            } else {
                // 卖盘累加
                price = price.add(price.multiply(this.pairRobotVo.getPriceStepRate())).setScale(this.pairRobotVo.getPricePrecision(), BigDecimal.ROUND_HALF_DOWN);
            }

            double temRand;
            int intRand = this.rand.nextInt(100);
            if(intRand == 0) {
                temRand = this.pairRobotVo.getRandRange0();
            }else if(intRand < 10) {
                temRand = this.pairRobotVo.getRandRange1();
            }else if(intRand < 30) {
                temRand = this.pairRobotVo.getRandRange2();
            }else if(intRand < 50) {
                temRand = this.pairRobotVo.getRandRange3();
            }else if(intRand < 70) {
                temRand = this.pairRobotVo.getRandRange4();
            }else if(intRand < 90) {
                temRand = this.pairRobotVo.getRandRange5();
            }else{
                temRand = this.pairRobotVo.getRandRange6();
            }

            double amount = BigDecimal.valueOf(this.pairRobotVo.getMinAmount() + temRand * this.rand.nextDouble()).setScale(this.pairRobotVo.getAmountPrecision(), BigDecimal.ROUND_HALF_DOWN).doubleValue();

            EntrustOrderRobotCreateDubboReq req = new EntrustOrderRobotCreateDubboReq();
            req.setTradeCoinId(this.pairRobotVo.getTradeCoinId());
            req.setCoinId(this.pairRobotVo.getCoinId());
            req.setDirection(direction);
            req.setType(1);
            req.setAmount(amount);
            req.setPrice(price.doubleValue());

            System.out.println(req);
            // entrustOrderDubboService.robotCreate(req);

        }


    }
}
