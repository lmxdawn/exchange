package com.lmxdawn.market.dubbo.service;

import com.lmxdawn.dubboapi.res.market.DepthSizeDubboRes;
import com.lmxdawn.dubboapi.service.market.DepthDubboService;
import com.lmxdawn.market.constant.CacheConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

@DubboService
public class DepthDubboServiceImpl implements DepthDubboService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    protected Random rand = new Random();

    @Override
    public DepthSizeDubboRes size(Long tradeCoinId, Long coinId, BigDecimal price, Integer initCount, Integer amountPrecision) {
        Long pair = Long.valueOf(tradeCoinId.toString() + coinId.toString());

        // 放大倍数，不然有精度问题
        BigDecimal bigPow = BigDecimal.valueOf(Math.pow(10.0, amountPrecision));

        String buyKey = String.format(CacheConstant.BUY_DEPTH, pair);
        String buyInfoKey =  CacheConstant.BUY_DEPTH_INFO;
        BigDecimal buyRandPrice = BigDecimal.valueOf(0);
        BigDecimal buyFirstPrice = BigDecimal.valueOf(0);
        BigDecimal buyFirstAmount = BigDecimal.valueOf(0);
        BigDecimal buyGreaterPriceAmount = BigDecimal.valueOf(0);
        BigDecimal buyLastPrice = price;
        int buySize = 0;
        // 买盘，从大到小获取
        Set<String> depthBuyList = redisTemplate.opsForZSet().reverseRange(buyKey, 0, initCount);
        if (depthBuyList != null && depthBuyList.size() > 0) {
            int r = rand.nextInt(depthBuyList.size());
            int i = 0;
            for (String depthPriceStr : depthBuyList) {
                BigDecimal depthPrice = new BigDecimal(depthPriceStr);
                if (i == r) {
                    buyRandPrice = depthPrice;
                }
                i++;
                String depthAmountKey = String.format(buyInfoKey, pair, depthPriceStr);
                String depthAmountStr = redisTemplate.opsForValue().get(depthAmountKey);
                BigDecimal depthAmount = !StringUtils.isBlank(depthAmountStr) ? new BigDecimal(depthAmountStr) : BigDecimal.valueOf(0);
                depthAmount = depthAmount.divide(bigPow, amountPrecision,BigDecimal.ROUND_DOWN);
                if (buyFirstPrice.compareTo(BigDecimal.valueOf(0)) == 0) {
                    buyFirstPrice = depthPrice;
                    buyFirstAmount = depthAmount;
                }
                buyLastPrice = depthPrice;
                // 买盘的价格大于最新价格的数量
                if (depthPrice.compareTo(price) >= 0) {
                    buyGreaterPriceAmount = buyGreaterPriceAmount.add(depthAmount);
                }
            }
            buySize = depthBuyList.size();
        }

        String sellKey = String.format(CacheConstant.SELL_DEPTH, pair);
        String sellInfoKey =  CacheConstant.SELL_DEPTH_INFO;
        BigDecimal sellRandPrice = BigDecimal.valueOf(0);
        BigDecimal sellFirstPrice = BigDecimal.valueOf(0);
        BigDecimal sellFirstAmount = BigDecimal.valueOf(0);
        BigDecimal sellGreaterPriceAmount = BigDecimal.valueOf(0);
        BigDecimal sellLastPrice = price;
        int sellSize = 0;
        // 卖盘，从小到大获取
        Set<String> depthSellList = redisTemplate.opsForZSet().range(sellKey, 0, initCount);
        if (depthSellList != null && depthSellList.size() > 0) {
            int r = rand.nextInt(depthSellList.size());
            int i = 0;
            for (String depthPriceStr : depthSellList) {
                BigDecimal depthPrice = new BigDecimal(depthPriceStr);
                if (i == r) {
                    sellRandPrice = depthPrice;
                }
                i++;
                String depthAmountKey = String.format(sellInfoKey, pair, depthPriceStr);
                String depthAmountStr = redisTemplate.opsForValue().get(depthAmountKey);
                BigDecimal depthAmount = !StringUtils.isBlank(depthAmountStr) ? new BigDecimal(depthAmountStr) : BigDecimal.valueOf(0);
                depthAmount = depthAmount.divide(bigPow, amountPrecision,BigDecimal.ROUND_DOWN);
                if (sellFirstPrice.compareTo(BigDecimal.valueOf(0)) == 0) {
                    sellFirstPrice = depthPrice;
                    sellFirstAmount = depthAmount;
                }
                sellLastPrice = depthPrice;
                // 卖盘的价格小于最新价格的数量
                if (depthPrice.compareTo(price) <= 0) {
                    sellGreaterPriceAmount = sellGreaterPriceAmount.add(depthAmount);
                }
            }
            sellSize = depthSellList.size();
        }

        DepthSizeDubboRes res = new DepthSizeDubboRes();
        res.setBuySize(buySize);
        res.setBuyRandPrice(buyRandPrice);
        res.setBuyFirstPrice(buyFirstPrice);
        res.setBuyFirstAmount(buyFirstAmount);
        res.setBuyGreaterPriceAmount(buyGreaterPriceAmount);
        res.setBuyLastPrice(buyLastPrice);
        res.setSellSize(sellSize);
        res.setSellRandPrice(sellRandPrice);
        res.setSellFirstPrice(sellFirstPrice);
        res.setSellFirstAmount(sellFirstAmount);
        res.setSellGreaterPriceAmount(sellGreaterPriceAmount);
        res.setSellLastPrice(sellLastPrice);

        return res;
    }


}
