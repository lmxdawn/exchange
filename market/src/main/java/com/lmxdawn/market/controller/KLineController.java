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
        long date = 1652191200L;
        for (int i = 0; i < 100; i++) {
            if (close.compareTo(BigDecimal.ZERO) == 0) {
                open = BigDecimal.valueOf(RandomUtils.nextDouble(1, 9999));
            } else {
                open = close;
            }
            close = BigDecimal.valueOf(RandomUtils.nextDouble(1, 9999));
            KLineListRes kLineListRes = new KLineListRes();
            kLineListRes.setTime(date + 60);
            kLineListRes.setOpen(open);
            kLineListRes.setClose(close);
            kLineListRes.setLowest(close.add(BigDecimal.valueOf(0.5)));
            kLineListRes.setHighest(close.add(BigDecimal.valueOf(2)));
            kLineListRes.setVol(BigDecimal.valueOf(RandomUtils.nextDouble(1, 50)));
            kLineListResList.add(kLineListRes);
        }


        return ResultVOUtils.success(kLineListResList);
    }

}
