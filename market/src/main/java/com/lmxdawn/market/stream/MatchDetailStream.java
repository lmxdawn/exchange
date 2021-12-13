package com.lmxdawn.market.stream;

import com.lmxdawn.dubboapi.req.match.MatchEventDubboReq;
import com.lmxdawn.dubboapi.req.trade.EntrustOrderMatchDubboReq;
import com.lmxdawn.dubboapi.req.user.MemberCoinMatchDubboReq;
import com.lmxdawn.dubboapi.res.trade.EntrustOrderMatchDubboRes;
import com.lmxdawn.dubboapi.service.match.MatchDubboService;
import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.market.constant.CacheConstant;
import com.lmxdawn.market.mq.EntrustOrderMq;
import com.lmxdawn.market.mq.MatchDetailMq;
import com.lmxdawn.market.service.MatchService;
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
public class MatchDetailStream {

    @Autowired
    private MatchService matchService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @DubboReference
    private EntrustOrderDubboService entrustOrderDubboService;

    /**
     * 监听撮合成功的消息
     */
    @Bean
    public Consumer<MatchDetailMq> matchDetail() {

        return matchDetailMq -> {

            Long tradeCoinId = matchDetailMq.getTradeCoinId();
            Long coinId = matchDetailMq.getCoinId();
            Long symbol = Long.valueOf(tradeCoinId.toString() + coinId.toString());
            Long id = matchDetailMq.getId();
            Long matchId = matchDetailMq.getMatchId();
            Long memberId = matchDetailMq.getMemberId();
            Long matchMemberId = matchDetailMq.getMatchMemberId();
            Integer type = matchDetailMq.getType();
            Integer matchType = matchDetailMq.getMatchType();
            Integer direction = matchDetailMq.getDirection();
            Integer matchDirection = matchDetailMq.getMatchDirection();
            Integer isRobot = matchDetailMq.getIsRobot();
            Integer matchIsRobot = matchDetailMq.getMatchIsRobot();
            BigDecimal price = BigDecimal.valueOf(matchDetailMq.getPrice());
            BigDecimal amount = BigDecimal.valueOf(matchDetailMq.getAmount());
            Integer isComplete = matchDetailMq.getIsComplete();
            Integer matchIsComplete = matchDetailMq.getMatchIsComplete();

            BigDecimal completeMoney = amount.multiply(price);

            // 处理订单的完成量
            Set<Long> idSet = new HashSet<>();
            // 订单不是机器人
            if (isRobot == null || isRobot == 0) {
                idSet.add(id);
            }
            if (matchIsRobot == null || matchIsRobot == 0) {
                idSet.add(matchId);
            }
            if (idSet.size() > 0) {
                List<Long> ids = new ArrayList<>(idSet);
                Map<Long, EntrustOrderMatchDubboRes> entrustOrderMatchMap = entrustOrderDubboService.mapByIdIn(ids);
                if (entrustOrderMatchMap.size() > 0) {

                    // 订单状态的修改
                    EntrustOrderMatchDubboReq entrustOrderMatchDubboReq = new EntrustOrderMatchDubboReq();

                    // 用户余额修改的
                    MemberCoinMatchDubboReq memberCoinMatchDubboReq = new MemberCoinMatchDubboReq();
                    memberCoinMatchDubboReq.setTradeCoinId(tradeCoinId);
                    memberCoinMatchDubboReq.setCoinId(coinId);
                    // 订单
                    if (entrustOrderMatchMap.containsKey(id)) {
                        EntrustOrderMatchDubboRes entrustOrder = entrustOrderMatchMap.get(id);
                        // 市价买入，需要判断是否交易完，因为有除不尽的情况
                        if (isComplete == 1 && type == 2 && direction == 1) {
                            BigDecimal bigTotal = BigDecimal.valueOf(entrustOrder.getTotal());
                            BigDecimal bigTotalComplete = BigDecimal.valueOf(entrustOrder.getTotalComplete());
                            // 成交额 = 交易额 - 已完成的交易额
                            completeMoney = bigTotal.subtract(bigTotalComplete);
                        }
                        // 买入，则增加余额
                        if (direction == 1) {
                            memberCoinMatchDubboReq.setBuyMemberId(memberId);
                            memberCoinMatchDubboReq.setBuyMoney(amount.doubleValue());
                            memberCoinMatchDubboReq.setBuyUnfrozenMoney(completeMoney.doubleValue());
                        } else {
                            // 卖出，解冻余额
                            memberCoinMatchDubboReq.setSellMemberId(memberId);
                            memberCoinMatchDubboReq.setSellUnfrozenMoney(amount.doubleValue());
                        }
                        entrustOrderMatchDubboReq.setId(id);
                        entrustOrderMatchDubboReq.setAmountComplete(entrustOrder.getAmountComplete());
                        entrustOrderMatchDubboReq.setAmount(amount.doubleValue());
                        entrustOrderMatchDubboReq.setTotal(completeMoney.doubleValue());
                        entrustOrderMatchDubboReq.setStatus(isComplete == 1 ? 2 : 1);
                    }
                    // 对手单
                    if (entrustOrderMatchMap.containsKey(matchId)) {
                        EntrustOrderMatchDubboRes matchEntrustOrder = entrustOrderMatchMap.get(matchId);
                        // 市价买入，需要判断是否交易完，因为有除不尽的情况
                        if (matchIsComplete == 1 && matchType == 2 && matchDirection == 1) {
                            BigDecimal bigTotal = BigDecimal.valueOf(matchEntrustOrder.getTotal());
                            BigDecimal bigTotalComplete = BigDecimal.valueOf(matchEntrustOrder.getTotalComplete());
                            // 成交额 = 交易额 - 已完成的交易额
                            completeMoney = bigTotal.subtract(bigTotalComplete);
                        }
                        // 买入，则增加余额
                        if (matchDirection == 1) {
                            memberCoinMatchDubboReq.setBuyMemberId(matchMemberId);
                            memberCoinMatchDubboReq.setBuyMoney(amount.doubleValue());
                            memberCoinMatchDubboReq.setBuyUnfrozenMoney(completeMoney.doubleValue());
                        } else {
                            // 卖出，解冻余额
                            memberCoinMatchDubboReq.setSellMemberId(matchMemberId);
                            memberCoinMatchDubboReq.setSellUnfrozenMoney(amount.doubleValue());
                        }

                        entrustOrderMatchDubboReq.setMatchId(matchId);
                        entrustOrderMatchDubboReq.setMatchAmountComplete(matchEntrustOrder.getAmountComplete());
                        entrustOrderMatchDubboReq.setMatchAmount(amount.doubleValue());
                        entrustOrderMatchDubboReq.setMatchTotal(completeMoney.doubleValue());
                        entrustOrderMatchDubboReq.setMatchStatus(matchIsComplete == 1 ? 2 : 1);
                    }

                    // 处理交易
                    boolean complete = matchService.complete(memberCoinMatchDubboReq, entrustOrderMatchDubboReq);
                    if (!complete) {
                        throw new RuntimeException();
                    }
                }
            }

            // 减少撮合订单的深度
            String key = matchDirection == 1 ? CacheConstant.BUY_DEPTH : CacheConstant.SELL_DEPTH;
            key = String.format(key, symbol);
            String infoKey = matchDirection == 1 ? CacheConstant.BUY_DEPTH_INFO : CacheConstant.SELL_DEPTH_INFO;
            String amountKey = String.format(infoKey, symbol, price);
            Double increment = redisTemplate.opsForValue().increment(amountKey, -amount.doubleValue());
            if (increment == null || increment <= 0) {
                redisTemplate.opsForValue().decrement(amountKey);
                redisTemplate.opsForZSet().remove(key, price.toString());
            }

            Set<String> depthPriceList = redisTemplate.opsForZSet().range(key, 0, 100);
            List<DepthVo> depthVoList = new ArrayList<>();
            if (depthPriceList != null) {
                for (String depthPrice : depthPriceList) {
                    String depthAmountKey = String.format(infoKey, symbol, depthPrice);
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

            // 推送 ws 深度行情
            System.out.println(depthVoList);


        };

    }


}
