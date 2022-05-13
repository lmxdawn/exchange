package com.lmxdawn.admin.controller.other;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.other.AreaCodeQueryDubboReq;
import com.lmxdawn.dubboapi.req.other.AreaCodeSaveDubboReq;
import com.lmxdawn.dubboapi.res.PageSimpleDubboRes;
import com.lmxdawn.dubboapi.res.other.AreaCodeInfoDubboRes;
import com.lmxdawn.dubboapi.service.other.AreaCodeDubboService;
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
 * 电话地区
 */
@Api(tags = "电话地区管理")
@RestController
@Slf4j
public class AreaCodeController {

    @DubboReference
    private AreaCodeDubboService areaCodeDubboService;

    @ApiOperation(value = "电话地区列表")
    @AuthRuleAnnotation(value = "other/area-code/index")
    @GetMapping(value = "other/area-code/index")
    public BaseRes<PageSimpleDubboRes<AreaCodeInfoDubboRes>> index(@Valid AreaCodeQueryDubboReq req,
                                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        PageSimpleDubboRes<AreaCodeInfoDubboRes> list = areaCodeDubboService.list(req);

        return ResultVOUtils.success(list);
    }


    @ApiOperation(value = "电话地区添加")
    @AuthRuleAnnotation(value = "other/area-code/save")
    @PostMapping(value = "other/area-code/save")
    public BaseRes save(@RequestBody @Valid AreaCodeSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = areaCodeDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前电话地区key已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "电话地区修改")
    @AuthRuleAnnotation(value = "other/area-code/edit")
    @PostMapping(value = "other/area-code/edit")
    public BaseRes edit(@RequestBody @Valid AreaCodeSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = areaCodeDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前电话地区已存在");
        }

        return ResultVOUtils.success();
    }

}
