package com.lmxdawn.match.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.springframework.beans.BeanUtils;

public class MatchEventProducer {

    private final RingBuffer<MatchEvent> ringBuffer;

    public MatchEventProducer(RingBuffer<MatchEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(MatchEvent req) {
        long sequence = ringBuffer.next();
        try {
            MatchEvent event = ringBuffer.get(sequence);
            Integer type = req.getType();
            Integer direction = req.getDirection();
            // 如果是市价买单
            if (type == 2 && direction == 1) {
                req.setPrice(0.00);
                req.setAmount(0.00);
            } else {
                req.setTotal(0.00);
            }
            BeanUtils.copyProperties(req, event);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
