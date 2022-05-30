package com.lmxdawn.market.controller;

import com.lmxdawn.market.constant.CacheConstant;
import com.lmxdawn.market.res.BaseRes;
import com.lmxdawn.market.res.EchoRes;
import com.lmxdawn.market.util.ResultVOUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(tags = "测试接口")
@RestController
public class EchoController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @ApiOperation(value = "打印信息", notes = "打印信息出来")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str", value = "需要打印的字符", dataType = "string", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = EchoRes.class),
            @ApiResponse(code = 401, message = "xxx具体错误")
    })
    @GetMapping("/echo/{str}")
    public BaseRes<EchoRes> echo(@PathVariable String str) {
        EchoRes echoResponse = new EchoRes();
        echoResponse.setStr(str);
        List<ServiceInstance> instances = discoveryClient.getInstances("service-im");
        for (ServiceInstance i : instances) {
            System.out.println(i.getHost() + i.getPort());
            Map<String, String> metadata = i.getMetadata();
            for (String ii : metadata.keySet()) {
                if (ii.equals("server-port") || ii.equals("ws-port")) {
                    System.out.println(ii + ": " + metadata.get(ii));
                }
            }
        }
        return ResultVOUtils.success(echoResponse);
    }

    @GetMapping("/sdf")
    public BaseRes<EchoRes> rr() {

        Map<String, Double> map = new HashMap<>();
        map.put("3605.484", 3605.484);
        map.put("3594.668", 3594.668);
        map.put("3583.884", 3583.884);
        map.put("3573.132", 3573.132);
        map.put("3562.413", 3562.413);
        map.put("3551.726", 3551.726);
        map.put("3541.071", 3541.071);
        map.put("3530.448", 3530.448);
        map.put("3519.857", 3519.857);
        map.put("3509.297", 3509.297);
        map.put("3498.769", 3498.769);
        map.put("3488.273", 3488.273);
        map.put("3477.808", 3477.808);
        map.put("3467.375", 3467.375);
        map.put("3456.973", 3456.973);
        map.put("3446.602", 3446.602);
        map.put("3436.262", 3436.262);
        map.put("3425.953", 3425.953);
        map.put("3415.675", 3415.675);
        map.put("3405.428", 3405.428);
        map.put("3395.212", 3395.212);
        map.put("3385.026", 3385.026);
        map.put("3374.871", 3374.871);
        map.put("3364.746", 3364.746);
        map.put("3354.652", 3354.652);
        map.put("3344.588", 3344.588);
        map.put("3334.554", 3334.554);
        map.put("3324.55", 3324.55);
        map.put("3314.576", 3314.576);
        map.put("3304.632", 3304.632);
        map.put("3647.242", 3647.242);
        map.put("3658.184", 3658.184);
        map.put("3669.159", 3669.159);
        map.put("3680.166", 3680.166);
        map.put("3691.206", 3691.206);
        map.put("3702.28", 3702.28);
        map.put("3713.387", 3713.387);
        map.put("3724.527", 3724.527);
        map.put("3735.701", 3735.701);
        map.put("3746.908", 3746.908);
        map.put("3758.149", 3758.149);
        map.put("3769.423", 3769.423);
        map.put("3780.731", 3780.731);
        map.put("3792.073", 3792.073);
        map.put("3803.449", 3803.449);
        map.put("3814.859", 3814.859);
        map.put("3826.304", 3826.304);
        map.put("3837.783", 3837.783);
        map.put("3849.296", 3849.296);
        map.put("3860.844", 3860.844);
        map.put("3872.427", 3872.427);
        map.put("3884.044", 3884.044);
        map.put("3895.696", 3895.696);
        map.put("3907.383", 3907.383);
        map.put("3919.105", 3919.105);
        map.put("3930.862", 3930.862);
        map.put("3942.655", 3942.655);
        map.put("3954.483", 3954.483);
        map.put("3966.346", 3966.346);
        map.put("3978.245", 3978.245);

        int direction = 1;
        Long pair = 31L;
        String key = direction == 1 ? CacheConstant.BUY_DEPTH : CacheConstant.SELL_DEPTH;
        key = String.format(key, pair);
        String infoKey = direction == 1 ? CacheConstant.BUY_DEPTH_INFO : CacheConstant.SELL_DEPTH_INFO;

        for (String price : map.keySet()) {
            Boolean add = redisTemplate.opsForZSet().add(key, price, Double.parseDouble(price));

            Set<String> depthPriceList = direction == 1 ? redisTemplate.opsForZSet().reverseRange(key, 0, 100) : redisTemplate.opsForZSet().range(key, 0, 100);

            System.out.println("订单来了价格：" + price + "，状态：" + add);
            System.out.println("订单来了：" + depthPriceList.size());
        }

        return ResultVOUtils.success();
    }

}
