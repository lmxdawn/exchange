package com.lmxdawn.admin.controller.wallet;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.wallet.CoinQueryDubboReq;
import com.lmxdawn.dubboapi.req.wallet.CoinSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.wallet.CoinDubboRes;
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
import java.util.Map;

/**
 * 币种
 */
@Api(tags = "币种管理")
@RestController
@Slf4j
public class CoinController {

    @DubboReference
    private CoinDubboService coinDubboService;

    @ApiOperation(value = "币种列表")
    @AuthRuleAnnotation(value = "wallet/coin/index")
    @GetMapping(value = "wallet/coin/index")
    public BaseRes<PageSimpleDubboRes<CoinDubboRes>> index(@Valid CoinQueryDubboReq req,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<CoinDubboRes> list = coinDubboService.list(req);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "币种添加")
    @AuthRuleAnnotation(value = "wallet/coin/save")
    @PostMapping(value = "wallet/coin/save")
    public BaseRes save(@RequestBody @Valid CoinSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = coinDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "币种修改")
    @AuthRuleAnnotation(value = "wallet/coin/edit")
    @PostMapping(value = "wallet/coin/edit")
    public BaseRes edit(@RequestBody @Valid CoinSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = coinDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        return ResultVOUtils.success();
    }

}
