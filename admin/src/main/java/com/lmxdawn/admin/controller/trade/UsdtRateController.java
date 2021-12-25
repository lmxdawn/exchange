package com.lmxdawn.admin.controller.trade;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.trade.UsdtRateQueryDubboReq;
import com.lmxdawn.dubboapi.req.trade.UsdtRateSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.trade.UsdtRateDubboRes;
import com.lmxdawn.dubboapi.service.trade.UsdtRateDubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Usdt汇率
 */
@Api(tags = "Usdt汇率管理")
@RestController
@Slf4j
public class UsdtRateController {

    @DubboReference
    private UsdtRateDubboService usdtRateDubboService;

    @ApiOperation(value = "Usdt汇率列表")
    @AuthRuleAnnotation(value = "market/usdt-rate/index")
    @GetMapping(value = "market/usdt-rate/index")
    public BaseRes<PageSimpleDubboRes<UsdtRateDubboRes>> index(@Valid UsdtRateQueryDubboReq req,
                                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<UsdtRateDubboRes> list = usdtRateDubboService.list(req);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "Usdt汇率添加")
    @AuthRuleAnnotation(value = "market/usdt-rate/save")
    @PostMapping(value = "market/usdt-rate/save")
    public BaseRes save(@RequestBody @Valid UsdtRateSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = usdtRateDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前Usdt汇率已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "Usdt汇率修改")
    @AuthRuleAnnotation(value = "market/usdt-rate/edit")
    @PostMapping(value = "market/usdt-rate/edit")
    public BaseRes edit(@RequestBody @Valid UsdtRateSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = usdtRateDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前Usdt汇率已存在");
        }

        return ResultVOUtils.success();
    }

}
