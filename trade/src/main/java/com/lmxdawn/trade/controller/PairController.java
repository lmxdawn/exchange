package com.lmxdawn.trade.controller;

import com.lmxdawn.trade.enums.ResultEnum;
import com.lmxdawn.trade.req.PairListPageReq;
import com.lmxdawn.trade.req.PairReadReq;
import com.lmxdawn.trade.res.BaseRes;
import com.lmxdawn.trade.res.PairRes;
import com.lmxdawn.trade.service.PairService;
import com.lmxdawn.trade.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "交易对")
@RestController
@RequestMapping("/pair")
public class PairController {

    @Autowired
    private PairService pairService;

    @ApiOperation(value = "交易对列表")
    @GetMapping("/list")
    public BaseRes<List<PairRes>> list(@Valid PairListPageReq req,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }


        List<PairRes> pairRes = pairService.listPage(req);

        return ResultVOUtils.success(pairRes);
    }

    @ApiOperation(value = "交易对详情")
    @GetMapping("/read")
    public BaseRes<PairRes> read(@Valid PairReadReq req,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        PairRes pairRes = pairService.read(req);

        return ResultVOUtils.success(pairRes);
    }


}
