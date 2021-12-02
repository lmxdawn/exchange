package com.lmxdawn.user.dubbo.service;

import com.lmxdawn.dubboapi.service.user.UserDubboService;
import com.lmxdawn.user.service.LoginService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class UserDubboServiceImpl implements UserDubboService {

    @Autowired
    private LoginService loginService;

    @Override
    public Long Login(String token) {
        return loginService.verify(token);
    }
}
