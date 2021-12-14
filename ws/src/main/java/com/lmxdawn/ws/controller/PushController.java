package com.lmxdawn.ws.controller;

import com.lmxdawn.ws.enums.ResultEnum;
import com.lmxdawn.ws.req.WsMarketReq;
import com.lmxdawn.ws.res.BaseRes;
import com.lmxdawn.ws.res.WSBaseRes;
import com.lmxdawn.ws.util.ResultVOUtils;
import com.lmxdawn.ws.ws.WSServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "推送信息")
@RestController
@RequestMapping("push")
public class PushController {

    @Autowired
    private WSServer wsServer;

    @ApiOperation(value = "推送行情数据")
    @PostMapping("/market")
    public BaseRes market(@RequestBody @Valid WsMarketReq req,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long memberId = req.getMemberId();
        Long orderId = req.getOrderId();
        if (memberId != null) {
            WSBaseRes orderWsBaseRes = new WSBaseRes();
            orderWsBaseRes.setType(3);
            orderWsBaseRes.setData(orderId.toString());
            wsServer.sendMsg(memberId.toString(), orderWsBaseRes);
        }
        WSBaseRes wsBaseRes = new WSBaseRes();
        wsBaseRes.setType(2);
        wsBaseRes.setData(req.getData());
        wsServer.full(wsBaseRes);

        return ResultVOUtils.success();
    }

}
