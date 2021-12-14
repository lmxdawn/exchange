package com.lmxdawn.wsroute.service.impl;

import com.lmxdawn.dubboapi.service.user.UserDubboService;
import com.lmxdawn.wsroute.service.UserLoginService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @DubboReference
    private UserDubboService userDubboService;

    @Override
    public Long Login(String token) {
        return userDubboService.Login(token);
    }
}
