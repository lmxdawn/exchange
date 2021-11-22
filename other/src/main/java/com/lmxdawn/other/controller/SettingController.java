package com.lmxdawn.other.controller;

import com.lmxdawn.other.enums.ResultEnum;
import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.util.ResultVOUtils;
import com.lmxdawn.other.req.SystemAppVersionReq;
import com.lmxdawn.other.res.SettingAppInfoRes;
import com.lmxdawn.other.res.SystemAppVersionInfoRes;
import com.lmxdawn.other.service.AppSettingService;
import com.lmxdawn.other.service.SystemAppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "配置相关")
@RestController
@RequestMapping("/setting")
public class SettingController {

    @Resource
    private SystemAppVersionService systemAppVersionService;

    @Resource
    private AppSettingService appSettingService;

    @ApiOperation(value = "APP版本配置")
    @GetMapping("/appVersion")
    public BaseRes<SystemAppVersionInfoRes> appVersion(@Validated SystemAppVersionReq req,
                                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        SystemAppVersionInfoRes systemAppVersionInfoRes = systemAppVersionService.update(req);

        return ResultVOUtils.success(systemAppVersionInfoRes);
    }


    @ApiOperation(value = "APP配置")
    @GetMapping("/app")
    public BaseRes<SettingAppInfoRes> app() {

        SettingAppInfoRes settingAppInfoRes = appSettingService.appSettingInfo();

        return ResultVOUtils.success(settingAppInfoRes);
    }

}
