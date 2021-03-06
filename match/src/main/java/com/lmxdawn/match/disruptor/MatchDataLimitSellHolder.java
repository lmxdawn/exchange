package com.lmxdawn.match.disruptor;

import com.lmxdawn.match.mq.MatchDetailMq;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

/**
 * 买单的内存数据
 */
public class MatchDataLimitSellHolder {

    // 撮合的数据
    private static final Map<Long, TreeMap<BigDecimal, List<MatchEvent>>> DATA = new HashMap<>();

    // 订单号的下标
    private static final Map<Long, ListIndex> LIST_INDEX_DATA = new HashMap<>();

    /**
     * 匹配订单
     */
    public static List<MatchDetailMq> match(MatchEvent buy) {

        // 类型（1：限价，2：市价）
        Integer type = buy.getType();
        BigDecimal buyPrice = BigDecimal.valueOf(buy.getPrice());
        BigDecimal buyAmount = BigDecimal.valueOf(buy.getAmount());
        BigDecimal buyTotal = BigDecimal.valueOf(buy.getTotal());
        // 精度
        Integer tradeAmountPrecision = buy.getTradeAmountPrecision();
        Long tradeCoinId = buy.getTradeCoinId();
        Long coinId = buy.getCoinId();
        Long pair = Long.valueOf(tradeCoinId.toString() + coinId.toString());
        Double buyFee = buy.getBuyFee();
        Integer buyFeePrecision = buy.getBuyFeePrecision();
        Double sellFee = buy.getSellFee();
        Integer sellFeePrecision = buy.getSellFeePrecision();

        List<MatchDetailMq> matchDetailMqList = new ArrayList<>();

        // 限价，优先去匹配未完成的市价卖单
        if (type == 1) {
            List<MatchEvent> marketList = MatchDataMarketSellHolder.getList(pair);
            Iterator<MatchEvent> marketIterator = marketList.iterator();
            if (marketIterator.hasNext()) {
                MatchEvent market = marketIterator.next();
                BigDecimal bigAmount = BigDecimal.valueOf(market.getAmount());
                // 撮合明细
                MatchDetailMq matchDetailMq = new MatchDetailMq();
                matchDetailMq.setTradeCoinId(tradeCoinId);
                matchDetailMq.setCoinId(coinId);
                matchDetailMq.setId(buy.getId());
                matchDetailMq.setMatchId(market.getId());
                matchDetailMq.setMemberId(buy.getMemberId());
                matchDetailMq.setMatchMemberId(market.getMemberId());
                matchDetailMq.setType(buy.getType());
                matchDetailMq.setMatchType(market.getType());
                matchDetailMq.setDirection(buy.getDirection());
                matchDetailMq.setMatchDirection(market.getDirection());
                matchDetailMq.setMatchPrice(buyPrice.doubleValue());
                matchDetailMq.setPrice(buyPrice.doubleValue());
                matchDetailMq.setIsComplete(0);
                matchDetailMq.setMatchIsComplete(0);
                matchDetailMq.setIsRobot(buy.getIsRobot());
                matchDetailMq.setMatchIsRobot(market.getIsRobot());
                matchDetailMq.setBuyFee(buyFee);
                matchDetailMq.setBuyFeePrecision(buyFeePrecision);
                matchDetailMq.setSellFee(sellFee);
                matchDetailMq.setSellFeePrecision(sellFeePrecision);
                matchDetailMq.setTradeAmountPrecision(tradeAmountPrecision);
                // 成交量
                BigDecimal completeAmount = bigAmount;
                // 订单相等
                if (bigAmount.compareTo(buyAmount) == 0) {
                    matchDetailMq.setIsComplete(1);
                    matchDetailMq.setMatchIsComplete(1);
                    marketIterator.remove();
                } else if (bigAmount.compareTo(buyAmount) < 0) {
                    // 撮合订单小于当前买单
                    matchDetailMq.setMatchIsComplete(1);
                    marketIterator.remove();
                } else {
                    matchDetailMq.setIsComplete(1);
                    completeAmount = buyAmount;
                    // 更新撮合订单
                    bigAmount = bigAmount.subtract(completeAmount);
                    market.setAmount(bigAmount.doubleValue());
                }
                matchDetailMq.setAmount(completeAmount.doubleValue());
                // 增加明细
                matchDetailMqList.add(matchDetailMq);

                buyAmount = buyAmount.subtract(completeAmount);
            }
        }

        System.out.println("                                                  ");
        System.out.println("--------------------------------------------------");
        System.out.println("                                                  ");
        System.out.println("*******************撮合买单 start*******************");
        // 匹配撮合
        Map<BigDecimal, List<MatchEvent>> map = getMap(pair);
        System.out.println("卖盘当前数据：" + map);

        // 限价判断数量大于0，市价判断成交量大于0
        if ((buyAmount.compareTo(BigDecimal.ZERO) > 0 || buyTotal.compareTo(BigDecimal.ZERO) > 0) && map.size() > 0) {
            Iterator<Map.Entry<BigDecimal, List<MatchEvent>>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<BigDecimal, List<MatchEvent>> listEntry = iterator.next();
                BigDecimal bigPrice = listEntry.getKey();
                System.out.println("买单价格：" + buyPrice + "，卖单价格：" + bigPrice);
                // 限价，并且买单价格小于第一个撮合价格，则退出
                if (type == 1 && buyPrice.compareTo(bigPrice) < 0) {
                    break;
                }
                // 可成交
                List<MatchEvent> eventList = listEntry.getValue();
                Iterator<MatchEvent> eventIterator = eventList.iterator();
                while (eventIterator.hasNext()) {
                    // 市价，成交数量用总额/当前价格
                    if (type == 2) {
                        buyAmount = buyTotal.divide(bigPrice, tradeAmountPrecision, BigDecimal.ROUND_DOWN);
                    }
                    MatchEvent matchEvent = eventIterator.next();
                    BigDecimal bigAmount = BigDecimal.valueOf(matchEvent.getAmount());
                    // 撮合明细
                    MatchDetailMq matchDetailMq = new MatchDetailMq();
                    matchDetailMq.setTradeCoinId(tradeCoinId);
                    matchDetailMq.setCoinId(coinId);
                    matchDetailMq.setId(buy.getId());
                    matchDetailMq.setMatchId(matchEvent.getId());
                    matchDetailMq.setMemberId(buy.getMemberId());
                    matchDetailMq.setMatchMemberId(matchEvent.getMemberId());
                    matchDetailMq.setType(buy.getType());
                    matchDetailMq.setMatchType(matchEvent.getType());
                    matchDetailMq.setDirection(buy.getDirection());
                    matchDetailMq.setMatchDirection(matchEvent.getDirection());
                    matchDetailMq.setMatchPrice(buyPrice.doubleValue());
                    matchDetailMq.setPrice(matchEvent.getPrice());
                    matchDetailMq.setIsComplete(0);
                    matchDetailMq.setMatchIsComplete(0);
                    matchDetailMq.setIsRobot(buy.getIsRobot());
                    matchDetailMq.setMatchIsRobot(matchEvent.getIsRobot());
                    matchDetailMq.setBuyFee(buyFee);
                    matchDetailMq.setBuyFeePrecision(buyFeePrecision);
                    matchDetailMq.setSellFee(sellFee);
                    matchDetailMq.setSellFeePrecision(sellFeePrecision);
                    matchDetailMq.setTradeAmountPrecision(tradeAmountPrecision);
                    // 成交量
                    BigDecimal completeAmount = bigAmount;
                    // 订单数量相等
                    if (bigAmount.compareTo(buyAmount) == 0) {
                        matchDetailMq.setIsComplete(1);
                        matchDetailMq.setMatchIsComplete(1);
                        eventIterator.remove();
                    } else if (bigAmount.compareTo(buyAmount) < 0) {
                        // 撮合订单小于当前买单，则还需要找
                        matchDetailMq.setMatchIsComplete(1);
                        eventIterator.remove();
                    } else {
                        completeAmount = buyAmount;
                        matchDetailMq.setIsComplete(1);
                        // 更新撮合订单数量
                        matchEvent.setAmount(bigAmount.subtract(completeAmount).doubleValue());
                    }

                    System.out.println("撮合数量：" + bigAmount + "，买单数量：" + buyAmount + "，实际数量：" + completeAmount);

                    matchDetailMq.setAmount(completeAmount.doubleValue());
                    // 增加明细
                    matchDetailMqList.add(matchDetailMq);

                    // 如果是限价
                    if (type == 1) {
                        buyAmount = buyAmount.subtract(completeAmount);
                        if (buyAmount.compareTo(BigDecimal.ZERO) <= 0) {
                            break;
                        }
                    } else {
                        // 市价，判断成交额
                        buyTotal = buyTotal.subtract(completeAmount.multiply(bigPrice));
                        if (buyTotal.compareTo(BigDecimal.ZERO) <= 0) {
                            break;
                        }
                    }
                }

                if (eventList.size() == 0) {
                    iterator.remove();
                }
            }
        }


        // 没有深度，或者剩余没有撮合
        // 如果是限价
        if (type == 1) {
            if (buyAmount.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("剩余没有撮合1");
                System.out.println(buyAmount);
                buy.setAmount(buyAmount.doubleValue());
                // 限价，压入买单
                MatchDataLimitBuyHolder.put(buy);
                System.out.println(MatchDataLimitBuyHolder.getMap(pair));
            }
        } else {
            if (buyTotal.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("剩余没有撮合2");
                System.out.println(buyTotal);
                buy.setTotal(buyTotal.doubleValue());
                // 市价，压入未完成的市价买单
                MatchDataMarketBuyHolder.add(buy);
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

    public static void put(MatchEvent event) {
        Long id = event.getId();
        // 处理机器人订单的问题
        if (id > 0 && LIST_INDEX_DATA.containsKey(id)) {
            return;
        }
        Long tradeCoinId = event.getTradeCoinId();
        Long coinId = event.getCoinId();
        Long pair = Long.valueOf(tradeCoinId.toString() + coinId.toString());
        // 卖单队列价格升序排列
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
        return DATA.computeIfAbsent(pair, k -> new TreeMap<>(Comparator.naturalOrder()));
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
