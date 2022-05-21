package com.lmxdawn.trade.controller;

import com.lmxdawn.trade.annotation.LoginAuthAnnotation;
import com.lmxdawn.trade.enums.ResultEnum;
import com.lmxdawn.trade.req.EntrustOrderDetailReq;
import com.lmxdawn.trade.res.BaseRes;
import com.lmxdawn.trade.res.EntrustOrderDetailRes;
import com.lmxdawn.trade.service.EntrustOrderDetailService;
import com.lmxdawn.trade.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "委托订单明细")
@RestController
@RequestMapping("/entrust-order-detail")
public class EntrustOrderDetailController {

    @Autowired
    private EntrustOrderDetailService entrustOrderDetailService;

    @ApiOperation(value = "订单列表")
    @GetMapping("/list")
    @LoginAuthAnnotation
    public BaseRes<EntrustOrderDetailRes> list(@Valid EntrustOrderDetailReq req,
                                               BindingResult bindingResult,
                                               HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long memberId = (Long) request.getAttribute("memberId");

        req.setMemberId(memberId);

        List<EntrustOrderDetailRes> res = entrustOrderDetailService.listPageByMemberIdAndOrderId(req);

        return ResultVOUtils.success(res);
    }

}
