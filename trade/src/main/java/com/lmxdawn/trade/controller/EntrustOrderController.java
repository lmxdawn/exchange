package com.lmxdawn.trade.controller;

import com.lmxdawn.dubboapi.res.user.MemberCoinSimpleDubboRes;
import com.lmxdawn.dubboapi.service.user.MemberCoinDubboService;
import com.lmxdawn.trade.annotation.LoginAuthAnnotation;
import com.lmxdawn.trade.constant.MqTopicConstant;
import com.lmxdawn.trade.entity.Symbol;
import com.lmxdawn.trade.enums.ResultEnum;
import com.lmxdawn.trade.exception.JsonException;
import com.lmxdawn.trade.mq.EntrustOrderMq;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private StreamBridge streamBridge;

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
    public BaseRes create(@RequestBody @Valid EntrustOrderCreateReq req,
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
        BigDecimal bigTotal = new BigDecimal(req.getTotal() + "");

        BigDecimal bigMoney = BigDecimal.ZERO;

        Integer direction = req.getDirection();

        // 冻结的币种
        Long frozenCoinId = tradeCoinId;
        if (direction == 1) {
            // 买入
            frozenCoinId = coinId;
            // 限价
            if (type == 1) {
                req.setTotal(0.00);
                bigMoney = bigAmount.multiply(bigPrice).setScale(tradeTotalPrecision, BigDecimal.ROUND_HALF_DOWN);
            } else {
                // 市价
                if (bigTotal.compareTo(BigDecimal.ZERO) <= 0) {
                    // 市价买入订单必须输入交易额
                    return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "请输入交易额");
                }
                req.setAmount(0.00);
                req.setPrice(0.00);
                bigMoney = bigTotal;
            }
        } else {
            // 卖出
            req.setTotal(0.00);
            if (type == 2) {
                // 市价
                req.setPrice(0.00);
            }
            bigMoney = bigAmount;
        }

        if (bigMoney.compareTo(BigDecimal.ZERO) <= 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, "请输入交易额");
        }

        // 判断账户余额是否充足
        MemberCoinSimpleDubboRes memberCoin = memberCoinDubboService.balance(req.getMemberId(), frozenCoinId);
        if (memberCoin == null) {
            throw new JsonException(ResultEnum.BALANCE_NOT);
        }
        Double balance = memberCoin.getBalance();
        BigDecimal bigBalance = new BigDecimal(balance);
        // 余额不足
        if (bigMoney.compareTo(bigBalance) > 0) {
            throw new JsonException(ResultEnum.BALANCE_NOT);
        }

        req.setFrozenMoney(bigMoney.doubleValue());
        req.setFrozenCoinId(frozenCoinId);
        // 创建订单，并且调用服务冻结金额
        Long id = entrustOrderService.create(req);

        // 加入撮合队列
        EntrustOrderMq entrustOrderMq = new EntrustOrderMq();
        BeanUtils.copyProperties(req, entrustOrderMq);
        entrustOrderMq.setId(id);
        streamBridge.send(MqTopicConstant.ENTRUST_ORDER_TOPIC, entrustOrderMq);

        return ResultVOUtils.success();
    }


}
