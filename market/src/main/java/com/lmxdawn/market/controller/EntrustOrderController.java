package com.lmxdawn.market.controller;

import com.lmxdawn.market.annotation.LoginAuthAnnotation;
import com.lmxdawn.market.enums.ResultEnum;
import com.lmxdawn.market.req.EntrustOrderCreateReq;
import com.lmxdawn.market.req.EntrustOrderListPageReq;
import com.lmxdawn.market.res.BaseRes;
import com.lmxdawn.market.res.EchoRes;
import com.lmxdawn.market.res.EntrustOrderRes;
import com.lmxdawn.market.service.EntrustOrderService;
import com.lmxdawn.market.util.ResultVOUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = "委托订单")
@RestController
@RequestMapping("/entrust-order")
public class EntrustOrderController {

    @Autowired
    private EntrustOrderService entrustOrderService;

    @ApiOperation(value = "订单列表")
    @GetMapping("/list")
    @LoginAuthAnnotation
    public BaseRes<EchoRes> list(@Valid EntrustOrderListPageReq req,
                                 BindingResult bindingResult,
                                 HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long memberId = (Long) request.getAttribute("memberId");

        req.setMemberId(memberId);

        List<EntrustOrderRes> entrustOrderRes = entrustOrderService.listPage(req);

        return ResultVOUtils.success(entrustOrderRes);
    }


    @ApiOperation(value = "发起委托")
    @PostMapping("/create")
    @LoginAuthAnnotation
    public BaseRes<EchoRes> create(@Valid EntrustOrderCreateReq req,
                                   BindingResult bindingResult,
                                   HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long memberId = (Long) request.getAttribute("memberId");

        req.setMemberId(memberId);

        Integer type = req.getType();
        // 如果是限价，并且价格小于等于0
        if (type == 1 && req.getPrice() <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "限价类型的订单价格不能为空");
        }

        // 判断账户余额是否充足


        entrustOrderService.create(req);

        return ResultVOUtils.success();
    }


}
