package com.lmxdawn.robot.run;

import com.lmxdawn.dubboapi.req.trade.EntrustOrderRobotCreateDubboReq;
import com.lmxdawn.dubboapi.res.market.DepthSizeDubboRes;
import com.lmxdawn.dubboapi.service.market.DepthDubboService;
import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.robot.vo.PairRobotVo;

import java.math.BigDecimal;
import java.util.Random;

public class MarketRun implements Runnable {

    private PairRobotVo pairRobotVo;

    private final EntrustOrderDubboService entrustOrderDubboService;

    private final DepthDubboService depthDubboService;

    public MarketRun(PairRobotVo pairRobotVo, EntrustOrderDubboService entrustOrderDubboService, DepthDubboService depthDubboService) {
        this.pairRobotVo = pairRobotVo;
        this.entrustOrderDubboService = entrustOrderDubboService;
        this.depthDubboService = depthDubboService;
    }

    protected Random rand = new Random();

    @Override
    public void run() {

        System.out.println("机器人开始");

        while (pairRobotVo.getStatus() == 1) {

            try {
                // 获取外部行情价格
                BigDecimal price = BigDecimal.valueOf(3623);

                // 获取初始化数量
                int initOrderCount = pairRobotVo.getInitOrderCount();

                Long tradeCoinId = pairRobotVo.getTradeCoinId();
                Long coinId = pairRobotVo.getCoinId();

                Integer amountPrecision = pairRobotVo.getAmountPrecision();

                DepthSizeDubboRes size = depthDubboService.size(tradeCoinId, coinId, price, initOrderCount, amountPrecision);

                System.out.println(size);
                System.out.println("********************************");
                System.out.println("价格：" + price);

                boolean isOrder = false;

                // 获取平台的买盘行情深度
                int buyOrderCount = size.getBuySize();
                int buyCreateCount = initOrderCount - buyOrderCount;
                if (buyCreateCount > 0) {
                    // 买盘不足，构建随机订单
                    System.out.println("买盘不足，构建随机订单" + buyCreateCount);
                    isOrder = true;
                    this.buildRandOrder(1, buyCreateCount, size.getBuyLastPrice());
                } else if (price.compareTo(size.getSellFirstPrice()) > 0) {
                    // 行情价格 > 卖盘最低价，需要构建买单
                    System.out.println("买行情价格 > 卖盘最低价，需要构建买单");
                    isOrder = true;
                    this.createOrder(1, size.getSellGreaterPriceAmount(), price);
                }

                // 获取平台的卖盘行情深度
                int sellOrderCount = size.getSellSize();
                int sellCreateCount = initOrderCount - sellOrderCount;
                if (sellCreateCount > 0) {
                    // 卖盘不足，构建随机订单
                    System.out.println("卖盘不足，构建随机订单" + sellCreateCount);
                    isOrder = true;
                    this.buildRandOrder(2, sellCreateCount, size.getSellLastPrice());
                } else if (price.compareTo(size.getBuyFirstPrice()) < 0) {
                    // 行情价格 < 买盘最高价，需要构建卖单
                    System.out.println("行情价格 < 买盘最高价，需要构建卖单");
                    isOrder = true;
                    this.createOrder(2, size.getBuyGreaterPriceAmount(), price);
                }

                if (!isOrder) {
                    // 没有订单随机创建一个买盘或卖盘的订单，促成盘面流动
                    int temR = this.rand.nextInt(10);
                    int direction = temR > 5 ? 1 : 2;
                    BigDecimal amount = direction == 1 ? size.getSellFirstAmount() : size.getBuyFirstAmount();
                    BigDecimal bigPrice = direction == 1 ? size.getSellFirstPrice() : size.getBuyFirstPrice();
                    System.out.println("没有订单随机创建一个买盘或卖盘的订单，" + (direction == 1 ? "买单" : "卖单"));
                    this.createOrder(direction, amount, bigPrice);

                    int createDirection = direction == 1 ? 2 : 1;
                    // 撮合了订单，则添加一个
                    this.buildRandOrder(createDirection, 1, price);
                }

                Thread.sleep(pairRobotVo.getRunTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("机器人停止");

    }

    /**
     * 构建随机买卖单
     *
     * @param direction 方向（1：买入，2：卖出）
     * @param count     订单数量
     * @param price     外部价格
     */
    private void buildRandOrder(Integer direction, int count, BigDecimal price) {
        BigDecimal bigMaxSubPrice = BigDecimal.valueOf(this.pairRobotVo.getMaxSubPrice());

        BigDecimal diffPrice = bigMaxSubPrice.divide(BigDecimal.valueOf(3), this.pairRobotVo.getPricePrecision(), BigDecimal.ROUND_HALF_DOWN);

        // 价格加上价差
        if (direction == 1) {
            price = price.subtract(diffPrice);
        } else {
            price = price.add(bigMaxSubPrice.subtract(diffPrice));
        }

        for (int i = 0; i < count; i++) {

            BigDecimal bigPriceStepRate = BigDecimal.valueOf(this.pairRobotVo.getPriceStepRate());
            // 价格变化
            if (direction == 1) {
                // 买盘累减
                price = price.subtract(price.multiply(bigPriceStepRate)).setScale(this.pairRobotVo.getPricePrecision(), BigDecimal.ROUND_HALF_DOWN);
            } else {
                // 卖盘累加
                price = price.add(price.multiply(bigPriceStepRate)).setScale(this.pairRobotVo.getPricePrecision(), BigDecimal.ROUND_HALF_DOWN);
            }

            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal amount = this.randAmount();

            this.createOrder(direction, amount, price);

        }
    }

    /**
     * 随机数量
     *
     * @return
     */
    private BigDecimal randAmount() {

        double temRand;
        int intRand = this.rand.nextInt(100);
        if (intRand == 0) {
            temRand = this.pairRobotVo.getRandRange0();
        } else if (intRand < 10) {
            temRand = this.pairRobotVo.getRandRange1();
        } else if (intRand < 30) {
            temRand = this.pairRobotVo.getRandRange2();
        } else if (intRand < 50) {
            temRand = this.pairRobotVo.getRandRange3();
        } else if (intRand < 70) {
            temRand = this.pairRobotVo.getRandRange4();
        } else if (intRand < 90) {
            temRand = this.pairRobotVo.getRandRange5();
        } else {
            temRand = this.pairRobotVo.getRandRange6();
        }

        return BigDecimal.valueOf(this.pairRobotVo.getMinAmount() + temRand * this.rand.nextDouble()).setScale(this.pairRobotVo.getAmountPrecision(), BigDecimal.ROUND_HALF_DOWN);

    }

    /**
     * 创建订单
     *
     * @param direction 方向（1：买入，2：卖出）
     * @param amount    数量
     * @param price     外部价格
     */
    private void createOrder(Integer direction, BigDecimal amount, BigDecimal price) {

        EntrustOrderRobotCreateDubboReq req = new EntrustOrderRobotCreateDubboReq();
        req.setTradeCoinId(this.pairRobotVo.getTradeCoinId());
        req.setCoinId(this.pairRobotVo.getCoinId());
        req.setDirection(direction);
        req.setType(1);
        req.setAmount(amount.doubleValue());
        req.setPrice(price.doubleValue());

        entrustOrderDubboService.robotCreate(req);

    }
}
