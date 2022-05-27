package com.lmxdawn.market.controller;

import com.lmxdawn.dubboapi.res.trade.PairSimpleDubboRes;
import com.lmxdawn.dubboapi.service.trade.PairDubboService;
import com.lmxdawn.market.constant.CacheConstant;
import com.lmxdawn.market.enums.ResultEnum;
import com.lmxdawn.market.req.DepthListReq;
import com.lmxdawn.market.res.BaseRes;
import com.lmxdawn.market.res.DepthListRes;
import com.lmxdawn.market.res.DepthRes;
import com.lmxdawn.market.util.ResultVOUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Api(tags = "行情深度信息")
@RestController
@RequestMapping("/depth")
public class DepthController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @DubboReference
    private PairDubboService pairDubboService;

    @ApiOperation(value = "深度列表")
    @GetMapping("/list")
    public BaseRes<DepthListRes> list(@Valid DepthListReq req,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();

        DepthListRes depthListRes = new DepthListRes();

        PairSimpleDubboRes byTidAndCid = pairDubboService.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid == null) {
            return ResultVOUtils.success(depthListRes);
        }

        Integer tradeAmountPrecision = byTidAndCid.getTradeAmountPrecision();

        // 放大倍数，不然有精度问题
        BigDecimal bigPow = BigDecimal.valueOf(Math.pow(10.0, tradeAmountPrecision));

        Long pair = Long.valueOf(tradeCoinId.toString() + coinId.toString());

        String buyKey = String.format(CacheConstant.BUY_DEPTH, pair);
        String buyInfoKey =  CacheConstant.BUY_DEPTH_INFO;
        // 买盘，从大到小获取
        Set<String> depthBuyList = redisTemplate.opsForZSet().reverseRange(buyKey, 0, 100);
        List<DepthRes> buyList = new ArrayList<>();
        if (depthBuyList != null) {
            for (String depthPrice : depthBuyList) {
                String depthAmountKey = String.format(buyInfoKey, pair, depthPrice);
                String depthAmountStr = redisTemplate.opsForValue().get(depthAmountKey);
                DepthRes depthRes = new DepthRes();
                depthRes.setPrice(Double.parseDouble(depthPrice));
                BigDecimal depthAmount = !StringUtils.isBlank(depthAmountStr) ? new BigDecimal(depthAmountStr) : BigDecimal.ZERO;
                if (depthAmount.compareTo(BigDecimal.ZERO) <= 0) {
                    redisTemplate.delete(depthAmountKey);
                    redisTemplate.opsForZSet().remove(buyKey, depthPrice);
                    continue;
                }
                depthRes.setAmount(depthAmount.divide(bigPow, tradeAmountPrecision,BigDecimal.ROUND_DOWN).doubleValue());
                buyList.add(depthRes);
            }
            depthListRes.setBuyList(buyList);
        }

        // 卖出盘
        String sellKey = String.format(CacheConstant.SELL_DEPTH, pair);
        String sellInfoKey =  CacheConstant.SELL_DEPTH_INFO;
        // 卖盘，从小到大获取
        Set<String> depthSellList = redisTemplate.opsForZSet().range(sellKey, 0, 100);
        List<DepthRes> sellList = new ArrayList<>();
        if (depthSellList != null) {
            for (String depthPrice : depthSellList) {
                String depthAmountKey = String.format(sellInfoKey, pair, depthPrice);
                String depthAmountStr = redisTemplate.opsForValue().get(depthAmountKey);
                DepthRes depthRes = new DepthRes();
                depthRes.setPrice(Double.parseDouble(depthPrice));
                BigDecimal depthAmount = !StringUtils.isBlank(depthAmountStr) ? new BigDecimal(depthAmountStr) : BigDecimal.ZERO;
                if (depthAmount.compareTo(BigDecimal.ZERO) <= 0) {
                    redisTemplate.delete(depthAmountKey);
                    redisTemplate.opsForZSet().remove(sellKey, depthPrice);
                    continue;
                }
                depthRes.setAmount(depthAmount.divide(bigPow, tradeAmountPrecision,BigDecimal.ROUND_DOWN).doubleValue());
                sellList.add(depthRes);
            }
            depthListRes.setSellList(sellList);
        }



        return ResultVOUtils.success(depthListRes);
    }

}
