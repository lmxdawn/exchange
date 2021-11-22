package com.lmxdawn.dubboapi.interceptor;

import com.lmxdawn.dubboapi.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.dubbo.rpc.*;

public class ProviderRpcFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String key = "x3EEX8Di6sWfNjer486bnQAzfXWkX1R1";
        String token = invocation.getAttachment("token");
        System.out.println("提供端拦截了*****************************" + token);
        // 验证 token
        Claims claims = JwtUtils.parse(token, key);
        if (claims == null || !claims.get("key").equals(1)) {
            throw new RpcException("Sorry, your authorization code is wrong");
        }

        return invoker.invoke(invocation);
    }
}
