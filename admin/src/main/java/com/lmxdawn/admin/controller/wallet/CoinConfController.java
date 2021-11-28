package com.lmxdawn.admin.controller.wallet;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.wallet.CoinConfQueryDubboReq;
import com.lmxdawn.dubboapi.req.wallet.CoinConfSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinConfDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinProtocolSimpleDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.wallet.CoinConfDubboService;
import com.lmxdawn.dubboapi.service.wallet.CoinDubboService;
import com.lmxdawn.dubboapi.service.wallet.CoinProtocolDubboService;
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
 * 币种配置
 */
@Api(tags = "币种配置管理")
@RestController
@Slf4j
public class CoinConfController {

    @DubboReference
    private CoinDubboService coinDubboService;

    @DubboReference
    private CoinProtocolDubboService coinProtocolDubboService;

    @DubboReference
    private CoinConfDubboService coinConfDubboService;

    @ApiOperation(value = "币种配置里币种列表")
    @AuthRuleAnnotation(value = "wallet/coin-conf/coinList")
    @GetMapping(value = "wallet/coin-conf/coinList")
    public BaseRes<List<CoinSimpleDubboRes>> coinList() {

        List<CoinSimpleDubboRes> list = coinDubboService.listAll();

        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "币种配置里协议列表")
    @AuthRuleAnnotation(value = "wallet/coin-conf/protocolList")
    @GetMapping(value = "wallet/coin-conf/protocolList")
    public BaseRes<List<CoinProtocolSimpleDubboRes>> protocolList() {

        List<CoinProtocolSimpleDubboRes> list = coinProtocolDubboService.listAll();

        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "币种配置列表")
    @AuthRuleAnnotation(value = "wallet/coin-conf/index")
    @GetMapping(value = "wallet/coin-conf/index")
    public BaseRes<PageSimpleDubboRes<CoinConfDubboRes>> index(@Valid CoinConfQueryDubboReq req,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<CoinConfDubboRes> list = coinConfDubboService.list(req);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "币种配置添加")
    @AuthRuleAnnotation(value = "wallet/coin-conf/save")
    @PostMapping(value = "wallet/coin-conf/save")
    public BaseRes save(@RequestBody @Valid CoinConfSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = coinConfDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "币种配置修改")
    @AuthRuleAnnotation(value = "wallet/coin-conf/edit")
    @PostMapping(value = "wallet/coin-conf/edit")
    public BaseRes edit(@RequestBody @Valid CoinConfSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = coinConfDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        return ResultVOUtils.success();
    }

}
