package com.lmxdawn.market.service.impl;

import com.lmxdawn.market.entity.KLine;
import com.lmxdawn.market.service.KLineService;
import com.lmxdawn.market.util.KLineUtil;
import com.lmxdawn.market.vo.KLineDateTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class KLineServiceImpl implements KLineService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean install(Long tradeCoinId, Long coinId, BigDecimal price, BigDecimal amount, BigDecimal vol) {

        long symbol = Long.parseLong(tradeCoinId.toString() + coinId.toString());

        String collectionNamePrefix = "trade_kline_" + symbol + "_";
        String collectionName1min = collectionNamePrefix + "1min";
        String collectionName5min = collectionNamePrefix + "5min";
        String collectionName15min = collectionNamePrefix + "15min";
        String collectionName30min = collectionNamePrefix + "30min";
        String collectionName1hour = collectionNamePrefix + "1hour";
        String collectionName4hour = collectionNamePrefix + "4hour";
        String collectionName1day = collectionNamePrefix + "1day";
        String collectionName1week = collectionNamePrefix + "1week";
        String collectionName1month = collectionNamePrefix + "1month";

        KLineDateTimeVo kLineDateTimeVo = KLineUtil.createDateTime();

        Long time1min = kLineDateTimeVo.getTime1min();
        Query time1minQuery = new Query(Criteria.where("time").is(time1min));
        KLine time1minKLine = mongoTemplate.findOne(time1minQuery, KLine.class, collectionName1min);
        if (time1minKLine == null) {
            time1minKLine = new KLine();
        }
        time1minKLine.setTime(time1min);
        time1minKLine.setAmount(time1minKLine.getAmount().add(amount));
        time1minKLine.setCount(time1minKLine.getCount().add(BigDecimal.valueOf(1)));
        time1minKLine.setOpen(time1minKLine.getOpen().compareTo(BigDecimal.ZERO) == 0 ? price : time1minKLine.getOpen());
        time1minKLine.setClose(time1minKLine.getClose().compareTo(BigDecimal.ZERO) == 0 ? price : time1minKLine.getClose());
        time1minKLine.setLow(time1minKLine.getLow().compareTo(BigDecimal.ZERO) == 0 ? price : time1minKLine.getClose().min(price));
        time1minKLine.setHigh(time1minKLine.getHigh().compareTo(BigDecimal.ZERO) == 0 ? price : time1minKLine.getHigh().max(price));
        time1minKLine.setVol(time1minKLine.getVol().add(vol));


        return false;
    }
}
