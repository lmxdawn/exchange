package com.lmxdawn.match.disruptor;

import com.lmxdawn.match.mq.MatchDetailMq;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

/**
 * 买单的内存数据
 */
public class MatchDataLimitBuyHolder {

    // 撮合的数据
    private static final Map<Long, TreeMap<BigDecimal, List<MatchEvent>>> DATA = new HashMap<>();

    // 订单号的下标
    private static final Map<Long, ListIndex> LIST_INDEX_DATA = new HashMap<>();

    /**
     * 匹配订单
     */
    public static List<MatchDetailMq> match(MatchEvent sell) {

        // 类型（1：限价，2：市价）
        Integer type = sell.getType();
        BigDecimal sellPrice = BigDecimal.valueOf(sell.getPrice());
        BigDecimal sellAmount = BigDecimal.valueOf(sell.getAmount());
        // 精度
        Integer tradeAmountPrecision = sell.getTradeAmountPrecision();
        Long tradeCoinId = sell.getTradeCoinId();
        Long coinId = sell.getCoinId();
        Long pair = Long.valueOf(tradeCoinId.toString() + coinId.toString());
        Double buyFee = sell.getBuyFee();
        Integer buyFeePrecision = sell.getBuyFeePrecision();
        Double sellFee = sell.getSellFee();
        Integer sellFeePrecision = sell.getSellFeePrecision();

        List<MatchDetailMq> matchDetailMqList = new ArrayList<>();

        // 限价，优先去匹配未完成的市价买单
        if (type == 1) {
            List<MatchEvent> marketList = MatchDataMarketBuyHolder.getList(pair);
            Iterator<MatchEvent> marketIterator = marketList.iterator();
            if (marketIterator.hasNext()) {
                MatchEvent market = marketIterator.next();
                BigDecimal buyTotal = BigDecimal.valueOf(market.getTotal());
                // 市价买单数量 = 总额/限价卖单的单价
                BigDecimal bigAmount = buyTotal.divide(sellPrice, tradeAmountPrecision, BigDecimal.ROUND_DOWN);
                // 撮合明细
                MatchDetailMq matchDetailMq = new MatchDetailMq();
                matchDetailMq.setTradeCoinId(tradeCoinId);
                matchDetailMq.setCoinId(coinId);
                matchDetailMq.setId(sell.getId());
                matchDetailMq.setMatchId(market.getId());
                matchDetailMq.setMemberId(sell.getMemberId());
                matchDetailMq.setMatchMemberId(market.getMemberId());
                matchDetailMq.setType(sell.getType());
                matchDetailMq.setMatchType(market.getType());
                matchDetailMq.setDirection(sell.getDirection());
                matchDetailMq.setMatchDirection(market.getDirection());
                matchDetailMq.setMatchPrice(sellPrice.doubleValue());
                matchDetailMq.setPrice(sellPrice.doubleValue());
                matchDetailMq.setIsComplete(0);
                matchDetailMq.setMatchIsComplete(0);
                matchDetailMq.setIsRobot(sell.getIsRobot());
                matchDetailMq.setMatchIsRobot(market.getIsRobot());
                matchDetailMq.setBuyFee(buyFee);
                matchDetailMq.setBuyFeePrecision(buyFeePrecision);
                matchDetailMq.setSellFee(sellFee);
                matchDetailMq.setSellFeePrecision(sellFeePrecision);
                matchDetailMq.setTradeAmountPrecision(tradeAmountPrecision);
                // 成交量
                BigDecimal completeAmount = bigAmount;
                // 订单数量相等
                if (bigAmount.compareTo(sellAmount) == 0) {
                    matchDetailMq.setIsComplete(1);
                    matchDetailMq.setMatchIsComplete(1);
                    marketIterator.remove();
                } else if (bigAmount.compareTo(sellAmount) < 0) {
                    // 撮合订单小于当前卖单
                    matchDetailMq.setMatchIsComplete(1);
                    marketIterator.remove();
                } else {
                    matchDetailMq.setIsComplete(1);
                    completeAmount = sellAmount;
                    // 更新撮合订单
                    buyTotal = buyTotal.subtract(completeAmount.multiply(sellPrice));
                    market.setTotal(buyTotal.doubleValue());
                }

                matchDetailMq.setAmount(completeAmount.doubleValue());
                // 增加明细
                matchDetailMqList.add(matchDetailMq);

                sellAmount = sellAmount.subtract(completeAmount);
            }
        }

        System.out.println("                                                  ");
        System.out.println("--------------------------------------------------");
        System.out.println("                                                  ");
        System.out.println("*******************撮合卖单 start*******************");
        // 匹配撮合
        Map<BigDecimal, List<MatchEvent>> map = getMap(pair);
        System.out.println("买盘当前数据：" + map);
        // 判断数量是否还大于0
        if (sellAmount.compareTo(BigDecimal.ZERO) > 0 && map.size() > 0) {
            Iterator<Map.Entry<BigDecimal, List<MatchEvent>>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<BigDecimal, List<MatchEvent>> listEntry = iterator.next();
                BigDecimal bigPrice = listEntry.getKey();
                System.out.println("卖单价格：" + sellPrice + "，买单价格：" + bigPrice);
                // 限价，并且卖单大于第一个撮合买单，则退出
                if (type == 1 && sellPrice.compareTo(bigPrice) > 0) {
                    break;
                }
                // 可成交
                List<MatchEvent> eventList = listEntry.getValue();
                Iterator<MatchEvent> eventIterator = eventList.iterator();
                while (eventIterator.hasNext()) {
                    MatchEvent matchEvent = eventIterator.next();
                    BigDecimal bigAmount = BigDecimal.valueOf(matchEvent.getAmount());
                    // 撮合明细
                    MatchDetailMq matchDetailMq = new MatchDetailMq();
                    matchDetailMq.setTradeCoinId(tradeCoinId);
                    matchDetailMq.setCoinId(coinId);
                    matchDetailMq.setId(sell.getId());
                    matchDetailMq.setMatchId(matchEvent.getId());
                    matchDetailMq.setMemberId(sell.getMemberId());
                    matchDetailMq.setMatchMemberId(matchEvent.getMemberId());
                    matchDetailMq.setType(sell.getType());
                    matchDetailMq.setMatchType(matchEvent.getType());
                    matchDetailMq.setDirection(sell.getDirection());
                    matchDetailMq.setMatchDirection(matchEvent.getDirection());
                    matchDetailMq.setMatchPrice(sellPrice.doubleValue());
                    matchDetailMq.setPrice(matchEvent.getPrice());
                    matchDetailMq.setIsComplete(0);
                    matchDetailMq.setMatchIsComplete(0);
                    matchDetailMq.setIsRobot(sell.getIsRobot());
                    matchDetailMq.setMatchIsRobot(matchEvent.getIsRobot());
                    matchDetailMq.setBuyFee(buyFee);
                    matchDetailMq.setBuyFeePrecision(buyFeePrecision);
                    matchDetailMq.setSellFee(sellFee);
                    matchDetailMq.setSellFeePrecision(sellFeePrecision);
                    matchDetailMq.setTradeAmountPrecision(tradeAmountPrecision);
                    // 成交量
                    BigDecimal completeAmount = bigAmount;
                    // 订单数量相等
                    if (bigAmount.compareTo(sellAmount) == 0) {
                        matchDetailMq.setIsComplete(1);
                        matchDetailMq.setMatchIsComplete(1);
                        eventIterator.remove();
                    } else if (bigAmount.compareTo(sellAmount) < 0) {
                        // 撮合订单数量小于当前卖单数量，则还需要找
                        matchDetailMq.setMatchIsComplete(1);
                        eventIterator.remove();
                    } else {
                        completeAmount = sellAmount;
                        matchDetailMq.setIsComplete(1);
                        // 更新撮合订单数量
                        matchEvent.setAmount(bigAmount.subtract(completeAmount).doubleValue());
                    }

                    System.out.println("撮合数量：" + bigAmount + "，卖单数量：" + sellAmount + "，实际数量：" + completeAmount);

                    matchDetailMq.setAmount(completeAmount.doubleValue());
                    // 增加明细
                    matchDetailMqList.add(matchDetailMq);

                    sellAmount = sellAmount.subtract(completeAmount);
                    if (sellAmount.compareTo(BigDecimal.ZERO) <= 0) {
                        break;
                    }
                }

                if (eventList.size() == 0) {
                    iterator.remove();
                }
            }
        }

        // 剩余没有撮合
        if (sellAmount.compareTo(BigDecimal.ZERO) > 0) {
            sell.setAmount(sellAmount.doubleValue());
            System.out.println("剩余没有撮合");
            System.out.println(sell);
            if (type == 1) {
                // 限价，压入卖单
                MatchDataLimitSellHolder.put(sell);
            } else {
                // 市价，压入未完成卖单
                MatchDataMarketSellHolder.add(sell);
            }
        }

        System.out.println("撮合队列：" + matchDetailMqList);
        System.out.println("买盘数据：");
        MatchDataLimitBuyHolder.dump(pair);
        System.out.println("卖盘数据：");
        MatchDataLimitSellHolder.dump(pair);
        System.out.println("市场买盘" + MatchDataMarketBuyHolder.getList(pair));
        System.out.println("市场卖盘" + MatchDataMarketSellHolder.getList(pair));

        return matchDetailMqList;
    }

    // 测试
    // public static void main(String[] args) {
    //
    //
    //     // 卖盘
    //     MatchEvent matchEvent = new MatchEvent();
    //     matchEvent.setId(143L);
    //     matchEvent.setMemberId(4L);
    //     matchEvent.setTradeCoinId(3L);
    //     matchEvent.setCoinId(1L);
    //     matchEvent.setType(1);
    //     matchEvent.setDirection(2);
    //     matchEvent.setAmount(1.0);
    //     matchEvent.setPrice(6.0);
    //     matchEvent.setTotal(0.0);
    //     matchEvent.setBuyFee(0.1);
    //     matchEvent.setBuyFeePrecision(2);
    //     matchEvent.setSellFee(0.0);
    //     matchEvent.setSellFeePrecision(0);
    //     matchEvent.setTradeAmountPrecision(2);
    //     MatchDataLimitBuyHolder.match(matchEvent);
    //
    //     matchEvent = new MatchEvent();
    //     matchEvent.setId(142L);
    //     matchEvent.setMemberId(4L);
    //     matchEvent.setTradeCoinId(3L);
    //     matchEvent.setCoinId(1L);
    //     matchEvent.setType(1);
    //     matchEvent.setDirection(2);
    //     matchEvent.setAmount(1.0);
    //     matchEvent.setPrice(7.0);
    //     matchEvent.setTotal(0.0);
    //     matchEvent.setBuyFee(0.1);
    //     matchEvent.setBuyFeePrecision(2);
    //     matchEvent.setSellFee(0.0);
    //     matchEvent.setSellFeePrecision(0);
    //     matchEvent.setTradeAmountPrecision(2);
    //     MatchDataLimitBuyHolder.match(matchEvent);
    //
    //
    //     // 买盘
    //     // {2.0=[MatchEvent(id=148, memberId=4, tradeCoinId=3, coinId=1, type=1, direction=1, price=2.0, amount=1.0, total=0.0, buyFee=0.1, buyFeePrecision=2, sellFee=0.0, sellFeePrecision=0, tradeAmountPrecision=2, isRobot=null)],
    //     // 3.0=[MatchEvent(id=
    //     // 147
    //     // , memberId=
    //     // 4
    //     // , tradeCoinId=
    //     // 3
    //     // , coinId=
    //     // 1
    //     // , type=
    //     // 1
    //     // , direction=
    //     // 1
    //     // , price=
    //     // 3.0
    //     // , amount=
    //     // 1.0
    //     // , total=
    //     // 0.0
    //     // , buyFee=
    //     // 0.1
    //     // , buyFeePrecision=
    //     // 2
    //     // , sellFee=
    //     // 0.0
    //     // , sellFeePrecision=
    //     // 0
    //     // , tradeAmountPrecision=
    //     // 2
    //     // , isRobot=null)],
    //     // 4.0=[MatchEvent(id=146, memberId=4, tradeCoinId=3, coinId=1, type=1, direction=1, price=4.0, amount=1.0, total=0.0, buyFee=0.1, buyFeePrecision=2, sellFee=0.0, sellFeePrecision=0, tradeAmountPrecision=2, isRobot=null)]}
    //
    //     matchEvent = new MatchEvent();
    //     matchEvent.setId(146L);
    //     matchEvent.setMemberId(4L);
    //     matchEvent.setTradeCoinId(3L);
    //     matchEvent.setCoinId(1L);
    //     matchEvent.setType(1);
    //     matchEvent.setDirection(1);
    //     matchEvent.setAmount(1.0);
    //     matchEvent.setPrice(4.0);
    //     matchEvent.setTotal(0.0);
    //     matchEvent.setBuyFee(0.1);
    //     matchEvent.setBuyFeePrecision(2);
    //     matchEvent.setSellFee(0.0);
    //     matchEvent.setSellFeePrecision(0);
    //     matchEvent.setTradeAmountPrecision(2);
    //     MatchDataLimitSellHolder.match(matchEvent);
    //
    //     matchEvent = new MatchEvent();
    //     matchEvent.setId(147L);
    //     matchEvent.setMemberId(4L);
    //     matchEvent.setTradeCoinId(3L);
    //     matchEvent.setCoinId(1L);
    //     matchEvent.setType(1);
    //     matchEvent.setDirection(1);
    //     matchEvent.setAmount(1.0);
    //     matchEvent.setPrice(3.0);
    //     matchEvent.setTotal(0.0);
    //     matchEvent.setBuyFee(0.1);
    //     matchEvent.setBuyFeePrecision(2);
    //     matchEvent.setSellFee(0.0);
    //     matchEvent.setSellFeePrecision(0);
    //     matchEvent.setTradeAmountPrecision(2);
    //     MatchDataLimitSellHolder.match(matchEvent);
    //
    // }

    public static void put(MatchEvent event) {
        Long id = event.getId();
        // 处理机器人订单的问题
        if (id > 0 && LIST_INDEX_DATA.containsKey(id)) {
            return;
        }
        Long tradeCoinId = event.getTradeCoinId();
        Long coinId = event.getCoinId();
        Long pair = Long.valueOf(tradeCoinId.toString() + coinId.toString());
        // 买单队列价格降序排列
        TreeMap<BigDecimal, List<MatchEvent>> map = getMap(pair);
        Double price = event.getPrice();
        BigDecimal bigPrice = BigDecimal.valueOf(price);
        List<MatchEvent> matchEvents = map.computeIfAbsent(bigPrice, k -> new ArrayList<>());
        int index = matchEvents.size();
        ListIndex listIndex = new ListIndex();
        listIndex.setPrice(bigPrice);
        listIndex.setIndex(index);
        listIndex.setPair(pair);
        if (id > 0) {
            LIST_INDEX_DATA.put(id, listIndex);
        }
        matchEvents.add(event);
        map.put(bigPrice, matchEvents);
    }

    public static MatchEvent get(Long id) {

        if (LIST_INDEX_DATA.containsKey(id)) {
            return null;
        }

        ListIndex listIndex = LIST_INDEX_DATA.get(id);
        Long pair = listIndex.getPair();
        TreeMap<BigDecimal, List<MatchEvent>> map = DATA.get(pair);
        if (map == null) {
            return null;
        }
        BigDecimal bigPrice = listIndex.getPrice();
        List<MatchEvent> matchEvents = map.get(bigPrice);
        if (matchEvents == null) {
            return null;
        }
        int index = listIndex.getIndex();

        return matchEvents.get(index);
    }

    public static Map<Long, TreeMap<BigDecimal, List<MatchEvent>>> getData() {
        return DATA;
    }

    public static TreeMap<BigDecimal, List<MatchEvent>> getMap(Long pair) {
        return DATA.computeIfAbsent(pair, k -> new TreeMap<>(Comparator.reverseOrder()));
    }

    public static void remove(Long id) {
        if (LIST_INDEX_DATA.containsKey(id)) {
            return;
        }

        ListIndex listIndex = LIST_INDEX_DATA.get(id);
        Long pair = listIndex.getPair();
        TreeMap<BigDecimal, List<MatchEvent>> map = DATA.get(pair);
        if (map == null) {
            return;
        }
        BigDecimal bigPrice = listIndex.getPrice();
        List<MatchEvent> matchEvents = map.get(bigPrice);
        if (matchEvents == null) {
            return;
        }
        int size = matchEvents.size();
        int index = listIndex.getIndex();
        if (size <= index) {
            return;
        }
        matchEvents.remove(index);
    }

    public static void dump(Long pair) {
        Map<BigDecimal, List<MatchEvent>> map = getMap(pair);
        for (Map.Entry<BigDecimal, List<MatchEvent>> listEntry : map.entrySet()) {
            BigDecimal bigPrice = listEntry.getKey();
            System.out.println("深度价格信息：" + bigPrice);
        }
    }
    @Data
    private static class ListIndex {
        BigDecimal price;
        int index;
        Long pair;
    }

}
