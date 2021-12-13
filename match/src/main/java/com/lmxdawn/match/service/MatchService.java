package com.lmxdawn.match.service;

import com.lmxdawn.match.disruptor.MatchEvent;

public interface MatchService {

    void publish(MatchEvent req);

}
