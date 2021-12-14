package com.lmxdawn.wsroute.controller;

import com.lmxdawn.wsroute.enums.ResultEnum;
import com.lmxdawn.wsroute.req.LoginReq;
import com.lmxdawn.wsroute.res.BaseRes;
import com.lmxdawn.wsroute.res.ConnectionInfoRes;
import com.lmxdawn.wsroute.service.UserLoginService;
import com.lmxdawn.wsroute.service.WsRouteService;
import com.lmxdawn.wsroute.util.ResultVOUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Api(tags = "授权ws连接信息")
@RestController
@RequestMapping("connection")
public class ConnectionController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private WsRouteService wsRouteService;

    @PostMapping("/login")
    public BaseRes<ConnectionInfoRes> login(@RequestBody @Valid LoginReq req,
                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        String memberId = "";
        if (!StringUtils.isBlank(req.getToken())) {
            Long login = userLoginService.Login(req.getToken());
            memberId = login != null && login > 0 ? login.toString() : "";
        }

        // 如果为空，则游客登录
        if (StringUtils.isBlank(memberId)) {
            memberId = UUID.randomUUID().toString();
        }

        ConnectionInfoRes connectionInfoRes = wsRouteService.connectionLogin(memberId);

        return ResultVOUtils.success(connectionInfoRes);
    }

}
