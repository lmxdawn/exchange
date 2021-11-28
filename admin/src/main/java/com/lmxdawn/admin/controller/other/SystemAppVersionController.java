package com.lmxdawn.admin.controller.other;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.other.SystemAppVersionQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.SystemAppVersionSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.SystemAppVersionInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.SystemAppVersionDubboService;
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
 * app版本
 */
@Api(tags = "app版本管理")
@RestController
@Slf4j
public class SystemAppVersionController {

    @DubboReference
    private SystemAppVersionDubboService systemAppVersionDubboService;

    @ApiOperation(value = "app版本列表")
    @AuthRuleAnnotation(value = "other/system-app-version/index")
    @GetMapping(value = "other/system-app-version/index")
    public BaseRes<PageSimpleDubboRes<SystemAppVersionInfoDubboRes>> index(@Valid SystemAppVersionQueryDubboReq req,
                                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<SystemAppVersionInfoDubboRes> list = systemAppVersionDubboService.list(req);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "app版本添加")
    @AuthRuleAnnotation(value = "other/system-app-version/save")
    @PostMapping(value = "other/system-app-version/save")
    public BaseRes save(@RequestBody @Valid SystemAppVersionSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = systemAppVersionDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "app版本修改")
    @AuthRuleAnnotation(value = "other/system-app-version/edit")
    @PostMapping(value = "other/system-app-version/edit")
    public BaseRes edit(@RequestBody @Valid SystemAppVersionSaveDubboReq req,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = systemAppVersionDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前名称已存在");
        }

        return ResultVOUtils.success();
    }

}
