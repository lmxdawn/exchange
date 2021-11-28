package com.lmxdawn.admin.controller.wallet;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.wallet.CoinProtocolQueryDubboReq;
import com.lmxdawn.dubboapi.req.wallet.CoinProtocolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinProtocolDubboRes;
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
import java.util.Map;

/**
 * 币种协议
 */
@Api(tags = "币种协议管理")
@RestController
@Slf4j
public class CoinProtocolController {

    @DubboReference
    private CoinProtocolDubboService coinProtocolDubboService;

    @ApiOperation(value = "币种协议列表")
    @AuthRuleAnnotation(value = "wallet/coin-protocol/index")
    @GetMapping(value = "wallet/coin-protocol/index")
    public BaseRes<PageSimpleDubboRes<CoinProtocolDubboRes>> index(@Valid CoinProtocolQueryDubboReq req,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<CoinProtocolDubboRes> list = coinProtocolDubboService.list(req);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "币种协议添加")
    @AuthRuleAnnotation(value = "wallet/coin-protocol/save")
    @PostMapping(value = "wallet/coin-protocol/save")
    public BaseRes save(@RequestBody @Valid CoinProtocolSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = coinProtocolDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "币种协议修改")
    @AuthRuleAnnotation(value = "wallet/coin-protocol/edit")
    @PostMapping(value = "wallet/coin-protocol/edit")
    public BaseRes edit(@RequestBody @Valid CoinProtocolSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = coinProtocolDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        return ResultVOUtils.success();
    }

}
