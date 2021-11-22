package com.lmxdawn.other.controller;

import com.lmxdawn.other.enums.ResultEnum;
import com.lmxdawn.other.res.BaseRes;
import com.lmxdawn.other.util.ResultVOUtils;
import com.lmxdawn.other.req.HuaWeiObsAllReq;
import com.lmxdawn.other.req.HuaWeiObsReq;
import com.lmxdawn.other.res.HuaWeiObsPostParamRes;
import com.lmxdawn.other.res.HuaWeiObsPutParamRes;
import com.lmxdawn.other.service.HuaWeiObsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "存储相关")
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Resource
    private HuaWeiObsService huaWeiObsService;

    @ApiOperation(value = "获取华为云PUT上传参数", notes = "<font size=\"5\" color=\"red\">返回的签名10秒后过期，前端使用PUT上传，不带参数直接传文件流</font>")
    @PostMapping("/huaWeiUploadPutParam")
    public BaseRes<HuaWeiObsPutParamRes> huaWeiUploadPutParam(@RequestBody @Validated HuaWeiObsReq huaWeiObsReq,
                                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        HuaWeiObsPutParamRes uploadPutParam = huaWeiObsService.createUploadPutParam(huaWeiObsReq.getSuffix());

        if (uploadPutParam == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "Please check if the background is configured");
        }

        return ResultVOUtils.success(uploadPutParam);
    }

    @ApiOperation(value = "批量获取华为云PUT上传参数", notes = "<font size=\"5\" color=\"red\">返回的签名10秒后过期，前端使用PUT上传，不带参数直接传文件流</font>")
    @PostMapping("/huaWeiUploadPutParamAll")
    public BaseRes<List<HuaWeiObsPutParamRes>> huaWeiUploadPutParamAll(@RequestBody @Validated HuaWeiObsAllReq req,
                                                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        List<HuaWeiObsPutParamRes> list = huaWeiObsService.createUploadPutParamAll(req.getSuffixs());

        if (list == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "Please check if the background is configured");
        }

        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "获取华为云POST上传参数", notes = "<font size=\"5\" color=\"red\">返回的签名10秒后过期，前端组装参数<br/>" +
            "请求URL：返回的url参数<br/>" +
            "`key：返回的key`<br/>" +
            "`policy：返回的policy`<br/>" +
            "`signature：返回的signature`<br/>" +
            "`AccessKeyId：返回的accessKeyId`<br/>" +
            "`x-obs-Acl：返回的xobsAcl`<br/>" +
            "`file：binary文件流`<br/>" +
            "</font>")
    @PostMapping("/huaWeiUploadPostParam")
    public BaseRes<HuaWeiObsPostParamRes> huaWeiUploadPostParam(@RequestBody @Validated HuaWeiObsReq huaWeiObsReq,
                                                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        HuaWeiObsPostParamRes uploadParam = huaWeiObsService.createUploadPostParam(huaWeiObsReq.getSuffix());

        if (uploadParam == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "Please check if the background is configured");
        }

        return ResultVOUtils.success(uploadParam);
    }

    @ApiOperation(value = "批量获取华为云POST上传参数", notes = "<font size=\"5\" color=\"red\">返回的签名10秒后过期，前端组装参数<br/>" +
            "请求URL：返回的url参数<br/>" +
            "`key：返回的key`<br/>" +
            "`policy：返回的policy`<br/>" +
            "`signature：返回的signature`<br/>" +
            "`AccessKeyId：返回的accessKeyId`<br/>" +
            "`x-obs-Acl：返回的xobsAcl`<br/>" +
            "`file：binary文件流`<br/>" +
            "</font>")
    @PostMapping("/huaWeiUploadPostParamAll")
    public BaseRes<List<HuaWeiObsPostParamRes>> huaWeiUploadPostParamAll(@RequestBody @Validated HuaWeiObsAllReq req,
                                                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        List<HuaWeiObsPostParamRes> list = huaWeiObsService.createUploadPostParamAll(req.getSuffixs());

        if (list == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "Please check if the background is configured");
        }

        return ResultVOUtils.success(list);
    }

}
