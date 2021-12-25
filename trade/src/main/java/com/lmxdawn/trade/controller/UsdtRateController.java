package com.lmxdawn.trade.controller;

import com.lmxdawn.trade.enums.ResultEnum;
import com.lmxdawn.trade.req.UsdtRateReadReq;
import com.lmxdawn.trade.res.BaseRes;
import com.lmxdawn.trade.res.UsdtRateRes;
import com.lmxdawn.trade.service.UsdtRateService;
import com.lmxdawn.trade.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "usdt汇率")
@RestController
@RequestMapping("/usdt-rate")
public class UsdtRateController {

    @Autowired
    private UsdtRateService usdtRateService;

    @ApiOperation(value = "usdt汇率列表")
    @GetMapping("/list")
    public BaseRes<UsdtRateRes> list() {


        List<UsdtRateRes> list = usdtRateService.listAll();

        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "usdt汇率详情")
    @GetMapping("/read")
    public BaseRes<UsdtRateRes> read(@Valid UsdtRateReadReq req,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        UsdtRateRes read = usdtRateService.read(req.getName());

        return ResultVOUtils.success(read);
    }

}
