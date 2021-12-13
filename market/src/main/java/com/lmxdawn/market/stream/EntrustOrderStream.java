package com.lmxdawn.market.stream;

import com.lmxdawn.dubboapi.req.match.MatchEventDubboReq;
import com.lmxdawn.dubboapi.service.match.MatchDubboService;
import com.lmxdawn.market.constant.CacheConstant;
import com.lmxdawn.market.mq.EntrustOrderMq;
import com.lmxdawn.market.ws.DepthVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

@Service
public class EntrustOrderStream {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @DubboReference
    private MatchDubboService matchDubboService;

    @Bean
    public Consumer<EntrustOrderMq> entrustOrder() {

        return entrustOrderMq -> {

            // 提交撮合引擎
            MatchEventDubboReq matchEventDubboReq = new MatchEventDubboReq();
            BeanUtils.copyProperties(entrustOrderMq, matchEventDubboReq);
            matchDubboService.push(matchEventDubboReq);

            // 保存深度图行情
            Double price = entrustOrderMq.getPrice();
            Double amount = entrustOrderMq.getAmount();
            Integer type = entrustOrderMq.getType();
            Integer direction = entrustOrderMq.getDirection();
            String key = direction == 1 ? CacheConstant.BUY_DEPTH : CacheConstant.SELL_DEPTH;
            String infoKey = direction == 1 ? CacheConstant.BUY_DEPTH_INFO : CacheConstant.SELL_DEPTH_INFO;
            // 如果是限价，处理行情深度
            if (type == 1) {
                // 行情深度处理
                redisTemplate.opsForZSet().add(key, price.toString(), price);
                redisTemplate.opsForValue().increment(String.format(infoKey, price),amount);
            }

            Set<String> depthPriceList = redisTemplate.opsForZSet().range(key, 0, 100);
            List<DepthVo> depthVoList = new ArrayList<>();
            if (depthPriceList != null) {
                for (String depthPrice : depthPriceList) {
                    String depthAmountKey = String.format(infoKey, depthPrice);
                    String depthAmountStr = redisTemplate.opsForValue().get(depthAmountKey);
                    DepthVo depthVo = new DepthVo();
                    depthVo.setPrice(Double.parseDouble(depthPrice));
                    double depthAmount = !StringUtils.isBlank(depthAmountStr) ? Double.parseDouble(depthAmountStr) : 0.00;
                    if (depthAmount <= 0) {
                        redisTemplate.opsForValue().decrement(depthAmountKey);
                        redisTemplate.opsForZSet().remove(key, depthPrice);
                        continue;
                    }
                    depthVo.setAmount(depthAmount);
                    depthVoList.add(depthVo);
                }

            }

            System.out.println(depthVoList);

            // 推送 ws 深度行情

        };

    }


}
