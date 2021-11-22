package com.lmxdawn.other.controller;

import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试接口")
@RestController
@RequestMapping("/test")
public class TestController {


    @ApiOperation(value = "打印信息", notes = "打印信息出来")
    @GetMapping("/echo")
    public BaseRes echo() {

        return ResultVOUtils.success();
    }

}
