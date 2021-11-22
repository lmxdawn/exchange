package com.lmxdawn.dubboapi.interceptor;

import com.lmxdawn.dubboapi.util.JwtUtils;
import org.apache.dubbo.rpc.*;

import java.util.HashMap;
import java.util.Map;

public class ConsumerAuthFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String key = "x3EEX8Di6sWfNjer486bnQAzfXWkX1R1";
        Map<String, Object> claims = new HashMap<>();
        claims.put("key", 1);
        String token = JwtUtils.createToken(claims, key,86400L); // 一天后过期
        System.out.println("消费端拦截了*****************************" + token);
        invocation.setAttachment("token",token);
        return invoker.invoke(invocation);
    }
}
