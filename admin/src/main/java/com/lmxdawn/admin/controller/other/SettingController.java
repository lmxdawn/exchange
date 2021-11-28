package com.lmxdawn.admin.controller.other;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.other.SettingQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.SettingSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.SettingInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.SettingDubboService;
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

/**
 * 登录相关
 */
@Api(tags = "其它管理")
@RestController
@Slf4j
public class SettingController {

    @DubboReference
    private SettingDubboService settingDubboService;

    @ApiOperation(value = "配置列表")
    @AuthRuleAnnotation(value = "other/setting/index")
    @GetMapping(value = "other/setting/index")
    public BaseRes<PageSimpleDubboRes<SettingInfoDubboRes>> index(@Valid SettingQueryDubboReq settingQueryDubboReq,
                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<SettingInfoDubboRes> list = settingDubboService.list(settingQueryDubboReq);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "更新配置")
    @AuthRuleAnnotation(value = "other/setting/save")
    @PostMapping(value = "other/setting/save")
    public BaseRes save(@RequestBody @Valid SettingSaveDubboReq settingSaveDubboReq,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        settingDubboService.updateSetting(settingSaveDubboReq);

        return ResultVOUtils.success();
    }

}
