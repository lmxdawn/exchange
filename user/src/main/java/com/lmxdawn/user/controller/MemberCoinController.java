package com.lmxdawn.user.controller;

import com.lmxdawn.user.annotation.LoginAuthAnnotation;
import com.lmxdawn.user.entity.MemberCoin;
import com.lmxdawn.user.enums.ResultEnum;
import com.lmxdawn.user.req.MemberCoinBalanceReq;
import com.lmxdawn.user.req.MemberCoinPairBalanceReq;
import com.lmxdawn.user.res.BaseRes;
import com.lmxdawn.user.res.MemberCoinBalanceRes;
import com.lmxdawn.user.res.MemberCoinRes;
import com.lmxdawn.user.res.MemberCoinPairBalanceRes;
import com.lmxdawn.user.service.MemberCoinService;
import com.lmxdawn.user.util.ResultVOUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "用户钱包")
@RestController
@RequestMapping("/member-coin")
public class MemberCoinController {

    @Autowired
    private MemberCoinService memberCoinService;

    @ApiOperation(value = "获取用户钱包列表")
    @GetMapping("list")
    @LoginAuthAnnotation
    public BaseRes<List<MemberCoinRes>> list(HttpServletRequest request) {

        Long memberId = (Long) request.getAttribute("memberId");

        List<MemberCoinRes> list = memberCoinService.listAllByMemberId(memberId);

        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "获取用户钱包余额")
    @GetMapping("balance")
    @LoginAuthAnnotation
    public BaseRes<MemberCoinBalanceRes> balance(@Valid MemberCoinBalanceReq req,
                                                 BindingResult bindingResult,
                                                 HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long memberId = (Long) request.getAttribute("memberId");

        MemberCoin byMemberIdCoinId = memberCoinService.findByMemberIdCoinId(memberId, req.getCoinId());

        Double balance = 0.00;

        if (byMemberIdCoinId != null && byMemberIdCoinId.getStatus() == 2) {
            balance = byMemberIdCoinId.getBalance();
        }

        MemberCoinBalanceRes memberCoinBalanceRes = new MemberCoinBalanceRes();
        memberCoinBalanceRes.setBalance(balance);

        return ResultVOUtils.success(memberCoinBalanceRes);
    }

    @ApiOperation(value = "获取交易对钱包余额")
    @GetMapping("pair-balance")
    @LoginAuthAnnotation
    public BaseRes<MemberCoinPairBalanceRes> pairBalance(@Valid MemberCoinPairBalanceReq req,
                                                         BindingResult bindingResult,
                                                         HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long memberId = (Long) request.getAttribute("memberId");

        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();

        List<Long> coinIds = new ArrayList<>();
        coinIds.add(tradeCoinId);
        coinIds.add(coinId);

        Map<Long, MemberCoin> memberCoinMap = memberCoinService.mapByMemberIdCoinIds(memberId, coinIds);

        MemberCoinPairBalanceRes res = new MemberCoinPairBalanceRes();
        res.setTradeBalance(memberCoinMap.containsKey(tradeCoinId) ? memberCoinMap.get(tradeCoinId).getBalance() : 0.00);
        res.setBalance(memberCoinMap.containsKey(coinId) ? memberCoinMap.get(coinId).getBalance() : 0.00);

        return ResultVOUtils.success(res);
    }

}
