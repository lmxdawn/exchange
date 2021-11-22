package com.lmxdawn.other.controller;

import com.lmxdawn.other.enums.ResultEnum;
import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.util.ResultVOUtils;
import com.lmxdawn.other.req.RegionListReq;
import com.lmxdawn.other.res.RegionListInfoRes;
import com.lmxdawn.other.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "地区")
@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ApiOperation(value = "地区列表", notes = "<font size=\"5\" color=\"red\">各个参数传0表示只获取当前级别的地区，参考地址：https://codepen.io/jac142857/pen/GREPWvO</font>")
    @GetMapping("/list")
    public BaseRes<RegionListInfoRes> list(@Valid RegionListReq req,
                                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        List<RegionListInfoRes> regionListInfoRes = regionService.listAllByParams(req);

        return ResultVOUtils.success(regionListInfoRes);
    }

}
