package com.lmxdawn.market.controller;

import com.lmxdawn.market.enums.ResultEnum;
import com.lmxdawn.market.req.KLineListReq;
import com.lmxdawn.market.res.BaseRes;
import com.lmxdawn.market.res.KLineListRes;
import com.lmxdawn.market.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Api(tags = "行情K线信息")
@RestController
@RequestMapping("/kLine")
public class KLineController {

    @ApiOperation(value = "K线历史列表")
    @GetMapping("/list")
    public BaseRes<KLineListRes> list(@Valid KLineListReq req,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        List<KLineListRes> kLineListResList = new ArrayList<>();

        BigDecimal close = BigDecimal.ZERO;
        BigDecimal open;
        long date = new Date().getTime();
        for (int i = 0; i < 100; i++) {
            if (close.compareTo(BigDecimal.ZERO) == 0) {
                open = BigDecimal.valueOf(RandomUtils.nextDouble(20, 100));
            } else {
                open = close;
            }
            if (i % 2 == 0) {
                close = open.add(BigDecimal.valueOf(RandomUtils.nextDouble(1, 5)));
            } else {
                close = open.subtract(BigDecimal.valueOf(RandomUtils.nextDouble(1, 5)));
            }
            date = date + 60;
            KLineListRes kLineListRes = new KLineListRes();
            kLineListRes.setTime(date);
            kLineListRes.setOpen(open);
            kLineListRes.setClose(close);
            kLineListRes.setLowest(close.subtract(BigDecimal.valueOf(RandomUtils.nextDouble(1, 5))));
            kLineListRes.setHighest(close.add(BigDecimal.valueOf(RandomUtils.nextDouble(1, 5))));
            kLineListRes.setVol(BigDecimal.valueOf(RandomUtils.nextDouble(1, 20)));
            kLineListResList.add(kLineListRes);
        }


        return ResultVOUtils.success(kLineListResList);
    }

}
