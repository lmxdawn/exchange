package com.lmxdawn.market.stream;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.dubboapi.req.trade.EntrustOrderMatchDubboReq;
import com.lmxdawn.dubboapi.req.user.MemberCoinMatchDubboReq;
import com.lmxdawn.dubboapi.res.trade.EntrustOrderMatchDubboRes;
import com.lmxdawn.dubboapi.service.trade.EntrustOrderDubboService;
import com.lmxdawn.market.constant.CacheConstant;
import com.lmxdawn.market.constant.MqTopicConstant;
import com.lmxdawn.market.mq.MatchDetailMq;
import com.lmxdawn.market.mq.WsMarketMq;
import com.lmxdawn.market.service.KLineService;
import com.lmxdawn.market.service.MatchService;
import com.lmxdawn.market.ws.DataVo;
import com.lmxdawn.market.ws.DepthVo;
import com.lmxdawn.market.ws.MatchVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
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

    @Autowired
    private StreamBridge streamBridge;

    @DubboReference
    private EntrustOrderDubboService entrustOrderDubboService;

    @Autowired
    private KLineService kLineService;

    /**
     * 监听撮合成功的消息
     */
    @Bean
    public Consumer<MatchDetailMq> matchDetail() {

        return matchDetailMq -> {
            Long tradeCoinId = matchDetailMq.getTradeCoinId();
            Long coinId = matchDetailMq.getCoinId();
            Long pair = Long.valueOf(tradeCoinId.toString() + coinId.toString());
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
            BigDecimal bigBuyFee = BigDecimal.valueOf(matchDetailMq.getBuyFee());
            Integer buyFeePrecision = matchDetailMq.getBuyFeePrecision();
            BigDecimal bigSellFee = BigDecimal.valueOf(matchDetailMq.getSellFee());
            Integer sellFeePrecision = matchDetailMq.getSellFeePrecision();
            Integer tradeAmountPrecision = matchDetailMq.getTradeAmountPrecision();

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
                        // 手续费
                        BigDecimal bigFeeMoney = BigDecimal.ZERO;
                        // 买入，增加余额
                        if (direction == 1) {
                            // 计算手续费
                            bigFeeMoney = amount.multiply(bigSellFee).setScale(sellFeePrecision, BigDecimal.ROUND_DOWN);
                            BigDecimal buyMoney = amount.compareTo(bigFeeMoney) > 0 ? amount.subtract(bigFeeMoney) : amount;
                            memberCoinMatchDubboReq.setBuyMemberId(memberId);
                            memberCoinMatchDubboReq.setBuyMoney(buyMoney.doubleValue());
                            memberCoinMatchDubboReq.setBuyMoneyFee(bigFeeMoney.doubleValue());
                            memberCoinMatchDubboReq.setBuyUnfrozenMoney(completeMoney.doubleValue());
                        } else {
                            // 卖出，增加余额
                            // 计算手续费
                            bigFeeMoney = completeMoney.multiply(bigBuyFee).setScale(buyFeePrecision, BigDecimal.ROUND_DOWN);
                            BigDecimal sellMoney = completeMoney.compareTo(bigFeeMoney) > 0 ? completeMoney.subtract(bigFeeMoney) : amount;
                            memberCoinMatchDubboReq.setSellMemberId(memberId);
                            memberCoinMatchDubboReq.setSellMoney(sellMoney.doubleValue());
                            memberCoinMatchDubboReq.setSellMoneyFee(bigFeeMoney.doubleValue());
                            memberCoinMatchDubboReq.setSellUnfrozenMoney(amount.doubleValue());
                        }
                        entrustOrderMatchDubboReq.setId(id);
                        entrustOrderMatchDubboReq.setDirection(direction);
                        entrustOrderMatchDubboReq.setMemberId(memberId);
                        entrustOrderMatchDubboReq.setAmountComplete(entrustOrder.getAmountComplete());
                        entrustOrderMatchDubboReq.setFee(bigFeeMoney.doubleValue());
                        entrustOrderMatchDubboReq.setStatus(isComplete == 1 ? 2 : 1);
                    }
                    // 对手单
                    if (entrustOrderMatchMap.containsKey(matchId)) {
                        EntrustOrderMatchDubboRes matchEntrustOrder = entrustOrderMatchMap.get(matchId);
                        // 市价买入，需要判断是否交易完，因为有除不尽的情况
                        if (matchIsComplete == 1 && matchType == 2 && matchDirection == 1) {
                            BigDecimal bigTotal = BigDecimal.valueOf(matchEntrustOrder.getTotal());
                            BigDecimal bigTotalComplete = BigDecimal.valueOf(matchEntrustOrder.getTotalComplete());
                            // 如果交易完了，但是有除不尽的情况 成交额 = 总交易额 - 已完成的交易额
                            completeMoney = bigTotal.subtract(bigTotalComplete);
                        }
                        // 手续费
                        BigDecimal bigFeeMoney = BigDecimal.ZERO;
                        // 买入，则增加余额
                        if (matchDirection == 1) {
                            // 计算手续费
                            bigFeeMoney = amount.multiply(bigSellFee).setScale(sellFeePrecision, BigDecimal.ROUND_DOWN);
                            BigDecimal buyMoney = amount.compareTo(bigFeeMoney) > 0 ? amount.subtract(bigFeeMoney) : amount;
                            memberCoinMatchDubboReq.setBuyMemberId(matchMemberId);
                            memberCoinMatchDubboReq.setBuyMoney(buyMoney.doubleValue());
                            memberCoinMatchDubboReq.setBuyMoneyFee(bigFeeMoney.doubleValue());
                            memberCoinMatchDubboReq.setBuyUnfrozenMoney(completeMoney.doubleValue());
                        } else {
                            // 卖出，解冻余额
                            // 计算手续费
                            bigFeeMoney = completeMoney.multiply(bigBuyFee).setScale(buyFeePrecision, BigDecimal.ROUND_DOWN);
                            BigDecimal sellMoney = completeMoney.compareTo(bigFeeMoney) > 0 ? completeMoney.subtract(bigFeeMoney) : amount;
                            memberCoinMatchDubboReq.setSellMemberId(matchMemberId);
                            memberCoinMatchDubboReq.setSellMoney(sellMoney.doubleValue());
                            memberCoinMatchDubboReq.setSellMoneyFee(bigFeeMoney.doubleValue());
                            memberCoinMatchDubboReq.setSellUnfrozenMoney(amount.doubleValue());
                        }

                        entrustOrderMatchDubboReq.setMatchId(matchId);
                        entrustOrderMatchDubboReq.setMatchDirection(matchDirection);
                        entrustOrderMatchDubboReq.setMatchMemberId(matchMemberId);
                        entrustOrderMatchDubboReq.setMatchAmountComplete(matchEntrustOrder.getAmountComplete());
                        entrustOrderMatchDubboReq.setMatchFee(bigFeeMoney.doubleValue());
                        entrustOrderMatchDubboReq.setMatchStatus(matchIsComplete == 1 ? 2 : 1);
                    }

                    entrustOrderMatchDubboReq.setCoinId(coinId);
                    entrustOrderMatchDubboReq.setTradeCoinId(tradeCoinId);
                    entrustOrderMatchDubboReq.setPrice(price.doubleValue());
                    entrustOrderMatchDubboReq.setAmount(amount.doubleValue());
                    entrustOrderMatchDubboReq.setTotal(completeMoney.doubleValue());

                    // 处理交易
                    boolean complete = matchService.complete(memberCoinMatchDubboReq, entrustOrderMatchDubboReq);
                    if (!complete) {
                        throw new RuntimeException();
                    }
                }
            }
            // 放大倍数，不然有精度问题
            BigDecimal bigPow = BigDecimal.valueOf(Math.pow(10.0, tradeAmountPrecision));
            // ws 推送的data数据
            DataVo dataVo = new DataVo();
            dataVo.setTradeCoinId(tradeCoinId);
            dataVo.setCoinId(coinId);
            // 如果订单是限价，修改深度图
            if (type == 1) {
                String key = direction == 1 ? CacheConstant.BUY_DEPTH : CacheConstant.SELL_DEPTH;
                key = String.format(key, pair);
                String infoKey = direction == 1 ? CacheConstant.BUY_DEPTH_INFO : CacheConstant.SELL_DEPTH_INFO;
                String amountKey = String.format(infoKey, pair, price);
                Long increment = redisTemplate.opsForValue().increment(amountKey, -amount.multiply(bigPow).longValue());
                if (increment == null || increment <= 0) {
                    redisTemplate.delete(amountKey);
                    redisTemplate.opsForZSet().remove(key, price.toString());
                }

                Set<String> depthPriceList = direction == 1 ? redisTemplate.opsForZSet().reverseRange(key, 0, 100) : redisTemplate.opsForZSet().range(key, 0, 100);
                List<DepthVo> depthVoList = new ArrayList<>();
                if (depthPriceList != null) {
                    for (String depthPrice : depthPriceList) {
                        String depthAmountKey = String.format(infoKey, pair, depthPrice);
                        String depthAmountStr = redisTemplate.opsForValue().get(depthAmountKey);
                        DepthVo depthVo = new DepthVo();
                        depthVo.setPrice(Double.parseDouble(depthPrice));
                        BigDecimal depthAmount = !StringUtils.isBlank(depthAmountStr) ? BigDecimal.valueOf(Long.parseLong(depthAmountStr)) : BigDecimal.ZERO;
                        if (depthAmount.compareTo(BigDecimal.ZERO) <= 0) {
                            redisTemplate.delete(depthAmountKey);
                            redisTemplate.opsForZSet().remove(key, depthPrice);
                            continue;
                        }
                        depthVo.setAmount(depthAmount.divide(bigPow, tradeAmountPrecision,BigDecimal.ROUND_DOWN).doubleValue());
                        depthVoList.add(depthVo);
                    }

                    // 买入
                    if (direction == 1) {
                        dataVo.setBuyList(depthVoList);
                    } else {
                        dataVo.setSellList(depthVoList);
                    }

                }
            }

            // 如果对手单是限价
            if (matchType == 1) {
                String matchKey = matchDirection == 1 ? CacheConstant.BUY_DEPTH : CacheConstant.SELL_DEPTH;
                matchKey = String.format(matchKey, pair);
                String matchInfoKey = matchDirection == 1 ? CacheConstant.BUY_DEPTH_INFO : CacheConstant.SELL_DEPTH_INFO;
                String matchAmountKey = String.format(matchInfoKey, pair, price);
                Long matchIncrement = redisTemplate.opsForValue().increment(matchAmountKey, -amount.multiply(bigPow).longValue());
                if (matchIncrement == null || matchIncrement <= 0) {
                    redisTemplate.delete(matchAmountKey);
                    redisTemplate.opsForZSet().remove(matchKey, price.toString());
                }

                Set<String> matchDepthPriceList = matchDirection == 1 ? redisTemplate.opsForZSet().reverseRange(matchKey, 0, 100) : redisTemplate.opsForZSet().range(matchKey, 0, 100);
                List<DepthVo> matchDepthVoList = new ArrayList<>();
                if (matchDepthPriceList != null) {
                    for (String depthPrice : matchDepthPriceList) {
                        String depthAmountKey = String.format(matchInfoKey, pair, depthPrice);
                        String depthAmountStr = redisTemplate.opsForValue().get(depthAmountKey);
                        DepthVo depthVo = new DepthVo();
                        depthVo.setPrice(Double.parseDouble(depthPrice));
                        BigDecimal depthAmount = !StringUtils.isBlank(depthAmountStr) ? BigDecimal.valueOf(Long.parseLong(depthAmountStr)) : BigDecimal.ZERO;
                        if (depthAmount.compareTo(BigDecimal.ZERO) <= 0) {
                            redisTemplate.delete(depthAmountKey);
                            redisTemplate.opsForZSet().remove(matchKey, depthPrice);
                            continue;
                        }
                        depthVo.setAmount(depthAmount.divide(bigPow, tradeAmountPrecision,BigDecimal.ROUND_DOWN).doubleValue());
                        matchDepthVoList.add(depthVo);
                    }

                    // 买入
                    if (matchDirection == 1) {
                        dataVo.setBuyList(matchDepthVoList);
                    } else {
                        dataVo.setSellList(matchDepthVoList);
                    }
                }
            }


            // 推送 ws 深度行情
            // 设置撮合信息
            MatchVo matchVo = new MatchVo();
            matchVo.setPrice(price.doubleValue());
            matchVo.setAmount(amount.doubleValue());
            dataVo.setMatch(matchVo);
            // 组装ws数据
            WsMarketMq wsMarketMq = new WsMarketMq();
            wsMarketMq.setMemberId(memberId);
            wsMarketMq.setOrderId(id);
            wsMarketMq.setMatchMemberId(matchMemberId);
            wsMarketMq.setMatchOrderId(matchId);
            wsMarketMq.setData(JSON.toJSONString(dataVo));
            // 推送 ws 深度行情
            streamBridge.send(MqTopicConstant.WS_MARKET_TOPIC, wsMarketMq);

            // 保存k线图到MongoDB
            kLineService.install(tradeCoinId, coinId, price, amount);


        };

    }


}
