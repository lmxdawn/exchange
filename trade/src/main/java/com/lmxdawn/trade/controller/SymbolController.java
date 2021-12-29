package com.lmxdawn.trade.controller;

import com.lmxdawn.trade.enums.ResultEnum;
import com.lmxdawn.trade.req.SymbolListPageReq;
import com.lmxdawn.trade.req.SymbolReadReq;
import com.lmxdawn.trade.res.BaseRes;
import com.lmxdawn.trade.res.SymbolRes;
import com.lmxdawn.trade.service.SymbolService;
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

@Api(tags = "交易对")
@RestController
@RequestMapping("/symbol")
public class SymbolController {

    @Autowired
    private SymbolService symbolService;

    @ApiOperation(value = "交易对列表")
    @GetMapping("/list")
    public BaseRes<List<SymbolRes>> list(@Valid SymbolListPageReq req,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        List<SymbolRes> symbolRes = symbolService.listPage(req);

        return ResultVOUtils.success(symbolRes);
    }

    @ApiOperation(value = "交易对详情")
    @GetMapping("/read")
    public BaseRes<SymbolRes> read(@Valid SymbolReadReq req,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        SymbolRes symbolRes = symbolService.read(req);

        return ResultVOUtils.success(symbolRes);
    }


}
