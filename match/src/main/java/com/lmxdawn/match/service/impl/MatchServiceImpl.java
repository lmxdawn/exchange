package com.lmxdawn.match.service.impl;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmxdawn.match.disruptor.MatchEvent;
import com.lmxdawn.match.disruptor.MatchEventHandler;
import com.lmxdawn.match.disruptor.MatchEventProducer;
import com.lmxdawn.match.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private StreamBridge streamBridge;

    private MatchEventProducer producer;

    @PostConstruct
    private void init() {
        Disruptor<MatchEvent> disruptor = new Disruptor<>(
                MatchEvent::new,
                1024 * 1024,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new YieldingWaitStrategy()
        );
        disruptor.handleEventsWith(new MatchEventHandler(streamBridge));
        disruptor.start();
        RingBuffer<MatchEvent> ringBuffer = disruptor.getRingBuffer();
        producer = new MatchEventProducer(ringBuffer);
    }

    @Override
    public void publish(MatchEvent req) {

        Double price = req.getPrice();
        if (price <= 0) {
            return;
        }

        producer.onData(req);
    }
}
