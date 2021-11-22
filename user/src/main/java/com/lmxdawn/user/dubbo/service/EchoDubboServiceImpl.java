package com.lmxdawn.user.dubbo.service;

import com.lmxdawn.dubboapi.service.EchoDubboService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class EchoDubboServiceImpl implements EchoDubboService {
    @Override
    public String echo(String message) {
        return "Echo " + message;
    }
}
