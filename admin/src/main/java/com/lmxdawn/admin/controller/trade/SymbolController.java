package com.lmxdawn.admin.controller.trade;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.trade.SymbolQueryDubboReq;
import com.lmxdawn.dubboapi.req.trade.SymbolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.trade.SymbolDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.trade.SymbolDubboService;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
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
import java.util.List;
import java.util.Map;

/**
 * 交易对
 */
@Api(tags = "交易对管理")
@RestController
@Slf4j
public class SymbolController {

    @DubboReference
    private CoinDubboService coinDubboService;

    @DubboReference
    private SymbolDubboService symbolDubboService;

    @ApiOperation(value = "交易对里币种列表")
    @AuthRuleAnnotation(value = "market/symbol/coinList")
    @GetMapping(value = "market/symbol/coinList")
    public BaseRes<List<CoinSimpleDubboRes>> coinList() {

        List<CoinSimpleDubboRes> list = coinDubboService.listAll();

        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "交易对列表")
    @AuthRuleAnnotation(value = "market/symbol/index")
    @GetMapping(value = "market/symbol/index")
    public BaseRes<PageSimpleDubboRes<SymbolDubboRes>> index(@Valid SymbolQueryDubboReq req,
                                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<SymbolDubboRes> list = symbolDubboService.list(req);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "交易对添加")
    @AuthRuleAnnotation(value = "market/symbol/save")
    @PostMapping(value = "market/symbol/save")
    public BaseRes save(@RequestBody @Valid SymbolSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = symbolDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前交易对已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "交易对修改")
    @AuthRuleAnnotation(value = "market/symbol/edit")
    @PostMapping(value = "market/symbol/edit")
    public BaseRes edit(@RequestBody @Valid SymbolSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = symbolDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前交易对已存在");
        }

        return ResultVOUtils.success();
    }

}
