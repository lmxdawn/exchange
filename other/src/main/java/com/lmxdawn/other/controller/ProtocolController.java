package com.lmxdawn.other.controller;

import com.lmxdawn.other.enums.ResultEnum;
import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.util.ResultVOUtils;
import com.lmxdawn.other.req.ProtocolReadReq;
import com.lmxdawn.other.res.ProtocolInfoRes;
import com.lmxdawn.other.service.ProtocolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "协议相关")
@RestController
@RequestMapping("/protocol")
public class ProtocolController {

    @Resource
    private ProtocolService protocolService;

    @ApiOperation(value = "获取协议")
    @GetMapping("/read")
    public BaseRes<ProtocolInfoRes> read(@Valid ProtocolReadReq req,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        ProtocolInfoRes protocolInfoRes = protocolService.findByKey(req.getKey());

        return ResultVOUtils.success(protocolInfoRes);
    }

}
