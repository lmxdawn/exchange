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
        Long symbol = Long.valueOf(tradeCoinId.toString() + coinId.toString());

        List<MatchDetailMq> matchDetailMqList = new ArrayList<>();

        // 限价，优先去匹配未完成的市价卖单
        if (type == 1) {
            List<MatchEvent> marketList = MatchDataMarketSellHolder.getList(symbol);
            Iterator<MatchEvent> marketIterator = marketList.iterator();
            if (marketIterator.hasNext()) {
                MatchEvent market = marketIterator.next();
                BigDecimal bigAmount = BigDecimal.valueOf(market.getAmount());
                // 撮合明细
                MatchDetailMq matchDetailMq = new MatchDetailMq();
                matchDetailMq.setId(buy.getId());
                matchDetailMq.setMatchId(market.getId());
                matchDetailMq.setMemberId(buy.getMemberId());
                matchDetailMq.setMatchMemberId(market.getMemberId());
                matchDetailMq.setType(buy.getType());
                matchDetailMq.setMatchType(market.getType());
                matchDetailMq.setDirection(buy.getDirection());
                matchDetailMq.setMatchDirection(market.getDirection());
                matchDetailMq.setPrice(buyPrice.doubleValue());
                matchDetailMq.setIsComplete(0);
                matchDetailMq.setMatchIsComplete(0);
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

        // 匹配撮合
        Map<BigDecimal, List<MatchEvent>> map = getMap(symbol);

        // 限价判断数量大于0，市价判断成交量大于0
        if ((buyAmount.compareTo(BigDecimal.ZERO) > 0 || buyTotal.compareTo(BigDecimal.ZERO) > 0) && map.size() > 0) {
            Iterator<Map.Entry<BigDecimal, List<MatchEvent>>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<BigDecimal, List<MatchEvent>> listEntry = iterator.next();
                BigDecimal bigPrice = listEntry.getKey();
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
                    matchDetailMq.setId(buy.getId());
                    matchDetailMq.setMatchId(matchEvent.getId());
                    matchDetailMq.setMemberId(buy.getMemberId());
                    matchDetailMq.setMatchMemberId(matchEvent.getMemberId());
                    matchDetailMq.setType(buy.getType());
                    matchDetailMq.setMatchType(matchEvent.getType());
                    matchDetailMq.setDirection(buy.getDirection());
                    matchDetailMq.setMatchDirection(matchEvent.getDirection());
                    matchDetailMq.setPrice(matchEvent.getPrice());
                    matchDetailMq.setIsComplete(0);
                    matchDetailMq.setMatchIsComplete(0);
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


        System.out.println("                                                  ");
        System.out.println("--------------------------------------------------");
        System.out.println("                                                  ");
        System.out.println("*******************撮合买单 start*******************");
        // 没有深度，或者剩余没有撮合
        // 如果是限价
        if (type == 1) {
            if (buyAmount.compareTo(BigDecimal.ZERO) > 0) {
                buy.setAmount(buyAmount.doubleValue());
                // 限价，压入卖单
                MatchDataLimitBuyHolder.put(buy);
            }
        } else {
            if (buyTotal.compareTo(BigDecimal.ZERO) > 0) {
                buy.setTotal(buyTotal.doubleValue());
                // 市价，压入未完成的市价买单
                MatchDataMarketBuyHolder.add(buy);
            }
        }

        System.out.println(matchDetailMqList);
        System.out.println(MatchDataLimitBuyHolder.getMap(symbol));
        System.out.println(MatchDataLimitSellHolder.getMap(symbol));
        System.out.println(MatchDataMarketBuyHolder.getList(symbol));
        System.out.println(MatchDataMarketSellHolder.getList(symbol));

        return matchDetailMqList;
    }

    public static void put(MatchEvent event) {
        Long id = event.getId();
        if (LIST_INDEX_DATA.containsKey(id)) {
            return;
        }
        Long tradeCoinId = event.getTradeCoinId();
        Long coinId = event.getCoinId();
        Long symbol = Long.valueOf(tradeCoinId.toString() + coinId.toString());
        // 卖单队列价格升序排列
        TreeMap<BigDecimal, List<MatchEvent>> map = DATA.computeIfAbsent(symbol, k -> new TreeMap<>(Comparator.naturalOrder()));
        Double price = event.getPrice();
        BigDecimal bigPrice = BigDecimal.valueOf(price);
        List<MatchEvent> matchEvents = map.computeIfAbsent(bigPrice, k -> new ArrayList<>());
        int index = matchEvents.size();
        ListIndex listIndex = new ListIndex();
        listIndex.setPrice(bigPrice);
        listIndex.setIndex(index);
        listIndex.setSymbol(symbol);
        LIST_INDEX_DATA.put(id, listIndex);
        matchEvents.add(event);
        map.put(bigPrice, matchEvents);
    }

    public static MatchEvent get(Long id) {

        if (LIST_INDEX_DATA.containsKey(id)) {
            return null;
        }

        ListIndex listIndex = LIST_INDEX_DATA.get(id);
        Long symbol = listIndex.getSymbol();
        TreeMap<BigDecimal, List<MatchEvent>> map = DATA.get(symbol);
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

    public static Map<BigDecimal, List<MatchEvent>> getMap(Long symbol) {
        return DATA.computeIfAbsent(symbol, k -> new TreeMap<>());
    }

    public static void remove(Long id) {
        if (LIST_INDEX_DATA.containsKey(id)) {
            return;
        }

        ListIndex listIndex = LIST_INDEX_DATA.get(id);
        Long symbol = listIndex.getSymbol();
        TreeMap<BigDecimal, List<MatchEvent>> map = DATA.get(symbol);
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

    @Data
    private static class ListIndex {
        BigDecimal price;
        int index;
        Long symbol;
    }

}
