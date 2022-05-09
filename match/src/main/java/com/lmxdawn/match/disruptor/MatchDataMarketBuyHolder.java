package com.lmxdawn.match.disruptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 未成交的市价买单内存数据
 */
public class MatchDataMarketBuyHolder {

    // 撮合的数据
    private static final Map<Long, List<MatchEvent>> DATA = new HashMap<>();

    public static void add(MatchEvent event) {
        Long tradeCoinId = event.getTradeCoinId();
        Long coinId = event.getCoinId();
        Long pair = Long.valueOf(tradeCoinId.toString() + coinId.toString());
        List<MatchEvent> eventList = DATA.computeIfAbsent(pair, k -> new ArrayList<>());
        eventList.add(event);
    }

    public static List<MatchEvent> getList(Long pair) {
        if (!DATA.containsKey(pair)) {
            return new ArrayList<>();
        }
        return DATA.get(pair);
    }

}
