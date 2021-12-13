package com.lmxdawn.match.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmxdawn.match.constant.MqTopicConstant;
import com.lmxdawn.match.mq.MatchDetailMq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MatchEventHandler implements EventHandler<MatchEvent>, WorkHandler<MatchEvent> {

    private final StreamBridge streamBridge;

    public MatchEventHandler(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void onEvent(MatchEvent event, long sequence, boolean endOfBatch) {

        // 方向（1：买入，2：卖出）
        Integer direction = event.getDirection();
        List<MatchDetailMq> matchDetailMqList = new ArrayList<>();
        if (direction == 1) {
            // 买入，匹配卖单
            matchDetailMqList = MatchDataLimitSellHolder.match(event);
        } else {
            // 卖出，匹配卖单
            matchDetailMqList = MatchDataLimitBuyHolder.match(event);
        }

        for (MatchDetailMq matchDetailMq : matchDetailMqList) {
            streamBridge.send(MqTopicConstant.MATCH_DETAIL_TOPIC, matchDetailMq);
        }

    }


    @Override
    public void onEvent(MatchEvent event) {
        log.info("event: {}", event);
    }
}
