package com.lmxdawn.admin.controller.other;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.other.ProtocolQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.ProtocolSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.ProtocolInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.ProtocolDubboService;
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
 * 协议
 */
@Api(tags = "协议管理")
@RestController
@Slf4j
public class ProtocolController {

    @DubboReference
    private ProtocolDubboService protocolDubboService;

    @ApiOperation(value = "协议列表")
    @AuthRuleAnnotation(value = "other/protocol/index")
    @GetMapping(value = "other/protocol/index")
    public BaseRes<PageSimpleDubboRes<ProtocolInfoDubboRes>> index(@Valid ProtocolQueryDubboReq req,
                                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<ProtocolInfoDubboRes> list = protocolDubboService.list(req);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "协议添加")
    @AuthRuleAnnotation(value = "other/protocol/save")
    @PostMapping(value = "other/protocol/save")
    public BaseRes save(@RequestBody @Valid ProtocolSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = protocolDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前协议key已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "协议修改")
    @AuthRuleAnnotation(value = "other/protocol/edit")
    @PostMapping(value = "other/protocol/edit")
    public BaseRes edit(@RequestBody @Valid ProtocolSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = protocolDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前协议key已存在");
        }

        return ResultVOUtils.success();
    }

}
