package com.lmxdawn.market.service.impl;

import com.lmxdawn.market.entity.KLine;
import com.lmxdawn.market.req.KLineListReq;
import com.lmxdawn.market.res.KLineListRes;
import com.lmxdawn.market.service.KLineService;
import com.lmxdawn.market.util.KLineUtil;
import com.lmxdawn.market.vo.KLineDateTimeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KLineServiceImpl implements KLineService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static String collectionNamePrefix = "trade_kline_%s_%s";

    @Override
    public boolean install(Long tradeCoinId, Long coinId, BigDecimal price, BigDecimal amount) {

        long pair = Long.parseLong(tradeCoinId.toString() + coinId.toString());

        // 成交额
        BigDecimal vol = amount.multiply(price);

        KLineUtil.timeMap.keySet().forEach(timeStr -> createKLine(timeStr, pair, price, amount, vol));

        return true;
    }

    @Override
    public List<KLineListRes> list(KLineListReq req) {

        String timeStr = req.getTimeStr();
        Long time = req.getTime();

        KLineDateTimeVo timeVo = KLineUtil.createDateTime(timeStr, time, 50);

        time = timeVo.getTime();
        Long prevTime = timeVo.getPrevTime();

        Criteria criteria = Criteria.where("time").gte(prevTime).andOperator(Criteria.where("time").lte(time));
        Sort sort = Sort.by(Sort.Direction.ASC, "time");
        Query query = new Query(criteria).with(sort);

        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();

        long pair = Long.parseLong(tradeCoinId.toString() + coinId.toString());
        String collectionName = String.format(collectionNamePrefix, pair, timeStr);

        List<KLine> kLines = mongoTemplate.find(query, KLine.class, collectionName);

        if (kLines.size() == 0) {
            return new ArrayList<>();
        }

        return kLines.stream().map(v -> {

            KLineListRes res = new KLineListRes();

            BeanUtils.copyProperties(v, res);
            return res;
        }).collect(Collectors.toList());
    }

    /**
     * 创建k线实例
     *
     * @param timeStr         时间字符
     * @param pair            交易对
     * @param price           价格
     * @param amount          数量
     * @param vol             数量
     * @return 是否创建成功
     */
    private boolean createKLine(String timeStr, Long pair, BigDecimal price, BigDecimal amount, BigDecimal vol) {
        Long time = 0L;

        KLineDateTimeVo timeVo = KLineUtil.createDateTime(timeStr, time, 1);

        time = timeVo.getTime();

        String collectionName = String.format(collectionNamePrefix, pair, timeStr);
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
