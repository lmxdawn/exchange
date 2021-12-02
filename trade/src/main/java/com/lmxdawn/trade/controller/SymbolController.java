package com.lmxdawn.trade.controller;

import com.lmxdawn.trade.res.BaseRes;
import com.lmxdawn.trade.res.SymbolRes;
import com.lmxdawn.trade.service.SymbolService;
import com.lmxdawn.trade.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "交易对")
@RestController
@RequestMapping("/symbol-order")
public class SymbolController {

    @Autowired
    private SymbolService symbolService;

    @ApiOperation(value = "交易对列表")
    @GetMapping("/list")
    public BaseRes<SymbolRes> list() {

        List<SymbolRes> symbolRes = symbolService.listAll();

        return ResultVOUtils.success(symbolRes);
    }


}
