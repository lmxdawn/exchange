package com.lmxdawn.trade.controller;

import com.lmxdawn.dubboapi.res.user.MemberCoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.user.MemberCoinDubboService;
import com.lmxdawn.trade.annotation.LoginAuthAnnotation;
import com.lmxdawn.trade.entity.Symbol;
import com.lmxdawn.trade.enums.ResultEnum;
import com.lmxdawn.trade.exception.JsonException;
import com.lmxdawn.trade.req.EntrustOrderCreateReq;
import com.lmxdawn.trade.req.EntrustOrderListPageReq;
import com.lmxdawn.trade.res.BaseRes;
import com.lmxdawn.trade.res.EchoRes;
import com.lmxdawn.trade.res.EntrustOrderRes;
import com.lmxdawn.trade.service.EntrustOrderService;
import com.lmxdawn.trade.service.SymbolService;
import com.lmxdawn.trade.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "委托订单")
@RestController
@RequestMapping("/entrust-order")
public class EntrustOrderController {

    @Autowired
    private EntrustOrderService entrustOrderService;

    @Autowired
    private SymbolService symbolService;
    
    @DubboReference
    private MemberCoinDubboService memberCoinDubboService;

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
    public BaseRes create(@Valid EntrustOrderCreateReq req,
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

        Long tradeCoinId = req.getTradeCoinId();
        Long coinId = req.getCoinId();

        // 查询交易对精度
        Symbol byTidAndCid = symbolService.findByTidAndCid(tradeCoinId, coinId);
        if (byTidAndCid == null) {
            throw new JsonException(ResultEnum.SYMBOL_NOT);
        }
        // 交易对精度
        Integer tradeTotalPrecision = byTidAndCid.getTradeTotalPrecision();
        BigDecimal bigPrice = new BigDecimal(req.getPrice() + "");
        BigDecimal bigAmount = new BigDecimal(req.getAmount() + "");

        BigDecimal bigMoney = bigAmount.multiply(bigPrice).setScale(tradeTotalPrecision, BigDecimal.ROUND_HALF_DOWN);

        Integer direction = req.getDirection();

        // 支付的币种
        Long byCoinId = tradeCoinId;
        // 买入
        if (direction == 1) {
            byCoinId = coinId;
            // 限价
            if (type == 1) {
                req.setTotal(bigMoney.doubleValue());
            }
        }

        // 判断账户余额是否充足
        MemberCoinSimpleDubboRes memberCoin = memberCoinDubboService.balance(req.getMemberId(), byCoinId);
        if (memberCoin == null) {
            throw new JsonException(ResultEnum.BALANCE_NOT);
        }
        Double balance = memberCoin.getBalance();
        BigDecimal bigBalance = new BigDecimal(balance);
        // 余额不足
        if (bigMoney.compareTo(bigBalance) > 0) {
            throw new JsonException(ResultEnum.BALANCE_NOT);
        }

        // 创建订单，并且调用服务冻结金额
        entrustOrderService.create(req);

        return ResultVOUtils.success();
    }


}
