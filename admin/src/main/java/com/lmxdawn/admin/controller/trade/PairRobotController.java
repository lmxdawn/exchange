package com.lmxdawn.admin.controller.trade;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.enums.ResultEnum;
import com.lmxdawn.admin.res.BaseRes;
import com.lmxdawn.admin.util.ResultVOUtils;
import com.lmxdawn.dubboapi.req.trade.PairRobotSaveDubboReq;
import com.lmxdawn.dubboapi.service.trade.PairRobotDubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 交易对机器人
 */
@Api(tags = "交易对管理")
@RestController
@Slf4j
public class PairRobotController {

    @DubboReference
    private PairRobotDubboService pairRobotDubboService;


    @ApiOperation(value = "交易对机器人添加")
    @AuthRuleAnnotation(value = "trade/pair-robot/save")
    @PostMapping(value = "trade/pair-robot/save")
    public BaseRes save(@RequestBody @Valid PairRobotSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long id = pairRobotDubboService.insert(req);

        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前交易对机器人已存在");
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", id);
        return ResultVOUtils.success(res);
    }


    @ApiOperation(value = "交易对机器人修改")
    @AuthRuleAnnotation(value = "trade/pair-robot/edit")
    @PostMapping(value = "trade/pair-robot/edit")
    public BaseRes edit(@RequestBody @Valid PairRobotSaveDubboReq req,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (req.getId() == null || req.getId() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "ID必传");
        }

        boolean update = pairRobotDubboService.update(req);

        if (!update) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前交易对已存在");
        }

        return ResultVOUtils.success();
    }

}
