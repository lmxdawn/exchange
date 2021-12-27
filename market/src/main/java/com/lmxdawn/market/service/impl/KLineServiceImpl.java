package com.lmxdawn.market.service.impl;

import com.lmxdawn.market.entity.KLine;
import com.lmxdawn.market.service.KLineService;
import com.lmxdawn.market.util.KLineUtil;
import com.lmxdawn.market.vo.KLineDateTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

@Service
public class KLineServiceImpl implements KLineService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static String collectionNamePrefix = "trade_kline_%s_%s";

    @Override
    public boolean install(Long tradeCoinId, Long coinId, BigDecimal price, BigDecimal amount) {

        long symbol = Long.parseLong(tradeCoinId.toString() + coinId.toString());

        KLineDateTimeVo kLineDateTimeVo = KLineUtil.createDateTime();

        // 成交额
        BigDecimal vol = amount.multiply(price);

        // 1分钟
        createKLine(kLineDateTimeVo, "1min", symbol, price, amount, vol);
        // 5分钟
        createKLine(kLineDateTimeVo, "5min", symbol, price, amount, vol);
        // 15分钟
        createKLine(kLineDateTimeVo, "15min", symbol, price, amount, vol);
        // 30分钟
        createKLine(kLineDateTimeVo, "30min", symbol, price, amount, vol);
        // 1小时
        createKLine(kLineDateTimeVo, "1hour", symbol, price, amount, vol);
        // 1小时
        createKLine(kLineDateTimeVo, "4hour", symbol, price, amount, vol);
        // 1天
        createKLine(kLineDateTimeVo, "1day", symbol, price, amount, vol);
        // 1周
        createKLine(kLineDateTimeVo, "1week", symbol, price, amount, vol);
        // 1月
        createKLine(kLineDateTimeVo, "1month", symbol, price, amount, vol);

        return false;
    }

    /**
     * 创建k线实例
     * @param kLineDateTimeVo 时间
     * @param timeStr 时间字符
     * @param symbol 交易对
     * @param price 价格
     * @param amount 数量
     * @param vol 数量
     * @return 是否创建成功
     */
    private boolean createKLine(KLineDateTimeVo kLineDateTimeVo, String timeStr, Long symbol, BigDecimal price, BigDecimal amount, BigDecimal vol) {
        long time;
        try {
            time = (long) kLineDateTimeVo.getClass().getMethod("getTime" + timeStr).invoke(kLineDateTimeVo);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }

        String collectionName = String.format(collectionNamePrefix, symbol, timeStr);
        Query timeQuery = new Query(Criteria.where("time").is(time));
        KLine timeKLine = mongoTemplate.findOne(timeQuery, KLine.class, collectionName);
        boolean isAdd = false;
        if (timeKLine == null) {
            isAdd = true;
            timeKLine = new KLine();
        }
        timeKLine.setTime(time);
        timeKLine.setPrice(price);
        timeKLine.setAmount(timeKLine.getAmount().add(amount));
        timeKLine.setCount(timeKLine.getCount().add(BigDecimal.valueOf(1)));
        timeKLine.setOpen(timeKLine.getOpen().compareTo(BigDecimal.ZERO) == 0 ? price : timeKLine.getOpen());
        timeKLine.setClose(price);
        timeKLine.setLow(timeKLine.getLow().compareTo(BigDecimal.ZERO) == 0 ? price : timeKLine.getClose().min(price));
        timeKLine.setHigh(timeKLine.getHigh().compareTo(BigDecimal.ZERO) == 0 ? price : timeKLine.getHigh().max(price));
        timeKLine.setVol(timeKLine.getVol().add(vol));

        if (isAdd) {
            mongoTemplate.insert(timeKLine, collectionName);
        } else {
            Update update = new Update();
            update.set("amount", timeKLine.getAmount());
            update.set("count", timeKLine.getCount());
            update.set("price", timeKLine.getPrice());
            update.set("open", timeKLine.getOpen());
            update.set("close", timeKLine.getClose());
            update.set("low", timeKLine.getLow());
            update.set("high", timeKLine.getHigh());
            update.set("vol", timeKLine.getVol());
            mongoTemplate.upsert(timeQuery, update, collectionName);
        }

        return true;
    }

}
